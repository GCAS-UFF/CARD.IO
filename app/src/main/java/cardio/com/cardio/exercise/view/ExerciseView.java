package cardio.com.cardio.exercise.view;

import java.util.List;

import cardio.com.cardio.common.model.view.CustomMapsList;

public interface ExerciseView {
    void showPrescribeButton();

    void hidePrescribeButton();

    void populateCurrentRecommendationsRecycleView(List<CustomMapsList> currentRecomendations);

    void populateOldRecommendationsRecycleView(List<CustomMapsList> oldRecomendations);

    List<CustomMapsList> getCurrentRecommendationsRecycleView();

    List<CustomMapsList> getOldRecommendationsRecycleView();

    void openPrescribeDialog();
}
