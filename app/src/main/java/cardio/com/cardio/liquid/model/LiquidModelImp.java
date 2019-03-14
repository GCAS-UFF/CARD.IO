package cardio.com.cardio.liquid.model;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cardio.com.cardio.common.Firebase.FirebaseHelper;
import cardio.com.cardio.common.model.model.Action;
import cardio.com.cardio.common.model.model.Alimentacao;
import cardio.com.cardio.common.model.model.Recomentation;
import cardio.com.cardio.common.model.view.CustomMapObject;
import cardio.com.cardio.common.model.view.CustomMapsList;
import cardio.com.cardio.common.model.view.CustomPair;
import cardio.com.cardio.common.util.Formater;
import cardio.com.cardio.common.util.PreferencesUtils;
import cardio.com.cardio.liquid.presenter.LiquidPresenter;

public class LiquidModelImp implements LiquidModel {

    private Context mContext;
    private LiquidPresenter mLiquidPresenterImp;

    public LiquidModelImp(Context mContext) {
        this.mContext = mContext;
    }

    public void setLiquidPresenterImp(LiquidPresenter mLiquidPresenterImp) {
        this.mLiquidPresenterImp = mLiquidPresenterImp;
    }

    @Override
    public boolean isProfessionalProfile() {
        return PreferencesUtils.getBollean(mContext, PreferencesUtils.IS_CURRENT_USER_PROFESSIONAL);
    }

    @Override
    public void setRecommendationListener() {
        FirebaseHelper.getInstance().
                getPatientDatabaseReference(getCurrentPatientKey()).
                child(FirebaseHelper.RECOMMENDED_ACTION_KEY).
                child(FirebaseHelper.ALIMENTACAO_KEY).
                addListenerForSingleValueEvent(liquidRecommendedEventListener);
    }

    @Override
    public String getCurrentPatientKey() {
        return PreferencesUtils.getString(mContext, PreferencesUtils.CURRENT_PATIENT_KEY);
    }

    private ValueEventListener liquidRecommendedEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            List<Recomentation> recomendationList = getRecomendationListFromDataSnapshot(dataSnapshot);
            mLiquidPresenterImp.finishLoadRecommendedLiquidData(recomendationList);
            setPerformedListener();


        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    private List<Recomentation> getRecomendationListFromDataSnapshot(DataSnapshot dataSnapshot){
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
    public void addCustomMapListForEachRecomendationDay(Recomentation recomentation, List<CustomMapsList> customMapsLists) {

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

    @Override
    public List<CustomMapsList> getPerformmedByDate(List<Recomentation> recomentations) {
        List<CustomMapsList> customMapsLists = new ArrayList<>();
        for (Recomentation recomentation : recomentations){
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

    private CustomMapObject getCustomMapObjectFromRecomendation (Recomentation recomentation, boolean isPerformed){
        Map<String, String> values = recomentation.toMap();
        List<CustomPair> customPairs = new ArrayList<>();

        for (Map.Entry<String, String> entry: values.entrySet()) {
            customPairs.add(new CustomPair(entry.getKey(), entry.getValue()));
        }

        return new CustomMapObject(recomentation.getId(), customPairs, !isPerformed);
    }



    private void setPerformedListener(){
        FirebaseHelper.getInstance().
                getPatientDatabaseReference(getCurrentPatientKey()).
                child(FirebaseHelper.PERFORMED_ACTION_KEY).
                child(FirebaseHelper.ALIMENTACAO_KEY).
                addListenerForSingleValueEvent(liquidPerformedEventListener);
    }

    private ValueEventListener liquidPerformedEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            List<Recomentation> recomendationList = getRecomendationListFromDataSnapshot(dataSnapshot);
            mLiquidPresenterImp.finishedLoadedPerformedLoquidData(recomendationList);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
        }
    };


}
