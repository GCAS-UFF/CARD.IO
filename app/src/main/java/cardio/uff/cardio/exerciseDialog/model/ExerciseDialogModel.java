package cardio.uff.cardio.exerciseDialog.model;

import cardio.uff.cardio.common.model.model.Recomentation;
import cardio.uff.cardio.exerciseDialog.presenter.ExerciseDialogPresenter;

public interface ExerciseDialogModel {

    void setmExerciseDialogPresenterImp(ExerciseDialogPresenter mExerciseDialogPresenterImp);

    void initializeMetaDataListeners();

    void initializeExerciseListener(String id);

    void saveIntoFirebase(Recomentation recomentation);
}
