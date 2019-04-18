package cardio.uff.cardio.liquid.presenter;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import cardio.uff.cardio.common.model.model.Recomentation;
import cardio.uff.cardio.common.model.view.CustomMapsList;
import cardio.uff.cardio.common.util.Formater;
import cardio.uff.cardio.liquid.model.LiquidModel;
import cardio.uff.cardio.liquid.view.LiquidView;

public class LiquidPresenterImp implements LiquidPresenter {

    private LiquidView mLiquidViewImp;
    private LiquidModel mLiquidModelImp;

    public LiquidPresenterImp(LiquidView mLiquidViewImp, LiquidModel mLiquidModelImp) {
        this.mLiquidViewImp = mLiquidViewImp;
        this.mLiquidModelImp = mLiquidModelImp;
        mLiquidModelImp.setLiquidPresenterImp(this);
    }

    @Override
    public void initializeProfileInformation(){
        boolean isProfessionalProfile = mLiquidModelImp.isProfessionalProfile();

        if (isProfessionalProfile){
            mLiquidViewImp.showPrescribeButton();
        } else {
            mLiquidViewImp.hidePrescribeButton();
        }
    }

    @Override
    public void initializeRecomendationList() {
        mLiquidModelImp.setRecommendationListener();
    }

    @Override
    public void finishLoadRecommendedLiquidData(List<Recomentation> recomendationList) {
        List<CustomMapsList> recomendatiosByDate = mLiquidModelImp.getRecomendationByDate(recomendationList);

        List <CustomMapsList> oldRecomendations = new ArrayList<>();
        List <CustomMapsList> currentRecomendations = new ArrayList<>();

        try {
            for (CustomMapsList recomendationByDate: recomendatiosByDate) {

                if (Formater.compareDateWithCurrentDateWtthoutMinutes(
                        Formater.getDateFromString(recomendationByDate.getTitle())) <= 0){
                    currentRecomendations.add(recomendationByDate);
                } else{
                    oldRecomendations.add(recomendationByDate);
                }
            }
        } catch (ParseException e){
            e.printStackTrace();
        }

        mLiquidViewImp.populateCurrentRecommendationsRecycleView(currentRecomendations);

        mLiquidViewImp.populateOldRecommendationsRecycleView(oldRecomendations);

    }

    @Override
    public void finishedLoadedPerformedLoquidData(List<Recomentation> recomendationList) {
        List<CustomMapsList> recomendatiosByDate = mLiquidModelImp.getPerformmedByDate(recomendationList);

        List <CustomMapsList> oldRecomendations = new ArrayList<>();
        List <CustomMapsList> currentRecomendations = new ArrayList<>();

        try {
            for (CustomMapsList recomendationByDate: recomendatiosByDate) {

                if (Formater.compareDateWithCurrentDateWtthoutMinutes(
                        Formater.getDateFromString(recomendationByDate.getTitle())) <= 0){
                    currentRecomendations.add(recomendationByDate);
                } else{
                    oldRecomendations.add(recomendationByDate);
                }
            }
        } catch (ParseException e){
            e.printStackTrace();
        }

        mLiquidViewImp.populateCurrentRecommendationsRecycleView(Formater.mergeCustomMapLists(
                mLiquidViewImp.getCurrentRecommendationsRecycleView(),
                currentRecomendations
        ));

        mLiquidViewImp.populateOldRecommendationsRecycleView(Formater.mergeCustomMapLists(
                mLiquidViewImp.getOldRecommendationsRecycleView(),
                oldRecomendations
        ));
    }


    @Override
    public void onClickPrescribeLiquid() {
        mLiquidViewImp.openPrescribeDialog();
    }

}
