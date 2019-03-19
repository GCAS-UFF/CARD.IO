package cardio.uff.cardio.appointmentDialog.model;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import cardio.uff.cardio.appointmentDialog.presenter.AppointmentDialogPresenter;
import cardio.uff.cardio.common.Firebase.FirebaseHelper;
import cardio.uff.cardio.common.model.model.Consulta;

public class AppointmentDialogModelImp implements AppointmentDialogModel {

    private Context mContext;
    private AppointmentDialogPresenter mAppointmentPresenter;

    public AppointmentDialogModelImp(Context mContext) {
        this.mContext = mContext;
    }


    @Override
    public void setAppointmentPresenter(AppointmentDialogPresenter appointmentDialogPresenter) {
        this.mAppointmentPresenter = appointmentDialogPresenter;
    }

    @Override
    public void initializeAppointmentListener(String id) {
        FirebaseHelper.appointmentDatabaseReference.
                child(id).
                addListenerForSingleValueEvent(appointmentDialogEventListener);
    }

    @Override
    public void initializeMetaDataListeners(){
        FirebaseHelper.adressDatabaseReference.addListenerForSingleValueEvent(getAdressMetadata);
        FirebaseHelper.specialitiesDatabaseReference.addListenerForSingleValueEvent(getSpecialityMetadata);
    }

    @Override
    public void saveIntoFirebase(Consulta appointment) {
        try {
            DatabaseReference mDbRef = FirebaseHelper.appointmentDatabaseReference.child(appointment.getId());
            mDbRef.setValue(appointment);

            mAppointmentPresenter.finishSendAppointment(true);
        }catch (Exception e){
            e.printStackTrace();
            mAppointmentPresenter.finishSendAppointment(false);        }
    }

    private ValueEventListener appointmentDialogEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            Consulta appointment = getAppointmentFromDataSnapshot(dataSnapshot);
            mAppointmentPresenter.finishLoadedMedicationDialogData(appointment);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    private Consulta getAppointmentFromDataSnapshot(DataSnapshot dataSnapshot) {
        Consulta consulta = dataSnapshot.getValue(Consulta.class);
        consulta.setId(dataSnapshot.getKey());

        return consulta;
    }

    private ValueEventListener getAdressMetadata = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            Map<String, String> options = new HashMap<>();

            for (DataSnapshot entrySnapshot : dataSnapshot.getChildren()){
                options.put(entrySnapshot.getKey(), entrySnapshot.getValue(String.class));
            }

            mAppointmentPresenter.finishLoadAdressMeadata(options);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    private ValueEventListener getSpecialityMetadata = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            Map<String, String> options = new LinkedHashMap<>();

            for (DataSnapshot entrySnapshot : dataSnapshot.getChildren()){
                options.put(entrySnapshot.getKey(), entrySnapshot.getValue(String.class));
            }

            mAppointmentPresenter.finishLoadSpecialitiesMeadata(options);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
}
