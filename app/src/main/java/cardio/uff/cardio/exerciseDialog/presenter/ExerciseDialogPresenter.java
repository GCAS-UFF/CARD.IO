package cardio.uff.cardio.exerciseDialog.presenter;

import java.util.Map;

import cardio.uff.cardio.common.model.model.Recomentation;

public interface ExerciseDialogPresenter {
    void initializeExerciseInformation(String id);

    void finishLoadSymptomsMeadata(Map<String, String> options);

    void finishLoadedMedicationDialogData(Recomentation recomentation);

    void onClickButtonOK();

    void finishSendRecomendation(boolean b);
}
