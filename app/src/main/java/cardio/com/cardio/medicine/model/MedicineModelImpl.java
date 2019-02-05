package cardio.com.cardio.medicine.model;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cardio.com.cardio.common.Firebase.FirebaseHelper;
import cardio.com.cardio.common.model.model.Medicamento;
import cardio.com.cardio.common.model.model.Profissional;
import cardio.com.cardio.common.model.model.Recomentation;
import cardio.com.cardio.common.model.view.CustomMapObject;
import cardio.com.cardio.common.model.view.CustomMapsList;
import cardio.com.cardio.common.model.view.CustomPair;
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
        FirebaseHelper.getInstance().
                getPatientDatabaseReference(getCurrentPatientKey()).
                child(FirebaseHelper.RECOMMENDED_ACTION_KEY).
                child(FirebaseHelper.MEDICINE_KEY).
                addValueEventListener(medicineEventListener);
    }

    @Override
    public boolean isProfessionalProfile() {
        return PreferencesUtils.getBollean(mContext, PreferencesUtils.IS_CURRENT_USER_PROFESSIONAL);
    }

    @Override
    public String getCurrentPatientKey() {
        return PreferencesUtils.getString(mContext, PreferencesUtils.CURRENT_PATIENT_KEY);
    }

    private List<Recomentation> getRecomendationListFromDataSnapshot(DataSnapshot dataSnapshot) {
        List<Recomentation> recomentationList = new ArrayList<>();

        try {

            DataSnapshot professionalSnapshot = dataSnapshot.child(FirebaseHelper.PROFESSIONAL_KEY);

            for (DataSnapshot entrySnapshot : dataSnapshot.getChildren()) {
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
    public List<CustomMapsList> getRecomendationByDate(List<Recomentation> recomentations) {

        List<CustomMapsList> customMapsLists = new ArrayList<>();
        for (Recomentation recomentation : recomentations) {
            addCustomMapListForEachRecomendationDay(recomentation, customMapsLists);
        }

        Formater.sortCustomMapListsWhereTitleIsDate(customMapsLists);

        return customMapsLists;
    }

    private void addCustomMapListForEachRecomendationDay(Recomentation recomentation, List<CustomMapsList> customMapsLists){

        long dayInMiliseconds = 86400000;
        Date startDate = new Date(recomentation.getStartDate());
        Date finishDate = new Date(recomentation.getFinishDate() + dayInMiliseconds);

        while (startDate.before(finishDate)) {

            String dateStr = Formater.getStringFromDate(startDate);

            if (!Formater.containsInMapsLists(dateStr, customMapsLists)) {
                CustomMapsList customMapsList = new CustomMapsList(dateStr, new ArrayList<CustomMapObject>());
                customMapsLists.add(customMapsList);
            }

            Formater.addIntoMapsLists(dateStr, getCustomMapObjectFromRecomendation(recomentation), customMapsLists);

            startDate.setTime(startDate.getTime() + dayInMiliseconds);
        }
    }

    private CustomMapObject getCustomMapObjectFromRecomendation (Recomentation recomentation){
        Map<String, String> values = recomentation.toMap();
        List<CustomPair> customPairs = new ArrayList<>();

        for (Map.Entry<String, String> entry: values.entrySet()) {
            customPairs.add(new CustomPair(entry.getKey(), entry.getValue()));
        }

        return new CustomMapObject(recomentation.getId(), customPairs);
    }

    private ValueEventListener medicineEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            List<Recomentation> recomendationList = getRecomendationListFromDataSnapshot(dataSnapshot);
            mMedicinePresenter.finishLoadedMedicationData(recomendationList);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
        }
    };
}
