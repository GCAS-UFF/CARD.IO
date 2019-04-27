package cardio.uff.cardio.monitoringAppointment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cardio.uff.cardio.alarms.AlarmForAppointment;
import cardio.uff.cardio.common.Firebase.FirebaseHelper;
import cardio.uff.cardio.common.model.model.Consulta;
import cardio.uff.cardio.common.util.Formater;
import cardio.uff.cardio.common.util.PreferencesUtils;

public class AppointmentMonitorating {
    private Context mContext;

    public AppointmentMonitorating(Context context) {
        this.mContext = context;
    }

    public void start() {
        if (getCurrentPatientKey() != null) {
            FirebaseHelper.appointmentDatabaseReference.
                    orderByChild(FirebaseHelper.APPOINTMENT_PATIENT_KEY).
                    equalTo(getCurrentPatientKey()).
                    addValueEventListener(monitorateAppointments);
        }
    }

    public String getCurrentPatientKey() {
        return PreferencesUtils.getString(mContext, PreferencesUtils.CURRENT_PATIENT_KEY);
    }

    private List<Consulta> getAppointmentListFromDataSnapshot(DataSnapshot dataSnapshot) {
        List<Consulta> appointmentList = new ArrayList<>();

        try {

            for (DataSnapshot entrySnapshot : dataSnapshot.getChildren()) {
                Consulta consulta = entrySnapshot.getValue(Consulta.class);
                consulta.setId(entrySnapshot.getKey());
                appointmentList.add(consulta);
            }

            return appointmentList;

        }catch (NullPointerException e){
            e.printStackTrace();
        }

        return appointmentList;
    }

    private List<Consulta> filterCurrentAppointments(List<Consulta> appointments) throws ParseException {

        Date currentDate = new Date();
        List<Consulta> currentAppointments = new ArrayList<>();

        for (Consulta appointment : appointments){
            Date date = new Date(appointment.getData());

            if (Formater.compareDatesWithoutSeconds(currentDate, date) <=0){
                currentAppointments.add(appointment);
            }
        }

        return currentAppointments;
    }

    private void testAppointments(List<Consulta> filterCurrentAppointments) {
        AlarmForAppointment alarmForAppointment = new AlarmForAppointment();

        for (Consulta appointment : filterCurrentAppointments){
            alarmForAppointment.createAlarm(mContext,appointment);
        }
    }


    private ValueEventListener monitorateAppointments = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            List<Consulta> appointmentList = getAppointmentListFromDataSnapshot(dataSnapshot);

            try {

                testAppointments(filterCurrentAppointments(appointmentList));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
        }
    };
}
