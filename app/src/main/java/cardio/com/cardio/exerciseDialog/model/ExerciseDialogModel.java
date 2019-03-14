package cardio.com.cardio.exerciseDialog.model;

import cardio.com.cardio.common.model.model.Recomentation;
import cardio.com.cardio.exerciseDialog.presenter.ExerciseDialogPresenter;

public interface ExerciseDialogModel {

    void setmExerciseDialogPresenterImp(ExerciseDialogPresenter mExerciseDialogPresenterImp);

    void initializeMetaDataListeners();

    void initializeExerciseListener(String id);

    void saveIntoFirebase(Recomentation recomentation);
}
