package cardio.com.cardio.medicine.view;

import java.util.List;
import java.util.Map;

import cardio.com.cardio.common.model.model.Recomentation;
import cardio.com.cardio.common.model.view.CustomMapObject;
import cardio.com.cardio.common.model.view.CustomMapsList;

public interface MedicineView {
    void openPrescribeDialog();

    void showPrescribeButton();

    void hidePrescribeButton();

    void populateOldRecommendationsRecycleView(List<CustomMapsList> customMapsLists);

    void populateCurrentRecommendationsRecycleView(List<CustomMapsList> customMapsLists);

    List<CustomMapsList> getCurrentRecommendationsRecycleView();

    List<CustomMapsList> getOldRecommendationsRecycleView();
}
