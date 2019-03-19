package cardio.uff.cardio.exercise.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cardio.uff.cardio.common.Firebase.FirebaseHelper;
import cardio.uff.cardio.common.model.model.Action;
import cardio.uff.cardio.common.model.model.Exercicio;
import cardio.uff.cardio.common.model.model.Recomentation;
import cardio.uff.cardio.common.model.view.CustomMapObject;
import cardio.uff.cardio.common.model.view.CustomMapsList;
import cardio.uff.cardio.common.model.view.CustomPair;
import cardio.uff.cardio.common.util.Formater;
import cardio.uff.cardio.common.util.PreferencesUtils;
import cardio.uff.cardio.exercise.presenter.ExercisePresenter;

public class ExerciseModelImp implements ExerciseModel {

    private Context mContext;
    private ExercisePresenter mExercisePresenterImp;

    public ExerciseModelImp(Context context) {
        this.mContext = context;
    }

    public void setmExercisePresenterImp(ExercisePresenter mExercisePresenterImp) {
        this.mExercisePresenterImp = mExercisePresenterImp;
    }

    @Override
    public boolean isProfessionalProfile() {
        return PreferencesUtils.getBollean(mContext, PreferencesUtils.IS_CURRENT_USER_PROFESSIONAL);
    }

    @Override
    public void setRecommendationListener() {
        FirebaseHelper.getInstance().
                getPatientDatabaseReference(getCurrentPatientKey()).
                child(FirebaseHelper.RECOMMENDED_ACTION_KEY).
                child(FirebaseHelper.EXERCICIO_KEY).
                addListenerForSingleValueEvent(exerciseRecommendedEventListener);
    }

    @Override
    public String getCurrentPatientKey() {
        return PreferencesUtils.getString(mContext, PreferencesUtils.CURRENT_PATIENT_KEY);
    }

    @Override
    public List<CustomMapsList> getRecomendationByDate(List<Recomentation> recomendationList) {
        List<CustomMapsList> customMapsLists = new ArrayList<>();
        for (Recomentation recomentation : recomendationList) {
            addCustomMapListForEachRecomendationDay(recomentation, customMapsLists);
        }

        Formater.sortCustomMapListsWhereTitleIsDate(customMapsLists);

        return customMapsLists;
    }

    @Override
    public List<CustomMapsList> getPerformmedByDate(List<Recomentation> recomendationList) {
        List<CustomMapsList> customMapsLists = new ArrayList<>();
        for (Recomentation recomentation : recomendationList){
            String dateStr = Formater.getStringFromDate(new Date(recomentation.getAction().getExecutedDate()));

            if (!Formater.containsInMapsLists(dateStr, customMapsLists)) {
                CustomMapsList customMapsList = new CustomMapsList(dateStr, new ArrayList<CustomMapObject>());
                customMapsLists.add(customMapsList);
            }

            Formater.addIntoMapsLists(dateStr, getCustomMapObjectFromRecomendation(recomentation, true), customMapsLists);
        }

        Formater.sortCustomMapListsWhereTitleIsDate(customMapsLists);

        return customMapsLists;
    }

    private void addCustomMapListForEachRecomendationDay(Recomentation recomentation, List<CustomMapsList> customMapsLists) {
        long dayInMiliseconds = 86400000;
        Date startDate = new Date(recomentation.getStartDate());
        Date finishDate = new Date(recomentation.getFinishDate() + dayInMiliseconds);

        while (startDate.before(finishDate)) {

            String dateStr = Formater.getStringFromDate(startDate);

            if (!Formater.containsInMapsLists(dateStr, customMapsLists)) {
                CustomMapsList customMapsList = new CustomMapsList(dateStr, new ArrayList<CustomMapObject>());
                customMapsLists.add(customMapsList);
            }

            Formater.addIntoMapsLists(dateStr, getCustomMapObjectFromRecomendation(recomentation, false), customMapsLists);

            startDate.setTime(startDate.getTime() + dayInMiliseconds);
        }
    }

    private CustomMapObject getCustomMapObjectFromRecomendation(Recomentation recomentation, boolean isPerformed) {
        Map<String, String> values = recomentation.toMap();
        List<CustomPair> customPairs = new ArrayList<>();

        for (Map.Entry<String, String> entry: values.entrySet()) {
            customPairs.add(new CustomPair(entry.getKey(), entry.getValue()));
        }

        return new CustomMapObject(recomentation.getId(), customPairs, !isPerformed);
    }

    private ValueEventListener exerciseRecommendedEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            List<Recomentation> recomendationList = getRecomendationListFromDataSnapshot(dataSnapshot);
            mExercisePresenterImp.finishLoadRecommendedExerciseData(recomendationList);
            setPerformedListener();
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    private void setPerformedListener() {
        FirebaseHelper.getInstance().
                getPatientDatabaseReference(getCurrentPatientKey()).
                child(FirebaseHelper.PERFORMED_ACTION_KEY).
                child(FirebaseHelper.EXERCICIO_KEY).
                addListenerForSingleValueEvent(exercisePerformedEventListener);
    }

    private ValueEventListener exercisePerformedEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            List<Recomentation> recomendationList = getRecomendationListFromDataSnapshot(dataSnapshot);
            mExercisePresenterImp.finishedLoadedPerformedLoquidData(recomendationList);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    private List<Recomentation> getRecomendationListFromDataSnapshot(DataSnapshot dataSnapshot) {
        List<Recomentation> recomentationList = new ArrayList<>();

        try{
            for (DataSnapshot entryDataSnapshot : dataSnapshot.getChildren()){
                Exercicio exercicio = entryDataSnapshot.getValue(Exercicio.class);
                Recomentation recomentation = entryDataSnapshot.getValue(Recomentation.class);
                recomentation.setId(entryDataSnapshot.getKey());

                exercicio.setDuration(entryDataSnapshot.child(FirebaseHelper.EXERCISE_DURATION_KEY).getValue(Integer.class));
                exercicio.setName(entryDataSnapshot.child(FirebaseHelper.EXERCISE_NAME_KEY).getValue(String.class));
                exercicio.setIntensity(entryDataSnapshot.child(FirebaseHelper.EXERCISE_INTENSITY_KEY).getValue(String.class));
                /*** Como pegar um mapa? ***/
//                exercicio.setSymptons();

                try {
                    Action action = entryDataSnapshot.getValue(Action.class);
                    long executedDate = action.getExecutedDate();
                    exercicio.setExecutedDate(executedDate);
                    exercicio.setPerformed(true);
                } catch (Exception e){
                    e.printStackTrace();
                }

                recomentation.setAction(exercicio);
                recomentationList.add(recomentation);
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        return recomentationList;
    }
}
