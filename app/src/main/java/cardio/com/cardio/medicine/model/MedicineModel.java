package cardio.com.cardio.medicine.model;

import java.util.List;
import java.util.Map;

import cardio.com.cardio.common.model.model.Recomentation;
import cardio.com.cardio.common.model.view.CustomMapObject;
import cardio.com.cardio.common.model.view.CustomMapsList;
import cardio.com.cardio.medicine.presenter.MedicinePresenter;

public interface MedicineModel {

    void setMedicinePresenter(MedicinePresenter medicinePresenter);

    boolean isProfessionalProfile();

    String getCurrentPatientKey();

    void setRecomendationListener();

    List<CustomMapsList> getRecomendationByDate(List<Recomentation> recomentations);
}
