package cardio.uff.cardio.monitoratingThreshold;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cardio.uff.cardio.R;
import cardio.uff.cardio.common.Firebase.FirebaseHelper;
import cardio.uff.cardio.common.model.model.MedicaoDadosFisiologicos;
import cardio.uff.cardio.common.model.model.Recomentation;
import cardio.uff.cardio.common.util.Formater;
import cardio.uff.cardio.common.util.NotificationUtils;
import cardio.uff.cardio.common.util.PreferencesUtils;
import cardio.uff.cardio.patiente.activities.MainActivityPatient;

public class ThresholdMonitoratingWeigth implements ThreshholdMonitorating{
    private Context mContext;
    private static final int QUANTITY_OF_DAYS_TO_CHECK  = 2;
    private static final int WEIGHT_THRESHOLD = 3;
    private static final int notificationId = 234555;


    public ThresholdMonitoratingWeigth(Context context) {
        this.mContext = context;
    }

    @Override
    public void start() {
        if (getCurrentPatientKey() != null) {
            FirebaseHelper.getInstance().
                    getPatientDatabaseReference(getCurrentPatientKey())
                    .child(FirebaseHelper.PERFORMED_ACTION_KEY)
                    .child(FirebaseHelper.MEDICAO_DADOS_FISIOLOGICOS_KEY).
                    addValueEventListener(monitorateWeightThreshold);

        }
    }

    public String getCurrentPatientKey() {
        return PreferencesUtils.getString(mContext, PreferencesUtils.CURRENT_PATIENT_KEY);
    }

    private List<Recomentation> getRecomendationFromDataSnapshot(DataSnapshot dataSnapshot){
        List<Recomentation> recomentationList = new ArrayList<>();

        try {
            for (DataSnapshot entrySnapshot : dataSnapshot.getChildren()) {
                MedicaoDadosFisiologicos medicaoDadosFisiologicos = entrySnapshot.getValue(MedicaoDadosFisiologicos.class);
                Recomentation recomentation = entrySnapshot.getValue(Recomentation.class);
                recomentation.setAction(medicaoDadosFisiologicos);
                recomentationList.add(recomentation);
            }

        } catch (NullPointerException e){
            e.printStackTrace();
        }

        return recomentationList;

    }

    private float getCurrentWeight (List<Recomentation> recomentationList){
        String currentDateStr = Formater.getStringFromDate(new Date());
        Recomentation currentRecomendation = null;

        for (Recomentation recomentation : recomentationList){
            String dateStr = Formater.getStringFromDate(new Date(recomentation.getAction().getExecutedDate()));
            if (dateStr.equals(currentDateStr)){

                if (currentRecomendation == null || currentRecomendation.getAction().getExecutedDate() <
                                recomentation.getAction().getExecutedDate()){

                    currentRecomendation = recomentation;
                }
            }
        }

        if (currentRecomendation == null)
            return 0;
        else
            return ((MedicaoDadosFisiologicos) currentRecomendation.getAction()).getWeigth();
    }

    private List<Float> getWeightsForDay (List<Recomentation> recomentationList, long day){
        String currentDateStr = Formater.getStringFromDate(new Date(day));
        List<Float> result = new ArrayList<>();

        for (Recomentation recomentation : recomentationList){
            String dateStr = Formater.getStringFromDate(new Date(recomentation.getAction().getExecutedDate()));
            if (dateStr.equals(currentDateStr)){
                result.add(((MedicaoDadosFisiologicos) recomentation.getAction()).getWeigth());
            }
        }

        return result;
    }


    private List<Float> getWeightFromLastDays(List<Recomentation> recomentationList){
        List<Float> result = new ArrayList<>();
        long currentDate = new Date().getTime();
        long dayInMiliseconds = 86400000;

        for (int i = 0; i < QUANTITY_OF_DAYS_TO_CHECK && currentDate > 0; i++){
            result.addAll(getWeightsForDay(recomentationList, currentDate));
            currentDate -= dayInMiliseconds;
        }
        return result;
    }

    private void testWeightTrashHold(float currentWeight, List<Float> weightFromLastDays) {

        String title = mContext.getResources().getString(R.string.threshold_notification_title);
        Intent intent = new Intent(mContext, MainActivityPatient.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("trocaTela", R.id.ll_controle_peso);

        if (currentWeight > 0){
            for (Float weight : weightFromLastDays){
                if (weight >0 && currentWeight >= weight + WEIGHT_THRESHOLD){
                    String content = mContext.getResources().getString(R.string.message_weight_threshold);
                    NotificationUtils.getInstance().showNotification(mContext, title, content, intent, notificationId);
                }
            }
        }

    }

    private ValueEventListener monitorateWeightThreshold = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            List<Recomentation> recomentationList = getRecomendationFromDataSnapshot(dataSnapshot);
            testWeightTrashHold (getCurrentWeight(recomentationList),
                    getWeightFromLastDays(recomentationList));
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
}
