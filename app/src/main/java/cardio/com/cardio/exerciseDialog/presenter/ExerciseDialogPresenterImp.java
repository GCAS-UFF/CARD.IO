package cardio.com.cardio.exerciseDialog.presenter;

import java.text.ParseException;
import java.util.Map;

import cardio.com.cardio.R;
import cardio.com.cardio.common.model.model.Exercicio;
import cardio.com.cardio.common.model.model.Recomentation;
import cardio.com.cardio.exercise.view.ExerciseView;
import cardio.com.cardio.exerciseDialog.model.ExerciseDialogModel;
import cardio.com.cardio.exerciseDialog.view.ExerciseDialogView;

public class ExerciseDialogPresenterImp implements ExerciseDialogPresenter {

    private ExerciseDialogModel mExerciseDialogModelImp;
    private ExerciseDialogView mExerciseDialogViewImp;

    public ExerciseDialogPresenterImp(ExerciseDialogModel mExerciseDialogModelImp, ExerciseDialogView mExerciseDialogViewImp) {
        this.mExerciseDialogModelImp = mExerciseDialogModelImp;
        this.mExerciseDialogViewImp = mExerciseDialogViewImp;
        mExerciseDialogModelImp.setmExerciseDialogPresenterImp(this);
    }

    @Override
    public void initializeExerciseInformation(String id) {
        mExerciseDialogModelImp.initializeExerciseListener(id);
        mExerciseDialogModelImp.initializeMetaDataListeners();
    }

    @Override
    public void finishLoadSymptomsMeadata(Map<String, String> options) {
        mExerciseDialogViewImp.populateSymtomsCheckList(options);
    }

    @Override
    public void finishLoadedMedicationDialogData(Recomentation recomentation) {
        mExerciseDialogViewImp.showExerciseInformation(recomentation);
    }

    @Override
    public void onClickButtonOK() {
        if (mExerciseDialogViewImp.isFormValid()) {
            try {
                mExerciseDialogModelImp.saveIntoFirebase(mExerciseDialogViewImp.getRecomendation());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        else {
            mExerciseDialogViewImp.showMessage(R.string.message_error_field_empty);
        }
    }

    @Override
    public void finishSendRecomendation(boolean b) {
        if (b) {
            mExerciseDialogViewImp.showMessage(R.string.message_success_recomendation);
            mExerciseDialogViewImp.dismiss();
        }
        else
            mExerciseDialogViewImp.showMessage(R.string.message_error_recomendation);
    }

}
