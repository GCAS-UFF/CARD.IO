package cardio.com.cardio.medicineDialog.model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.EventListener;

import cardio.com.cardio.common.Firebase.FirebaseHelper;
import cardio.com.cardio.common.model.model.Medicamento;
import cardio.com.cardio.common.model.model.Profissional;
import cardio.com.cardio.common.model.model.Recomentation;
import cardio.com.cardio.common.util.PreferencesUtils;
import cardio.com.cardio.medicineDialog.presenter.MedicineDialogPresenter;

public class MedicineDialogModelImp implements MedicineDialogModel {

    private Context mContext;
    private MedicineDialogPresenter mMedicineDialogPresenter;

    public MedicineDialogModelImp(Context mContext) {
        this.mContext = mContext;
    }

    public void setmMedicineDialogPresenter(MedicineDialogPresenter mMedicineDialogPresenter) {
        this.mMedicineDialogPresenter = mMedicineDialogPresenter;
    }

    @Override
    public void setRecomendationListener(String id) {
        FirebaseHelper.getInstance().
                getPatientDatabaseReference(getCurrentPatientKey()).
                child(FirebaseHelper.RECOMMENDED_ACTION_KEY).
                child(FirebaseHelper.MEDICINE_KEY).
                child(id).
                addListenerForSingleValueEvent(medicineDialogEventListener);
    }

    public String getCurrentPatientKey() {
        return PreferencesUtils.getString(mContext, PreferencesUtils.CURRENT_PATIENT_KEY);
    }

    private ValueEventListener medicineDialogEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            Recomentation recomentation = getRecomendationFromDataSnapshot(dataSnapshot);
            mMedicineDialogPresenter.finishLoadedMedicationDialogData(recomentation);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    private Recomentation getRecomendationFromDataSnapshot(DataSnapshot entrySnapshot){
        Medicamento medicamento = new Medicamento();
        Recomentation recomendation = entrySnapshot.getValue(Recomentation.class);
        recomendation.setId(entrySnapshot.getKey());

        medicamento.setName(entrySnapshot.child(FirebaseHelper.MEDICINE_NAME_KEY).getValue(String.class));
        medicamento.setDosagem(entrySnapshot.child(FirebaseHelper.MEDICINE_DOSAGE_KEY).getValue(String.class));
        medicamento.setQuantidade(entrySnapshot.child(FirebaseHelper.QUANTITY_KEY).getValue(String.class));
        medicamento.setNote(entrySnapshot.child(FirebaseHelper.MEDICINE_NOTE_KEY).getValue(String.class));
        medicamento.setHorario(entrySnapshot.child(FirebaseHelper.MEDICINE_START_HOUR_KEY).getValue(String.class));
        medicamento.setProfissionalId(entrySnapshot.child(FirebaseHelper.MEDICINE_PROFESSIONAL_KEY).getValue(String.class));

        recomendation.setAction(medicamento);

        return recomendation;
    }
}
