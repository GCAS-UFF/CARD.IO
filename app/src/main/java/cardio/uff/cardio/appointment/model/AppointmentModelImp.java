package cardio.uff.cardio.appointment.model;

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

import cardio.uff.cardio.appointment.presenter.AppointmentPresenter;
import cardio.uff.cardio.common.Firebase.FirebaseHelper;
import cardio.uff.cardio.common.model.model.Consulta;
import cardio.uff.cardio.common.model.view.CustomMapObject;
import cardio.uff.cardio.common.model.view.CustomMapsList;
import cardio.uff.cardio.common.model.view.CustomPair;
import cardio.uff.cardio.common.util.Formater;
import cardio.uff.cardio.common.util.PreferencesUtils;

public class AppointmentModelImp implements AppointmentModel{

    private Context mContext;
    private AppointmentPresenter appointmentPresenter;

    public AppointmentModelImp(Context mContext) {
        this.mContext = mContext;
    }

    public void setAppointmentPresenter(AppointmentPresenter mAppointmentPresenterImp) {
        this.appointmentPresenter = mAppointmentPresenterImp;
    }

    public boolean isProfessionalProfile() {
        return PreferencesUtils.getBollean(mContext, PreferencesUtils.IS_CURRENT_USER_PROFESSIONAL);
    }

    public void setRecommendationListener(){
        FirebaseHelper.appointmentDatabaseReference.
                addValueEventListener(appointmentEventListener);
    }

    public String getCurrentPatientKey() {
        return PreferencesUtils.getString(mContext, PreferencesUtils.CURRENT_PATIENT_KEY);
    }

    private ValueEventListener appointmentEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            List<Consulta> appointmentList = getAppointmentListFromDataSnapshot(dataSnapshot);
            appointmentPresenter.finishLoadRecommendedMedicationData(appointmentList);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
        }
    };

    private List<Consulta> getAppointmentListFromDataSnapshot(DataSnapshot dataSnapshot) {
        List<Consulta> appointmentList = new ArrayList<>();

        try {

            for (DataSnapshot entrySnapshot : dataSnapshot.getChildren()) {
                Consulta consulta = entrySnapshot.getValue(Consulta.class);
                consulta.setId(entrySnapshot.getKey());

                if (consulta.getPaciente().equals(getCurrentPatientKey())) {
                    appointmentList.add(consulta);
                }
            }

            return appointmentList;

        }catch (NullPointerException e){
            e.printStackTrace();
        }

        return appointmentList;
    }

    @Override
    public List<CustomMapsList> getAppointmentByDate(List<Consulta> consultas) {

        List<CustomMapsList> customMapsLists = new ArrayList<>();
        for (Consulta consulta : consultas) {
            String dateStr = Formater.getStringFromDate(new Date(consulta.getData()));

            if (!Formater.containsInMapsLists(dateStr, customMapsLists)) {
                CustomMapsList customMapsList = new CustomMapsList(dateStr, new ArrayList<CustomMapObject>());
                customMapsLists.add(customMapsList);
            }

            Formater.addIntoMapsLists(dateStr, getCustomMapObjectFromAppointment(consulta),
                    customMapsLists);
        }

        Formater.sortCustomMapListsWhereTitleIsDate(customMapsLists);

        return customMapsLists;
    }

    private CustomMapObject getCustomMapObjectFromAppointment(Consulta consulta){
        Map<String, String> values = consulta.toMap();
        List<CustomPair> customPairs = new ArrayList<>();

        for (Map.Entry<String, String> entry: values.entrySet()) {
            customPairs.add(new CustomPair(entry.getKey(), entry.getValue()));
        }

        return new CustomMapObject(consulta.getId(), customPairs, !consulta.isAttended());
    }
}
