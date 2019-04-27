package cardio.uff.cardio.monitoringMedication;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cardio.uff.cardio.alarms.AlarmForMedication;
import cardio.uff.cardio.common.Firebase.FirebaseHelper;
import cardio.uff.cardio.common.model.model.Action;
import cardio.uff.cardio.common.model.model.Medicamento;
import cardio.uff.cardio.common.model.model.Recomentation;
import cardio.uff.cardio.common.util.Formater;
import cardio.uff.cardio.common.util.PreferencesUtils;

public class MedicationMonitorating {
    private Context mContext;

    public MedicationMonitorating(Context context) {
        this.mContext = context;
    }

    public void start() {
        if (getCurrentPatientKey() != null) {
            FirebaseHelper.getInstance().
                    getPatientDatabaseReference(getCurrentPatientKey()).
                    child(FirebaseHelper.RECOMMENDED_ACTION_KEY).
                    child(FirebaseHelper.MEDICINE_KEY).
                    addValueEventListener(monitorateMedication);
        }
    }

    public String getCurrentPatientKey() {
        return PreferencesUtils.getString(mContext, PreferencesUtils.CURRENT_PATIENT_KEY);
    }

    private List<Recomentation> getRecomendationListFromDataSnapshot(DataSnapshot dataSnapshot) {
        List<Recomentation> recomentationList = new ArrayList<>();

        try {

            for (DataSnapshot entrySnapshot : dataSnapshot.getChildren()) {
                Medicamento medicamento = entrySnapshot.getValue(Medicamento.class);
                Recomentation recomendation = entrySnapshot.getValue(Recomentation.class);
                recomendation.setId(entrySnapshot.getKey());

                medicamento.setName(entrySnapshot.child(FirebaseHelper.MEDICINE_NAME_KEY).getValue(String.class));
                medicamento.setDosagem(entrySnapshot.child(FirebaseHelper.MEDICINE_DOSAGE_KEY).getValue(String.class));
                medicamento.setQuantidade(entrySnapshot.child(FirebaseHelper.QUANTITY_KEY).getValue(String.class));
                medicamento.setObservacao(entrySnapshot.child(FirebaseHelper.MEDICINE_NOTE_KEY).getValue(String.class));
                medicamento.setHorario(entrySnapshot.child(FirebaseHelper.MEDICINE_START_HOUR_KEY).getValue(String.class));

                try {
                    medicamento.setHorarios(getHorarios(medicamento.getHorario(), recomendation.getFrequencyByDay()));

                } catch (Exception e){
                    e.printStackTrace();
                }

                recomendation.setAction(medicamento);
                recomentationList.add(recomendation);
            }

            return recomentationList;

        }catch (NullPointerException e){
            e.printStackTrace();
        }

        return recomentationList;
    }

    private String [] getHorarios (String initialTime, int frequenceByDay){

        String[] horarios = new String[frequenceByDay];

        long dayInMiliseconds = 24 * 60 * 60 * 1000;
        long interval = dayInMiliseconds/frequenceByDay;
        int hours = Integer.parseInt(initialTime.substring(0,2));
        int minutes = Integer.parseInt(initialTime.substring(3,5));

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(0);
        calendar.set(Calendar.HOUR_OF_DAY, hours);
        calendar.set(Calendar.MINUTE, minutes);
        calendar.set(Calendar.SECOND, 0);

        for (int i = 0; i< frequenceByDay; i++){
            horarios[i] = Formater.getTimeStringFromDate(calendar.getTime());
            calendar.setTimeInMillis(
                    calendar.getTimeInMillis() + interval
            );
        }

        return horarios;
    }

    private void testMedications(List<Recomentation> recomendationList) throws ParseException {

        AlarmForMedication alarmForMedication = new AlarmForMedication();

        for (Recomentation recomentation : recomendationList){
            if (Formater.compareDateWithCurrentDateWtthoutMinutes(new Date(recomentation.getFinishDate())) >0){
                alarmForMedication.cancell(mContext, recomentation.getNotificationId());
            } else {
                alarmForMedication.createAlarm(mContext, recomentation);
            }
        }
    }

    private ValueEventListener monitorateMedication = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            List<Recomentation> recomendationList = getRecomendationListFromDataSnapshot(dataSnapshot);

            try {
                testMedications (recomendationList);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
        }
    };

}
