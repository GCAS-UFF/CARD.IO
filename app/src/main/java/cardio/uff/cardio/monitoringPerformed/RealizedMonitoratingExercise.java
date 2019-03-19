package cardio.uff.cardio.monitoringPerformed;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cardio.uff.cardio.R;
import cardio.uff.cardio.common.Firebase.FirebaseHelper;
import cardio.uff.cardio.common.model.model.Action;
import cardio.uff.cardio.common.model.model.Exercicio;
import cardio.uff.cardio.common.model.model.Recomentation;
import cardio.uff.cardio.common.util.Formater;
import cardio.uff.cardio.common.util.NotificationUtils;
import cardio.uff.cardio.common.util.PreferencesUtils;
import cardio.uff.cardio.patiente.activities.MainActivityPatient;

public class RealizedMonitoratingExercise implements RealizedMonitorating {

    private Context mContext;

    public RealizedMonitoratingExercise(Context context) {
        this.mContext = context;
    }

    @Override
    public void start() {
        FirebaseHelper.getInstance().
                getPatientDatabaseReference(getCurrentPatientKey())
                .addListenerForSingleValueEvent(monitorateExerciseRealized);
    }

    public String getCurrentPatientKey() {
        return PreferencesUtils.getString(mContext, PreferencesUtils.CURRENT_PATIENT_KEY);
    }

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

    private void testExerciseRealized(int recomended, int performed){
        String title = mContext.getResources().getString(R.string.threshold_notification_title);
        Intent intent = new Intent(mContext, MainActivityPatient.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("trocaTelaHome", R.id.ll_exercicios);

        if (recomended != 0 && performed < recomended ) {
            String content = mContext.getResources().getString(R.string.message_excercise_monitoring);
            NotificationUtils.getInstance().showNotification(mContext, title, content, intent);
        }
    }

    private int getPerformedExerciseForToday(List<Recomentation> recomentations) {
        String currentDateStr = Formater.getStringFromDate(new Date());
        List<Recomentation> performedRecomendations = new ArrayList<>();

        for (Recomentation recomentation : recomentations){
            String dateStr = Formater.getStringFromDate(new Date(recomentation.getAction().getExecutedDate()));

            if (dateStr.equals(currentDateStr)){
                performedRecomendations.add(recomentation);
            }
        }
        if (performedRecomendations == null) return 0;
        return performedRecomendations.size();
    }

    private int getRecomendedExerciseForToday(List<Recomentation> recomentations) throws ParseException {
        Date currentDate = new Date();
        Recomentation lastValidRecomendation = null;

        for (Recomentation recomentation : recomentations){
            Date startDate = new Date(recomentation.getStartDate());
            Date finishDate = new Date(recomentation.getFinishDate());

            if (Formater.compareDates(startDate, currentDate) <= 0 &&
                    Formater.compareDates(finishDate, currentDate) >=0){

                if (lastValidRecomendation == null)
                    lastValidRecomendation = recomentation;
                else if (Formater.compareDates(new Date(lastValidRecomendation.getStartDate()), startDate) < 0){
                    lastValidRecomendation = recomentation;
                }
            }
        }

        if (lastValidRecomendation == null) return 0;
        return lastValidRecomendation.getFrequencyByDay();
    }

    private ValueEventListener monitorateExerciseRealized = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            DataSnapshot recomended = dataSnapshot.child(FirebaseHelper.RECOMMENDED_ACTION_KEY)
                    .child(FirebaseHelper.EXERCICIO_KEY);

            DataSnapshot performed = dataSnapshot.child(FirebaseHelper.PERFORMED_ACTION_KEY)
                    .child(FirebaseHelper.EXERCICIO_KEY);

            List<Recomentation> recomendedList = getRecomendationListFromDataSnapshot(recomended);
            List<Recomentation> performedList = getRecomendationListFromDataSnapshot(performed);

            try {

                testExerciseRealized(getRecomendedExerciseForToday(recomendedList),
                        getPerformedExerciseForToday(performedList));

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
}
