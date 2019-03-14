package cardio.com.cardio.exercise.presenter;

import java.util.List;

import cardio.com.cardio.common.model.model.Recomentation;

public interface ExercisePresenter {

    void initializeProfileInformation();

    void initializeRecomendationList();

    void finishLoadRecommendedExerciseData(List<Recomentation> recomendationList);

    void finishedLoadedPerformedLoquidData(List<Recomentation> recomendationList);

    void onClickPrescribeLiquid();
}
