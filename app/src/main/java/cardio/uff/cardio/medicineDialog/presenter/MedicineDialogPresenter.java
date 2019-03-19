package cardio.uff.cardio.medicineDialog.presenter;

import cardio.uff.cardio.common.model.model.Recomentation;

public interface MedicineDialogPresenter {

    void initializeMedicineInformation(String id);

    void finishLoadedMedicationDialogData(Recomentation recomentation);

    void onClickButtonOK();

    void finishSendRecomendation(boolean success);
}
