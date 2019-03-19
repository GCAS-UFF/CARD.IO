package cardio.uff.cardio.medicine.view;

import java.util.List;

import cardio.uff.cardio.common.model.view.CustomMapsList;

public interface MedicineView {
    void openPrescribeDialog();

    void showPrescribeButton();

    void hidePrescribeButton();

    void populateOldRecommendationsRecycleView(List<CustomMapsList> customMapsLists);

    void populateCurrentRecommendationsRecycleView(List<CustomMapsList> customMapsLists);

    List<CustomMapsList> getCurrentRecommendationsRecycleView();

    List<CustomMapsList> getOldRecommendationsRecycleView();
}
