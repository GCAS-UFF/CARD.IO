package cardio.com.cardio.professional.fragments;

import android.content.Context;
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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import cardio.com.cardio.R;
import cardio.com.cardio.common.Firebase.FirebaseHelper;
import cardio.com.cardio.common.adapters.ItemExpandableSimpleListAdapter;
import cardio.com.cardio.common.model.model.Medicamento;
import cardio.com.cardio.common.model.model.Profissional;
import cardio.com.cardio.common.model.model.Recomentation;
import cardio.com.cardio.common.util.Formater;
import cardio.com.cardio.professional.ComunicatorFragmentActivity;

public class PrescribeMedicineFragment extends Fragment {


    private RelativeLayout mRlPrescribe;
    private RecyclerView mRVOldMedicines;
    private RecyclerView mRVCurrentMedicines;
    private ComunicatorFragmentActivity comunicatorFragmentActivity;
    private ItemExpandableSimpleListAdapter currentItemExpandableSimpleListAdapter;
    private ItemExpandableSimpleListAdapter oldItemExpandableSimpleListAdapter;


    public PrescribeMedicineFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_prescribe_medicine, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context != null)
            comunicatorFragmentActivity = (ComunicatorFragmentActivity) context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRlPrescribe = (RelativeLayout) view.findViewById(R.id.rl_prescribe_food);
        mRlPrescribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrescribeMedicineDialogFragment prescribeMedicineDialogFragment = new PrescribeMedicineDialogFragment();
                prescribeMedicineDialogFragment.show(getActivity().getSupportFragmentManager(), "dialogFragment");
            }
        });

        if (comunicatorFragmentActivity.isProfessionalActivity()){
            mRlPrescribe.setVisibility(View.VISIBLE);
        } else {
            mRlPrescribe.setVisibility(View.GONE);
        }

        mRVOldMedicines = (RecyclerView) view.findViewById(R.id.recycle_view_old_medicine);
        mRVOldMedicines.setLayoutManager(new LinearLayoutManager(getActivity()));

        mRVCurrentMedicines = (RecyclerView) view.findViewById(R.id.recycle_view_current_medicine);
        mRVCurrentMedicines.setLayoutManager(new LinearLayoutManager(getActivity()));

        DatabaseReference prescriptionRef = FirebaseHelper.getInstance().
                getPatientDatabaseReference(comunicatorFragmentActivity.getPatientSelected().getId()).
                child(FirebaseHelper.RECOMMENDED_ACTION_KEY).
                child(FirebaseHelper.MEDICINE_KEY);

        prescriptionRef.addValueEventListener(getPrescriptionListener);

    }

    private Map<String, List<Map.Entry<String, String>>> getRecomendationByDate(List<Recomentation> recomentations) {

        Map<String, List<Map.Entry<String, String>>> result = new LinkedHashMap<>();

        for (Recomentation recomentation : recomentations){

            Date startDate = new Date(recomentation.getStartDate());
            String dateStr = Formater.getStringFromDate(startDate);

            if (!result.containsKey(dateStr)){
                result.put(dateStr , new ArrayList<Map.Entry<String, String>>());
            }
            result.get(dateStr).addAll(recomentation.toMap().entrySet());
        }

        Map<String, List<Map.Entry<String, String>>> sorted = new TreeMap<>();
        sorted.putAll(result);

        return sorted;
    }

    private ValueEventListener getPrescriptionListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(rootEventListener);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(PrescribeFoodFragment.class.getName(), databaseError.getDetails());
        }
    };

    ValueEventListener rootEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            try {
                DataSnapshot recomendationSnapshot = dataSnapshot.child(FirebaseHelper.PATIENT_KEY)
                        .child(comunicatorFragmentActivity.getPatientSelected().getId())
                        .child(FirebaseHelper.RECOMMENDED_ACTION_KEY)
                        .child(FirebaseHelper.MEDICINE_KEY);

                DataSnapshot professionalSnapshot = dataSnapshot.child(FirebaseHelper.PROFESSIONAL_KEY);

                List<Recomentation> oldRecomendations = new ArrayList<>();
                List<Recomentation> currentRecomendations = new ArrayList<>();

                for (DataSnapshot entrySnapshot : recomendationSnapshot.getChildren()) {
                    Medicamento medicamento = new Medicamento();
                    Recomentation recomentation = entrySnapshot.getValue(Recomentation.class);

                    medicamento.setName(entrySnapshot.child(FirebaseHelper.MEDICINE_NAME_KEY).getValue(String.class));
                    medicamento.setDosagem(entrySnapshot.child(FirebaseHelper.MEDICINE_DOSAGE_KEY).getValue(String.class));
                    medicamento.setQuantidade(entrySnapshot.child(FirebaseHelper.QUANTITY_KEY).getValue(String.class));
                    medicamento.setNote(entrySnapshot.child(FirebaseHelper.MEDICINE_NOTE_KEY).getValue(String.class));
                    medicamento.setHorario(entrySnapshot.child(FirebaseHelper.MEDICINE_START_HOUR_KEY).getValue(String.class));
                    medicamento.setProfissionalId(entrySnapshot.child(FirebaseHelper.MEDICINE_PROFESSIONAL_KEY).getValue(String.class));

                    medicamento.setProfissionalObject(professionalSnapshot.child(medicamento.getProfissionalId()).getValue(Profissional.class));

                    recomentation.setAction(medicamento);
                    if ( Formater.getCurrentDateWithoutSeconds().compareTo(new Date(recomentation.getFinishDate())) == 1){
                        oldRecomendations.add(recomentation);
                    } else{
                        currentRecomendations.add(recomentation);
                    }
                }

                currentItemExpandableSimpleListAdapter = new ItemExpandableSimpleListAdapter(getRecomendationByDate(currentRecomendations));
                mRVCurrentMedicines.setAdapter(currentItemExpandableSimpleListAdapter);

                oldItemExpandableSimpleListAdapter = new ItemExpandableSimpleListAdapter(getRecomendationByDate(oldRecomendations));
                mRVOldMedicines.setAdapter(oldItemExpandableSimpleListAdapter);

            }catch (NullPointerException e){
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

}