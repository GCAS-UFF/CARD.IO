package cardio.com.cardio.medicineDialog.model;

import cardio.com.cardio.common.model.model.Recomentation;
import cardio.com.cardio.medicineDialog.presenter.MedicineDialogPresenter;

public interface MedicineDialogModel {

    void setRecomendationListener(String id);

    void setmMedicineDialogPresenter(MedicineDialogPresenter mMedicineDialogPresenter);

    String getCurrentPatientKey();

    void saveIntoFirebase(Recomentation recomentation);
}
