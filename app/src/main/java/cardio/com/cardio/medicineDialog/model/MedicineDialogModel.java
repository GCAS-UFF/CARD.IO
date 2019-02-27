package cardio.com.cardio.medicineDialog.model;

import cardio.com.cardio.medicineDialog.presenter.MedicineDialogPresenter;

public interface MedicineDialogModel {

    void setRecomendationListener(String id);

    void setmMedicineDialogPresenter(MedicineDialogPresenter mMedicineDialogPresenter);

    String getCurrentPatientKey();

}
