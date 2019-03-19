package cardio.uff.cardio.medicineDialog.model;

import cardio.uff.cardio.common.model.model.Recomentation;
import cardio.uff.cardio.medicineDialog.presenter.MedicineDialogPresenter;

public interface MedicineDialogModel {

    void setRecomendationListener(String id);

    void setmMedicineDialogPresenter(MedicineDialogPresenter mMedicineDialogPresenter);

    String getCurrentPatientKey();

    void saveIntoFirebase(Recomentation recomentation);
}
