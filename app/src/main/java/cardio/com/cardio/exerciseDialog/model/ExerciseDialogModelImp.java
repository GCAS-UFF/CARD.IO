package cardio.com.cardio.exerciseDialog.model;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import cardio.com.cardio.common.Firebase.FirebaseHelper;
import cardio.com.cardio.common.model.model.Exercicio;
import cardio.com.cardio.common.model.model.Recomentation;
import cardio.com.cardio.common.util.PreferencesUtils;
import cardio.com.cardio.exerciseDialog.presenter.ExerciseDialogPresenter;

public class ExerciseDialogModelImp implements ExerciseDialogModel {

    private Context context;
    private ExerciseDialogPresenter mExerciseDialogPresenterImp;

    public ExerciseDialogModelImp(Context context) {
        this.context = context;
    }

    public void setmExerciseDialogPresenterImp(ExerciseDialogPresenter mExerciseDialogPresenterImp) {
        this.mExerciseDialogPresenterImp = mExerciseDialogPresenterImp;
    }

    @Override
    public void initializeMetaDataListeners() {
        FirebaseHelper.symptomsDatabaseReference.addListenerForSingleValueEvent(getSymptomsMetadata);
    }

    @Override
    public void initializeExerciseListener(String id) {
        FirebaseHelper.getInstance().
                getPatientDatabaseReference(getCurrentPatientKey()).
                child(FirebaseHelper.RECOMMENDED_ACTION_KEY).
                child(FirebaseHelper.EXERCICIO_KEY).
                child(id).
                addListenerForSingleValueEvent(exerciseDialogEventListener);
    }

    @Override
    public void saveIntoFirebase(Recomentation recomendation) {
        try {
            DatabaseReference mDbRef = FirebaseHelper.getInstance()
                    .getPatientDatabaseReference(getCurrentPatientKey())
                    .child(FirebaseHelper.PERFORMED_ACTION_KEY)
                    .child(FirebaseHelper.EXERCICIO_KEY);

            recomendation.setId(mDbRef.push().getKey());
            mDbRef.child(recomendation.getId()).child(
                    FirebaseHelper.EXERCISE_NAME_KEY).setValue(((Exercicio) recomendation.getAction()).getName());
            mDbRef.child(recomendation.getId()).child(
                    FirebaseHelper.EXERCISE_INTENSITY_KEY).setValue(((Exercicio) recomendation.getAction()).getIntensity());
            mDbRef.child(recomendation.getId()).child(
                    FirebaseHelper.EXERCISE_DURATION_KEY).setValue(((Exercicio) recomendation.getAction()).getDuration());
            mDbRef.child(recomendation.getId()).child(
                    FirebaseHelper.EXERCISE_SYMPTOMS_KEY).setValue(((Exercicio) recomendation.getAction()).getSymptons());
            mDbRef.child(recomendation.getId()).child(
                    FirebaseHelper.PERFORMED_EXECUTED_DATE).setValue((recomendation.getAction()).getExecutedDate());

            mExerciseDialogPresenterImp.finishSendRecomendation(true);
        }catch (Exception e){
            e.printStackTrace();
            mExerciseDialogPresenterImp.finishSendRecomendation(false);
        }
    }

    public String getCurrentPatientKey() {
        return PreferencesUtils.getString(context, PreferencesUtils.CURRENT_PATIENT_KEY);
    }

    private ValueEventListener exerciseDialogEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            Recomentation recomentation = getRecomendationFromDataSnapshot(dataSnapshot);
            mExerciseDialogPresenterImp.finishLoadedMedicationDialogData(recomentation);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    private Recomentation getRecomendationFromDataSnapshot(DataSnapshot dataSnapshot){
        Recomentation recomentation = null;
        try{
            Exercicio exercicio = new Exercicio();
            recomentation = dataSnapshot.getValue(Recomentation.class);
            recomentation.setId(dataSnapshot.getKey());

            exercicio.setName(dataSnapshot.child(FirebaseHelper.EXERCISE_NAME_KEY).getValue(String.class));
            exercicio.setIntensity(dataSnapshot.child(FirebaseHelper.EXERCISE_INTENSITY_KEY).getValue(String.class));
            exercicio.setDuration(dataSnapshot.child(FirebaseHelper.EXERCISE_DURATION_KEY).getValue(Integer.class));
            exercicio.setSymptons((Map)dataSnapshot.child(FirebaseHelper.EXERCISE_SYMPTOMS_KEY).getValue());

            recomentation.setAction(exercicio);
        } catch (Exception e){
            e.printStackTrace();
        }

        return recomentation;
    }

    private ValueEventListener getSymptomsMetadata = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            Map<String, String> options = new HashMap<>();

            for (DataSnapshot entrySnapshot : dataSnapshot.getChildren()){
                options.put(entrySnapshot.getKey(), entrySnapshot.getValue(String.class));
            }

            mExerciseDialogPresenterImp.finishLoadSymptomsMeadata(options);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
}
