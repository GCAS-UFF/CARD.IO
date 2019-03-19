package cardio.uff.cardio.liquid.model;

import java.util.List;

import cardio.uff.cardio.common.model.model.Recomentation;
import cardio.uff.cardio.common.model.view.CustomMapsList;
import cardio.uff.cardio.liquid.presenter.LiquidPresenter;

public interface LiquidModel {

    void setLiquidPresenterImp(LiquidPresenter mLiquidPresenterImp);

    boolean isProfessionalProfile();

    String getCurrentPatientKey();

    void setRecommendationListener();

    List<CustomMapsList> getRecomendationByDate(List<Recomentation> recomentations);

    List<CustomMapsList> getPerformmedByDate(List<Recomentation> recomentations);
}
