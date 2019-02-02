package cardio.com.cardio.medicine.model;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import cardio.com.cardio.common.Firebase.FirebaseHelper;
import cardio.com.cardio.common.model.model.Medicamento;
import cardio.com.cardio.common.model.model.Profissional;
import cardio.com.cardio.common.model.model.Recomentation;
import cardio.com.cardio.common.util.Formater;
import cardio.com.cardio.common.util.PreferencesUtils;
import cardio.com.cardio.medicine.presenter.MedicinePresenter;

public class MedicineModelImpl implements MedicineModel {
    private Context mContext;
    private MedicinePresenter mMedicinePresenter;

    public MedicineModelImpl(Context context) {
        this.mContext = context;
    }

    @Override
    public void setMedicinePresenter (MedicinePresenter medicinePresenter){
        this.mMedicinePresenter = medicinePresenter;
    }

    @Override
    public void setRecomendationListener (){
        FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(rootEventListener);
    }

    @Override
    public boolean isProfessionalProfile() {
        return PreferencesUtils.getBollean(mContext, PreferencesUtils.IS_CURRENT_USER_PROFESSIONAL);
    }

    @Override
    public String getCurrentPatientKey() {
        return PreferencesUtils.getString(mContext, PreferencesUtils.CURRENT_PATIENT_KEY);
    }

    public List<Recomentation> getRecondationListFromDataSnapshot(DataSnapshot dataSnapshot) {
        List<Recomentation> recomentationList = new ArrayList<>();

        try {
            DataSnapshot recomendationSnapshot = dataSnapshot.child(FirebaseHelper.PATIENT_KEY)
                    .child(getCurrentPatientKey())
                    .child(FirebaseHelper.RECOMMENDED_ACTION_KEY)
                    .child(FirebaseHelper.MEDICINE_KEY);

            DataSnapshot professionalSnapshot = dataSnapshot.child(FirebaseHelper.PROFESSIONAL_KEY);

            for (DataSnapshot entrySnapshot : recomendationSnapshot.getChildren()) {
                Medicamento medicamento = new Medicamento();
                Recomentation recomendation = entrySnapshot.getValue(Recomentation.class);

                medicamento.setName(entrySnapshot.child(FirebaseHelper.MEDICINE_NAME_KEY).getValue(String.class));
                medicamento.setDosagem(entrySnapshot.child(FirebaseHelper.MEDICINE_DOSAGE_KEY).getValue(String.class));
                medicamento.setQuantidade(entrySnapshot.child(FirebaseHelper.QUANTITY_KEY).getValue(String.class));
                medicamento.setNote(entrySnapshot.child(FirebaseHelper.MEDICINE_NOTE_KEY).getValue(String.class));
                medicamento.setHorario(entrySnapshot.child(FirebaseHelper.MEDICINE_START_HOUR_KEY).getValue(String.class));
                medicamento.setProfissionalId(entrySnapshot.child(FirebaseHelper.MEDICINE_PROFESSIONAL_KEY).getValue(String.class));

                medicamento.setProfissionalObject(professionalSnapshot.child(medicamento.getProfissionalId()).getValue(Profissional.class));

                recomendation.setAction(medicamento);

                recomentationList.add(recomendation);
            }

            return recomentationList;

        }catch (NullPointerException e){
            e.printStackTrace();
        }

        return recomentationList;
    }

    @Override
    public Map<String, List<Map.Entry<String, String>>> getRecomendationByDate(List<Recomentation> recomentations) {

        Map<String, List<Map.Entry<String, String>>> result = new LinkedHashMap<>();

        for (Recomentation recomentation : recomentations){

            Date startDate = new Date(recomentation.getStartDate());
            String dateStr = Formater.getStringFromDate(startDate);

            if (!result.containsKey(dateStr)){
                result.put(dateStr , new ArrayList<Map.Entry<String, String>>());
            }
            result.get(dateStr).addAll(recomentation.toMap().entrySet());
        }

        Map<String, List<Map.Entry<String, String>>> sorted = new TreeMap<>();
        sorted.putAll(result);

        return sorted;
    }

    ValueEventListener rootEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            List<Recomentation> recomentationList = getRecondationListFromDataSnapshot(dataSnapshot);
            mMedicinePresenter.finishLoadedMedicationData(recomentationList);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
        }
    };
}
