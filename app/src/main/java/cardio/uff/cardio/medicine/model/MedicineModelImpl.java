package cardio.uff.cardio.medicine.model;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cardio.uff.cardio.common.Firebase.FirebaseHelper;
import cardio.uff.cardio.common.model.model.Action;
import cardio.uff.cardio.common.model.model.Medicamento;
import cardio.uff.cardio.common.model.model.Recomentation;
import cardio.uff.cardio.common.model.view.CustomMapObject;
import cardio.uff.cardio.common.model.view.CustomMapsList;
import cardio.uff.cardio.common.model.view.CustomPair;
import cardio.uff.cardio.common.util.Formater;
import cardio.uff.cardio.common.util.PreferencesUtils;
import cardio.uff.cardio.medicine.presenter.MedicinePresenter;

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
    public void setRecommendationListener(){
        FirebaseHelper.getInstance().
                getPatientDatabaseReference(getCurrentPatientKey()).
                child(FirebaseHelper.RECOMMENDED_ACTION_KEY).
                child(FirebaseHelper.MEDICINE_KEY).
                addListenerForSingleValueEvent(medicineRecommendedEventListener);
    }

    private void setPerformedListener(){
        FirebaseHelper.getInstance().
                getPatientDatabaseReference(getCurrentPatientKey()).
                child(FirebaseHelper.PERFORMED_ACTION_KEY).
                child(FirebaseHelper.MEDICINE_KEY).
                addListenerForSingleValueEvent(medicinePerformedEventListener);
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
                    Action action = entrySnapshot.getValue(Action.class);
                    long executedDate = action.getExecutedDate();
                    medicamento.setExecutedDate(executedDate);
                    medicamento.setPerformed(true);

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

    @Override
    public List<CustomMapsList> getRecomendationByDate(List<Recomentation> recomentations) {

        List<CustomMapsList> customMapsLists = new ArrayList<>();
        for (Recomentation recomentation : recomentations) {
            addCustomMapListForEachRecomendationDay(recomentation, customMapsLists);
        }

        Formater.sortCustomMapListsWhereTitleIsDate(customMapsLists);

        return customMapsLists;
    }

    @Override
    public List<CustomMapsList> getPerformmedByDate(List<Recomentation> recomentations) {

        List<CustomMapsList> customMapsLists = new ArrayList<>();
        for (Recomentation recomentation : recomentations) {

            String dateStr = Formater.getStringFromDate(new Date(recomentation.getAction().getExecutedDate()));

            if (!Formater.containsInMapsLists(dateStr, customMapsLists)) {
                CustomMapsList customMapsList = new CustomMapsList(dateStr, new ArrayList<CustomMapObject>());
                customMapsLists.add(customMapsList);
            }

            Formater.addIntoMapsLists(dateStr, getCustomMapObjectFromRecomendation(recomentation, true), customMapsLists);
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

            Formater.addIntoMapsLists(dateStr, getCustomMapObjectFromRecomendation(recomentation, false), customMapsLists);

            startDate.setTime(startDate.getTime() + dayInMiliseconds);
        }
    }

    private CustomMapObject getCustomMapObjectFromRecomendation (Recomentation recomentation, boolean isPerformed){
        Map<String, String> values = recomentation.toMap();
        List<CustomPair> customPairs = new ArrayList<>();

        for (Map.Entry<String, String> entry: values.entrySet()) {
            customPairs.add(new CustomPair(entry.getKey(), entry.getValue()));
        }

        return new CustomMapObject(recomentation.getId(), customPairs, !isPerformed);
    }

    private ValueEventListener medicineRecommendedEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            List<Recomentation> recomendationList = getRecomendationListFromDataSnapshot(dataSnapshot);
            mMedicinePresenter.finishLoadRecommendedMedicationData(recomendationList);
            setPerformedListener();

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
        }
    };

    private ValueEventListener medicinePerformedEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            List<Recomentation> recomendationList = getRecomendationListFromDataSnapshot(dataSnapshot);
            mMedicinePresenter.finshedLoadedPerformedMedicationData(recomendationList);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
        }
    };
}
