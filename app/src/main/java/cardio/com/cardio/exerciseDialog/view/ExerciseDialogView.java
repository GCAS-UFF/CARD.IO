package cardio.com.cardio.exerciseDialog.view;

import java.text.ParseException;
import java.util.Map;

import cardio.com.cardio.common.model.model.Exercicio;
import cardio.com.cardio.common.model.model.Recomentation;

public interface ExerciseDialogView {
    void populateSymtomsCheckList(Map<String, String> options);

    void showExerciseInformation(Recomentation recomentation);

    boolean isFormValid();

    Recomentation getRecomendation() throws ParseException;

    void showMessage(int message_error_field_empty);

    void dismiss();
}
