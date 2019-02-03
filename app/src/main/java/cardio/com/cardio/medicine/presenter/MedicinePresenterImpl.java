package cardio.com.cardio.medicine.presenter;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cardio.com.cardio.common.model.model.Recomentation;
import cardio.com.cardio.common.util.Formater;
import cardio.com.cardio.medicine.model.MedicineModel;
import cardio.com.cardio.medicine.view.MedicineView;

public class MedicinePresenterImpl implements MedicinePresenter {

    private MedicineView mMedicineView;
    private MedicineModel mMedicineModel;

    public MedicinePresenterImpl(MedicineView medicineView, MedicineModel medicineModel) {
        this.mMedicineView = medicineView;
        this.mMedicineModel = medicineModel;
        mMedicineModel.setMedicinePresenter(this);
    }

    @Override
    public void initializeProfileInformation (){
        boolean isProfessionalProfile = mMedicineModel.isProfessionalProfile();

        if (isProfessionalProfile){
            mMedicineView.showPrescribeButton();
        } else {
            mMedicineView.hidePrescribeButton();
        }
    }

    @Override
    public void initilizeRecomendationList() {
        mMedicineModel.setRecomendationListener();
    }

    @Override
    public void onClickPrescribeMedicine() {
        mMedicineView.openPrescribeDialog();
    }

    @Override
    public void finishLoadedMedicationData(List<Recomentation> recomentationList) {
        List <Recomentation> oldRecomendations = new ArrayList<>();
        List <Recomentation> currentRecomendations = new ArrayList<>();

        try {
            for (Recomentation recomendation: recomentationList) {

                if (Formater.compareDates(new Date(recomendation.getFinishDate()))){
                    currentRecomendations.add(recomendation);
                } else{
                    oldRecomendations.add(recomendation);
                }
            }
        } catch (ParseException e){
            e.printStackTrace();
        }

        mMedicineView.populateCurrentRecomendationsRecycleView(
                mMedicineModel.getRecomendationByDate(currentRecomendations)
        );

        mMedicineView.populateOldRecomendationsRecycleView(
                mMedicineModel.getRecomendationByDate(oldRecomendations)
        );
    }
}
