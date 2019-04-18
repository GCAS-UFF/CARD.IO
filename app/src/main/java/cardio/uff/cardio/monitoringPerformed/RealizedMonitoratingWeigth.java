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
import cardio.uff.cardio.common.model.model.MedicaoDadosFisiologicos;
import cardio.uff.cardio.common.model.model.Recomentation;
import cardio.uff.cardio.common.util.Formater;
import cardio.uff.cardio.common.util.NotificationUtils;
import cardio.uff.cardio.common.util.PreferencesUtils;
import cardio.uff.cardio.patiente.activities.MainActivityPatient;

public class RealizedMonitoratingWeigth implements RealizedMonitorating {

    private Context mContext;
    private static int notificationId = 5698076;


    public RealizedMonitoratingWeigth(Context context) {
        this.mContext = context;
    }

    @Override
    public void start() {
        FirebaseHelper.getInstance().
                getPatientDatabaseReference(getCurrentPatientKey())
                .addListenerForSingleValueEvent(monitorateWeightRealized);
    }

    public String getCurrentPatientKey() {
        return PreferencesUtils.getString(mContext, PreferencesUtils.CURRENT_PATIENT_KEY);
    }

    private List<Recomentation> getRecomendationListFromDataSnapshot(DataSnapshot dataSnapshot){
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

    private int getPerformedWeightForToday (List<Recomentation> recomentationList){
        String currentDateStr = Formater.getStringFromDate(new Date());
        List<Recomentation> performedRecomendations = new ArrayList<>();

        for (Recomentation recomentation : recomentationList){
            String dateStr = Formater.getStringFromDate(new Date(recomentation.getAction().getExecutedDate()));
            if (dateStr.equals(currentDateStr)){
                performedRecomendations.add(recomentation);
            }
        }

        if (performedRecomendations == null) return 0;
        return performedRecomendations.size();

    }

    private int getRecomendedWeightForToday(List<Recomentation> recomentations) throws ParseException {
        Date currentDate = new Date();
        Recomentation lastValidRecomendation = null;

        for (Recomentation recomentation : recomentations){
            Date startDate = new Date(recomentation.getStartDate());
            Date finishDate = new Date(recomentation.getFinishDate());

            if (Formater.compareDatesWithoutMinutes(startDate, currentDate) <= 0 &&
                    Formater.compareDatesWithoutMinutes(finishDate, currentDate) >=0){

                if (lastValidRecomendation == null)
                    lastValidRecomendation = recomentation;
                else if (Formater.compareDatesWithoutMinutes(new Date(lastValidRecomendation.getStartDate()), startDate) < 0){
                    lastValidRecomendation = recomentation;
                }
            }
        }

        if (lastValidRecomendation == null) return 0;
        return lastValidRecomendation.getFrequencyByDay();
    }

    private void testWeightRealized(int recomended, int performed){
        String title = mContext.getResources().getString(R.string.threshold_notification_title);
        Intent intent = new Intent(mContext, MainActivityPatient.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("trocaTelaHome", R.id.ll_controle_peso);

        if (recomended != 0 && performed < recomended ) {
            String content = mContext.getResources().getString(R.string.message_weight_monitoring);
            NotificationUtils.getInstance().showNotification(mContext, title, content, intent, notificationId);
        }
    }

    private ValueEventListener monitorateWeightRealized = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            DataSnapshot recomended = dataSnapshot.child(FirebaseHelper.RECOMMENDED_ACTION_KEY)
                    .child(FirebaseHelper.MEDICAO_DADOS_FISIOLOGICOS_KEY);

            DataSnapshot performed = dataSnapshot.child(FirebaseHelper.PERFORMED_ACTION_KEY)
                    .child(FirebaseHelper.MEDICAO_DADOS_FISIOLOGICOS_KEY);

            List<Recomentation> recomendedList = getRecomendationListFromDataSnapshot(recomended);
            List<Recomentation> performedList = getRecomendationListFromDataSnapshot(performed);

            try {

                testWeightRealized(getRecomendedWeightForToday(recomendedList),
                        getPerformedWeightForToday(performedList));

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
}
