package cardio.com.cardio.appointment.model;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cardio.com.cardio.appointment.presenter.AppointmentPresenter;
import cardio.com.cardio.common.Firebase.FirebaseHelper;
import cardio.com.cardio.common.model.model.Consulta;
import cardio.com.cardio.common.model.model.Recomentation;
import cardio.com.cardio.common.model.view.CustomMapObject;
import cardio.com.cardio.common.model.view.CustomMapsList;
import cardio.com.cardio.common.model.view.CustomPair;
import cardio.com.cardio.common.util.Formater;
import cardio.com.cardio.common.util.PreferencesUtils;

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
                addListenerForSingleValueEvent(appointmentEventListener);
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

                appointmentList.add(consulta);
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
