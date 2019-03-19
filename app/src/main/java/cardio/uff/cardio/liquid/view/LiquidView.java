package cardio.uff.cardio.liquid.view;

import java.util.List;

import cardio.uff.cardio.common.model.view.CustomMapsList;

public interface LiquidView {
    void showPrescribeButton();

    void hidePrescribeButton();

    void populateCurrentRecommendationsRecycleView(List<CustomMapsList> customMapsLists);

    void populateOldRecommendationsRecycleView(List<CustomMapsList> customMapsLists);

    List<CustomMapsList> getCurrentRecommendationsRecycleView();

    List<CustomMapsList> getOldRecommendationsRecycleView();

    void openPrescribeDialog();

}
