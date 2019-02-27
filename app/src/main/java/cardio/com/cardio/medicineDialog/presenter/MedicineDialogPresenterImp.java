package cardio.com.cardio.medicineDialog.presenter;

import cardio.com.cardio.common.model.model.Medicamento;
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
}


