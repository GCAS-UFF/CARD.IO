package cardio.uff.cardio.medicine.model;

import java.util.List;

import cardio.uff.cardio.common.model.model.Recomentation;
import cardio.uff.cardio.common.model.view.CustomMapsList;
import cardio.uff.cardio.medicine.presenter.MedicinePresenter;

public interface MedicineModel {

    void setMedicinePresenter(MedicinePresenter medicinePresenter);

    boolean isProfessionalProfile();

    String getCurrentPatientKey();

    void setRecommendationListener();

    List<CustomMapsList> getRecomendationByDate(List<Recomentation> recomentations);

    List<CustomMapsList> getPerformmedByDate(List<Recomentation> recomentations);
}
