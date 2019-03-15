package cardio.com.cardio.liquid.model;

import com.google.firebase.database.DataSnapshot;

import java.util.List;

import cardio.com.cardio.common.model.model.Recomentation;
import cardio.com.cardio.common.model.view.CustomMapsList;
import cardio.com.cardio.liquid.presenter.LiquidPresenter;

public interface LiquidModel {

    void setLiquidPresenterImp(LiquidPresenter mLiquidPresenterImp);

    boolean isProfessionalProfile();

    String getCurrentPatientKey();

    void setRecommendationListener();

    List<CustomMapsList> getRecomendationByDate(List<Recomentation> recomentations);

    List<CustomMapsList> getPerformmedByDate(List<Recomentation> recomentations);
}
