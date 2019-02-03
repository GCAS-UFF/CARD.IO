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

    void populateOldRecomendationsRecycleView(List<CustomMapsList> customMapsLists);

    void populateCurrentRecomendationsRecycleView(List<CustomMapsList> customMapsLists);
}
