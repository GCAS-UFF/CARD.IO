package cardio.uff.cardio.exerciseDialog.view;

import java.text.ParseException;
import java.util.Map;

import cardio.uff.cardio.common.model.model.Recomentation;

public interface ExerciseDialogView {
    void populateSymtomsCheckList(Map<String, String> options);

    void showExerciseInformation(Recomentation recomentation);

    boolean isFormValid();

    Recomentation getRecomendation() throws ParseException;

    void showMessage(int message_error_field_empty);

    void dismiss();
}
