package cardio.uff.cardio.exercise.model;

import java.util.List;

import cardio.uff.cardio.common.model.model.Recomentation;
import cardio.uff.cardio.common.model.view.CustomMapsList;
import cardio.uff.cardio.exercise.presenter.ExercisePresenter;

public interface ExerciseModel {

    void setmExercisePresenterImp(ExercisePresenter mExercisePresenterImp);

    boolean isProfessionalProfile();

    void setRecommendationListener();

    String getCurrentPatientKey();

    List<CustomMapsList> getRecomendationByDate(List<Recomentation> recomendationList);

    List<CustomMapsList> getPerformmedByDate(List<Recomentation> recomendationList);
}
