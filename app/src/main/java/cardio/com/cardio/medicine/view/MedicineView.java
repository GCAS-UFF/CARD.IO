package cardio.com.cardio.medicine.view;

import java.util.List;
import java.util.Map;

import cardio.com.cardio.common.model.model.Recomentation;

public interface MedicineView {
    void openPrescribeDialog();

    void showPrescribeButton();

    void hidePrescribeButton();

    void populateOldRecomendationsRecycleView(Map<String, List<Map.Entry<String, String>>> recomendationEntrysByDateMap);

    void populateCurrentRecomendationsRecycleView(Map<String, List<Map.Entry<String, String>>> recomendationEntrysByDateMap);
}
