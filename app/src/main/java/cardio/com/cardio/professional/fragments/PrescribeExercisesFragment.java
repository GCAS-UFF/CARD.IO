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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import cardio.com.cardio.R;
import cardio.com.cardio.common.Firebase.FirebaseHelper;
import cardio.com.cardio.common.adapters.ItemExpandableSimpleListAdapter;
import cardio.com.cardio.common.model.model.Exercicio;
import cardio.com.cardio.common.model.model.Recomentation;
import cardio.com.cardio.common.util.Formater;
import cardio.com.cardio.professional.ComunicatorFragmentActivity;


public class PrescribeExercisesFragment extends Fragment {

    private RelativeLayout mRlPrescribe;
    private RecyclerView mRVHistory;
    private ComunicatorFragmentActivity comunicatorFragmentActivity;
    private ItemExpandableSimpleListAdapter itemExpandableSimpleListAdapter;

    public PrescribeExercisesFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_prescribe_exercises, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context != null)
            comunicatorFragmentActivity = (ComunicatorFragmentActivity) context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRlPrescribe = (RelativeLayout) view.findViewById(R.id.rl_prescribe_food);
        mRlPrescribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PrescribeExercisesDialogFragment prescribeExercisesDialogFragment = new PrescribeExercisesDialogFragment();
                prescribeExercisesDialogFragment.show(getActivity().getSupportFragmentManager(), "dialogFragment");
            }
        });

        if (comunicatorFragmentActivity.isProfessionalActivity()){
            mRlPrescribe.setVisibility(View.VISIBLE);
        } else {
            mRlPrescribe.setVisibility(View.GONE);
        }

        mRVHistory = (RecyclerView) view.findViewById(R.id.recycle_view_prescribe_food);
        mRVHistory.setLayoutManager(new LinearLayoutManager(getActivity()));

        DatabaseReference prescriptionRef = FirebaseHelper.getInstance().
                getPatientDatabaseReference(comunicatorFragmentActivity.getPatientSelected().getId()).
                child(FirebaseHelper.RECOMMENDED_ACTION_KEY).
                child(FirebaseHelper.EXERCICIO_KEY);

        prescriptionRef.addValueEventListener(getPrescriptionListener);

        DatabaseReference ref = FirebaseHelper.getInstance().getPatientDatabaseReference(
                comunicatorFragmentActivity.getPatientSelected().getId()).
                child(FirebaseHelper.PERFORMED_ACTION_KEY).
                child(FirebaseHelper.EXERCICIO_KEY);

        ref.addValueEventListener(updateList);
    }

    private Map<String, List<Map.Entry<String, String>>> getRecomendationByDate(List<Recomentation> recomentations) {

        Map<String, List<Map.Entry<String, String>>> result = new LinkedHashMap<>();

        for (Recomentation recomentation : recomentations){

            long dayInMiliseconds = 86400000;
            Date startDate = new Date(recomentation.getStartDate());
            Date finishDate = new Date(recomentation.getFinishDate() + dayInMiliseconds);

            while (startDate.before(finishDate)){

                String dateStr = Formater.getStringFromDate(startDate);

                if (!result.containsKey(dateStr)){
                    result.put(dateStr , new ArrayList<Map.Entry<String, String>>());
                }
                result.get(dateStr).addAll(recomentation.toMap().entrySet());

                startDate.setTime(startDate.getTime() + dayInMiliseconds);
            }
        }

        Map<String, List<Map.Entry<String, String>>> sorted = new TreeMap<>();
        sorted.putAll(result);

        return sorted;
    }

    private Map<String, List<Map.Entry<String, String>>> mergeMaps (Map<String, List<Map.Entry<String, String>>> recomendationsMap,
                                                                    Map<String, List<Map.Entry<String, String>>> realizedActionsMap){

        Map<String, List<Map.Entry<String, String>>> result = recomendationsMap;

        for (Map.Entry<String, List<Map.Entry<String, String>>> entry : realizedActionsMap.entrySet()){
            if (!result.containsKey(entry.getKey())){
                result.put(entry.getKey() , new ArrayList<Map.Entry<String, String>>());
            }
            result.get(entry.getKey()).addAll(entry.getValue());
        }

        return result;
    }

    private Map<String, List<Map.Entry<String, String>>> getExercisesByDate(List<Exercicio> exercicioList) {

        Map<String, List<Map.Entry<String, String>>> result = new LinkedHashMap<>();

        for (Exercicio exercicio : exercicioList){

            String dateStr = Formater.getStringFromDate(new Date(exercicio.getExecutedDate()));

            if (!result.containsKey(dateStr)){
                result.put(dateStr , new ArrayList<Map.Entry<String, String>>());
            }
            result.get(dateStr).addAll(exercicio.toMap().entrySet());

        }

        TreeMap<String, List<Map.Entry<String, String>>> sorted = new TreeMap<>();
        sorted.putAll(result);

        return sorted.descendingMap();
    }

    private ValueEventListener getPrescriptionListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            try {

                List<Recomentation> recomentations = new ArrayList<>();

                for (DataSnapshot entrySnapshot : dataSnapshot.getChildren()) {
                    Exercicio exercicio = new Exercicio();
                    Recomentation recomentation = entrySnapshot.getValue(Recomentation.class);

                    exercicio.setExercise(entrySnapshot.child(FirebaseHelper.EXCERCISE_NAME_KEY).getValue(String.class));
                    exercicio.setIntensity(entrySnapshot.child(FirebaseHelper.EXCERCISE_INTENSITY_KEY).getValue(String.class));
                    if (entrySnapshot.child(FirebaseHelper.EXERCISE_DURATION_KEY).getValue(Integer.class) != null) {

                        exercicio.setDuration(entrySnapshot.child(FirebaseHelper.EXERCISE_DURATION_KEY).getValue(Integer.class));
                    }

                    recomentation.setAction(exercicio);

                    recomentations.add(recomentation);
                }

                itemExpandableSimpleListAdapter = new ItemExpandableSimpleListAdapter(getRecomendationByDate(recomentations));
                mRVHistory.setAdapter(itemExpandableSimpleListAdapter);

                DatabaseReference ref = FirebaseHelper.getInstance().getPatientDatabaseReference(
                        comunicatorFragmentActivity.getPatientSelected().getId()).
                        child(FirebaseHelper.PERFORMED_ACTION_KEY).
                        child(FirebaseHelper.EXERCICIO_KEY);

                ref.addListenerForSingleValueEvent(getRealizedActionsListListener);

            }catch (NullPointerException e){
                e.printStackTrace();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(PrescribeFoodFragment.class.getName(), databaseError.getDetails());
        }
    };

    private ValueEventListener getRealizedActionsListListener = new ValueEventListener() {
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            try {

                List<Exercicio> exercicioList = new ArrayList<>();

                for (DataSnapshot entrySnapshot : dataSnapshot.getChildren()) {
                    Exercicio exercicio = entrySnapshot.getValue(Exercicio.class);
                    exercicio.setPerformed(true);

                    exercicioList.add(exercicio);
                }

                itemExpandableSimpleListAdapter = new ItemExpandableSimpleListAdapter(
                        mergeMaps(itemExpandableSimpleListAdapter.getEntrysByDateMap(),
                                getExercisesByDate(exercicioList)));

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

    private ValueEventListener updateList = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            DatabaseReference prescriptionRef = FirebaseHelper.getInstance().
                    getPatientDatabaseReference(comunicatorFragmentActivity.getPatientSelected().getId()).
                    child(FirebaseHelper.RECOMMENDED_ACTION_KEY).
                    child(FirebaseHelper.EXERCICIO_KEY);

            prescriptionRef.addValueEventListener(getPrescriptionListener);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
}