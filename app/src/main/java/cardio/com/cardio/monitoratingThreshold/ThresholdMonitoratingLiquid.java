package cardio.com.cardio.monitoratingThreshold;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cardio.com.cardio.R;
import cardio.com.cardio.common.Firebase.FirebaseHelper;
import cardio.com.cardio.common.model.model.Action;
import cardio.com.cardio.common.model.model.Alimentacao;
import cardio.com.cardio.common.model.model.Recomentation;
import cardio.com.cardio.common.util.Formater;
import cardio.com.cardio.common.util.NotificationUtils;
import cardio.com.cardio.common.util.PreferencesUtils;
import cardio.com.cardio.patiente.activities.MainActivityPatient;

public class ThresholdMonitoratingLiquid implements ThreshholdMonitorating{

    private Context mContext;

    public ThresholdMonitoratingLiquid(Context context) {
        this.mContext = context;
    }

    public void start(){
        FirebaseHelper.getInstance().
                getPatientDatabaseReference(getCurrentPatientKey()).addValueEventListener(monitorateLiquidThreshold);
    }

    public String getCurrentPatientKey() {
        return PreferencesUtils.getString(mContext, PreferencesUtils.CURRENT_PATIENT_KEY);
    }

    private List<Recomentation> getAlimentationListFromDataSnapshot(DataSnapshot dataSnapshot){
        List<Recomentation> recomentationList = new ArrayList<>();

        try {
            for (DataSnapshot entryDataSnapshot : dataSnapshot.getChildren()){
                Alimentacao alimentacao = entryDataSnapshot.getValue(Alimentacao.class);
                Recomentation recomentation = entryDataSnapshot.getValue(Recomentation.class);
                recomentation.setId(entryDataSnapshot.getKey());

                alimentacao.setFood(entryDataSnapshot.child(FirebaseHelper.LIQUID_KEY).getValue(String.class));
                alimentacao.setQuantidade(entryDataSnapshot.child(FirebaseHelper.QUANTITY_KEY).getValue(Integer.class));

                try {
                    Action action = entryDataSnapshot.getValue(Action.class);
                    long executedDate = action.getExecutedDate();
                    alimentacao.setExecutedDate(executedDate);
                    alimentacao.setPerformed(true);
                }catch (Exception e){
                    e.printStackTrace();
                }

                recomentation.setAction(alimentacao);
                recomentationList.add(recomentation);
            }

        } catch (NullPointerException e){
            e.printStackTrace();
        }

        return recomentationList;

    }

    private double getRecomendedLiquidForToday(List<Recomentation> recomentations) throws ParseException {
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
        return ((Alimentacao) lastValidRecomendation.getAction()).getQuantidade();
    }

    private double getPerformedLiquidForToday(List<Recomentation> recomentations) {
        double liquidPerformed = 0;
        String currentDateStr = Formater.getStringFromDate(new Date());

        for (Recomentation recomentation : recomentations){
            String dateStr = Formater.getStringFromDate(new Date(recomentation.getAction().getExecutedDate()));

            if (dateStr.equals(currentDateStr)){
                liquidPerformed += ((Alimentacao) recomentation.getAction()).getQuantidade();
            }
        }
        return liquidPerformed;
    }

    private void testLiquidTrashHold (double recomended, double performed){

        Log.d("debug_kelly", recomended + " " + performed);

        String title = mContext.getResources().getString(R.string.threshold_notification_title);
        Intent intent = new Intent(mContext, MainActivityPatient.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("trocaTela", R.id.ll_alimentacao);

        if (recomended != 0 && performed != 0) {
            if (recomended < performed) {
                String content = mContext.getResources().getString(R.string.message_surpassed_liquid_threshold);
                NotificationUtils.getInstance().showNotification(mContext, title, content,intent);

            } else if (recomended * 0.75 <= performed) {
                String content = mContext.getResources().getString(R.string.message_liquid_threshold);
                NotificationUtils.getInstance().showNotification(mContext, title, content, intent);
            }
        }
    }

    private ValueEventListener monitorateLiquidThreshold = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            DataSnapshot recomended = dataSnapshot.child(FirebaseHelper.RECOMMENDED_ACTION_KEY)
                    .child(FirebaseHelper.ALIMENTACAO_KEY);

            DataSnapshot performed = dataSnapshot.child(FirebaseHelper.PERFORMED_ACTION_KEY)
                    .child(FirebaseHelper.ALIMENTACAO_KEY);

            List<Recomentation> recomendedList = getAlimentationListFromDataSnapshot(recomended);
            List<Recomentation> performedList = getAlimentationListFromDataSnapshot(performed);

            try {

                testLiquidTrashHold (getRecomendedLiquidForToday(recomendedList),
                        getPerformedLiquidForToday(performedList));

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
}
