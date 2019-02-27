package cardio.com.cardio.medicine.presenter;

import java.util.List;

import cardio.com.cardio.common.model.model.Recomentation;

public interface MedicinePresenter {

    void initializeProfileInformation();

    void initilizeRecomendationList();

    void onClickPrescribeMedicine();

    void finishLoadRecommendedMedicationData(List<Recomentation> recomentationList);

    void finshedLoadedPerformedMedicationData(List<Recomentation> recomentationList);
}
