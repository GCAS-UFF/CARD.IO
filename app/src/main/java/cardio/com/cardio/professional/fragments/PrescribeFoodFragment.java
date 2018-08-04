package cardio.com.cardio.professional.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cardio.com.cardio.R;
import cardio.com.cardio.common.Firebase.FirebaseHelper;
import cardio.com.cardio.common.adapters.ItemExpandableSimpleListAdapter;
import cardio.com.cardio.common.adapters.MapRecycleViewAdapter;
import cardio.com.cardio.common.model.model.Alimentacao;
import cardio.com.cardio.common.model.model.Recomentation;
import cardio.com.cardio.professional.ComunicatorFragmentActivity;

public class PrescribeFoodFragment extends Fragment {

    private RelativeLayout mRlPrescribe;
    private RecyclerView mRVHistory;
    private ComunicatorFragmentActivity comunicatorFragmentActivity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_alimentation_prescribe, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        comunicatorFragmentActivity = (ComunicatorFragmentActivity) context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRlPrescribe = (RelativeLayout) view.findViewById(R.id.rl_prescribe_food);
        mRlPrescribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrescribeFoodDialogFragment prescribeFoodDialogFragment = new PrescribeFoodDialogFragment();
                prescribeFoodDialogFragment.show(getActivity().getSupportFragmentManager(), "dialogFragment");
            }
        });

        mRVHistory = (RecyclerView) view.findViewById(R.id.recycle_view_prescribe_food);
        mRVHistory.setLayoutManager(new LinearLayoutManager(getActivity()));

        DatabaseReference prescriptionRef = FirebaseHelper.getInstance().
                getPatientDatabaseReference(comunicatorFragmentActivity.getPatientSelected().getId()).
                child(FirebaseHelper.RECOMMENDED_ACTION_KEY).
                child(FirebaseHelper.ALIMENTACAO_KEY);

        prescriptionRef.addValueEventListener(getPrescriptionListener);

    }

    private ValueEventListener getPrescriptionListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            Map<String, Map<String,String>> result = new HashMap<>();

            for (DataSnapshot entrySnapshot: dataSnapshot.getChildren()) {
                Alimentacao alimentacao = new Alimentacao();
                Recomentation recomentation = entrySnapshot.getValue(Recomentation.class);

                alimentacao.setQuantity(Integer.parseInt(entrySnapshot.
                        child(FirebaseHelper.QUANTIDADE_KEY).
                        getValue(Long.class).toString()));

                recomentation.setAction(alimentacao);

                result.put(entrySnapshot.getKey(), recomentation.toMap());
            }

            ItemExpandableSimpleListAdapter itemExpandableSimpleListAdapter = new ItemExpandableSimpleListAdapter(result);
            mRVHistory.setAdapter(itemExpandableSimpleListAdapter);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(PrescribeFoodFragment.class.getName(), databaseError.getDetails());
        }
    };
}
