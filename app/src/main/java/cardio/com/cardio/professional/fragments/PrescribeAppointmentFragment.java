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
import android.widget.RelativeLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

import cardio.com.cardio.R;
import cardio.com.cardio.common.Firebase.FirebaseHelper;
import cardio.com.cardio.common.adapters.ItemExpandableSimpleListAdapter;
import cardio.com.cardio.common.model.model.Consulta;
import cardio.com.cardio.common.model.model.MedicaoDadosFisiologicos;
import cardio.com.cardio.common.model.model.Profissional;
import cardio.com.cardio.common.model.model.Recomentation;
import cardio.com.cardio.common.util.Formater;
import cardio.com.cardio.common.util.PreferencesUtils;
import cardio.com.cardio.professional.ComunicatorFragmentActivity;

public class PrescribeAppointmentFragment extends Fragment {

    private RelativeLayout mRlPrescribe;
    private RecyclerView mRVHistory;
    private ComunicatorFragmentActivity comunicatorFragmentActivity;
    private ItemExpandableSimpleListAdapter itemExpandableSimpleListAdapter;

    public PrescribeAppointmentFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_prescribe_appointment, container, false);
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
                PrescribeAppointmentDialogFragment prescribeAppointmentDialogFragment = new PrescribeAppointmentDialogFragment();
                prescribeAppointmentDialogFragment.show(getActivity().getSupportFragmentManager(), "dialogFragment");
            }
        });

        mRVHistory = (RecyclerView) view.findViewById(R.id.recycle_view_prescribe_food);
        mRVHistory.setLayoutManager(new LinearLayoutManager(getActivity()));

        FirebaseHelper.appointmentDatabaseReference.addValueEventListener(getAppointmentListener);
    }

    private Map<String, List<Map.Entry<String, String>>> getAppointmentByDate(List<Consulta> appointments) {

        Map<String, List<Map.Entry<String, String>>> result = new LinkedHashMap<>();

        for (Consulta appoitment : appointments){

            String dateStr = Formater.getStringFromDate(new Date(appoitment.getData()));

            if (!result.containsKey(dateStr)){
                result.put(dateStr , new ArrayList<Map.Entry<String, String>>());
            }
            result.get(dateStr).addAll(appoitment.toMap().entrySet());

        }

        TreeMap<String, List<Map.Entry<String, String>>> sorted = new TreeMap<>();
        sorted.putAll(result);


        return sorted.descendingMap();
    }

    private ValueEventListener getAppointmentListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            try {

                List<Consulta> appoitments = new ArrayList<>();

                for (DataSnapshot entrySnapshot : dataSnapshot.getChildren()) {
                    Consulta consulta = entrySnapshot.getValue(Consulta.class);

                    if (consulta.getProfissional().equals(PreferencesUtils.getString(getActivity(), FirebaseHelper.USER_KEY))
                            && consulta.getPaciente().equals(comunicatorFragmentActivity.getPatientSelected().getId())){

                        consulta.setPacienteObject(comunicatorFragmentActivity.getPatientSelected());
                        appoitments.add(consulta);
                    }
                }

                itemExpandableSimpleListAdapter = new ItemExpandableSimpleListAdapter(getAppointmentByDate(appoitments));
                mRVHistory.setAdapter(itemExpandableSimpleListAdapter);

            }catch (NullPointerException e){
                e.printStackTrace();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(PrescribeFoodFragment.class.getName(), databaseError.getDetails());
        }
    };
}