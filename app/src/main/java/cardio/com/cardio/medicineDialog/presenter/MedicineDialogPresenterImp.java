package cardio.com.cardio.medicineDialog.presenter;

import java.text.ParseException;

import cardio.com.cardio.R;
import cardio.com.cardio.common.model.model.Recomentation;
import cardio.com.cardio.medicineDialog.model.MedicineDialogModel;
import cardio.com.cardio.medicineDialog.view.MedicineDialogView;

public class MedicineDialogPresenterImp implements MedicineDialogPresenter {

    private MedicineDialogView mMedicineDialogView;
    private MedicineDialogModel mMedicineDialogModel;

    public MedicineDialogPresenterImp(MedicineDialogView medicineDialogView,
                                      MedicineDialogModel medicineDialogModel) {
        this.mMedicineDialogView = medicineDialogView;
        this.mMedicineDialogModel = medicineDialogModel;
        medicineDialogModel.setmMedicineDialogPresenter(this);
    }


    @Override
    public void initializeMedicineInformation(String id) {
        mMedicineDialogModel.setRecomendationListener(id);
    }

    @Override
    public void finishLoadedMedicationDialogData(Recomentation recomentation) {
        mMedicineDialogView.showMedicineInformation(recomentation);
    }

    @Override
    public void onClickButtonOK() {
        if (mMedicineDialogView.isFormValid()) {
            try {
                mMedicineDialogModel.saveIntoFirebase(mMedicineDialogView.getRecomendation());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        else {
            mMedicineDialogView.showMessage(R.string.message_error_field_empty);
        }

    }

    @Override
    public void finishSendRecomendation(boolean success) {
        if (success) {
            mMedicineDialogView.showMessage(R.string.message_success_recomendation);
            mMedicineDialogView.dismiss();
        }
        else
            mMedicineDialogView.showMessage(R.string.message_error_recomendation);
    }
}


