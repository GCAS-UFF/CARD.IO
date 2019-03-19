package cardio.uff.cardio.liquid.presenter;

import java.util.List;

import cardio.uff.cardio.common.model.model.Recomentation;

public interface LiquidPresenter {

    void initializeProfileInformation();

    void initializeRecomendationList();

    void finishedLoadedPerformedLoquidData(List<Recomentation> recomendationList);

    void finishLoadRecommendedLiquidData(List<Recomentation> recomendationList);

    void onClickPrescribeLiquid();

}
