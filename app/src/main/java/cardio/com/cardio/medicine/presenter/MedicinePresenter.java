package cardio.com.cardio.medicine.presenter;

import java.util.List;

import cardio.com.cardio.common.model.model.Recomentation;

public interface MedicinePresenter {

    void initializeProfileInformation ();

    void initilizeRecomendationList();

    void onClickPrescribeMedicine();

    void finishLoadedMedicationData(List<Recomentation> recomentationList);
}
