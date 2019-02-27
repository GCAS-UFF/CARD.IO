package cardio.com.cardio.medicineDialog.presenter;

import cardio.com.cardio.common.model.model.Medicamento;
import cardio.com.cardio.common.model.model.Recomentation;

public interface MedicineDialogPresenter {

    void initializeMedicineInformation(String id);

    void finishLoadedMedicationDialogData(Recomentation recomentation);

    void onClickButtonOK();

    void finishSendRecomendation(boolean success);
}
