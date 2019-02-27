package cardio.com.cardio.medicine.presenter;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import cardio.com.cardio.common.model.model.Recomentation;
import cardio.com.cardio.common.model.view.CustomMapsList;
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
        mMedicineModel.setRecommendationListener();
    }

    @Override
    public void onClickPrescribeMedicine() {
        mMedicineView.openPrescribeDialog();
    }

    @Override
    public void finishLoadRecommendedMedicationData(List<Recomentation> recomentationList) {
        List<CustomMapsList> recomendatiosByDate = mMedicineModel.getRecomendationByDate(recomentationList);

        List <CustomMapsList> oldRecomendations = new ArrayList<>();
        List <CustomMapsList> currentRecomendations = new ArrayList<>();

        try {
            for (CustomMapsList recomendationByDate: recomendatiosByDate) {

                if (Formater.compareDateWithCurrentDate(
                        Formater.getDateFromString(recomendationByDate.getTitle())) <= 0){
                    currentRecomendations.add(recomendationByDate);
                } else{
                    oldRecomendations.add(recomendationByDate);
                }
            }
        } catch (ParseException e){
            e.printStackTrace();
        }

        mMedicineView.populateCurrentRecommendationsRecycleView(currentRecomendations);

        mMedicineView.populateOldRecommendationsRecycleView(oldRecomendations);
    }

    @Override
    public void finshedLoadedPerformedMedicationData(List<Recomentation> recomentationList) {
        List<CustomMapsList> recomendatiosByDate = mMedicineModel.getPerformmedByDate( recomentationList);

        List <CustomMapsList> oldRecomendations = new ArrayList<>();
        List <CustomMapsList> currentRecomendations = new ArrayList<>();

        try {
            for (CustomMapsList recomendationByDate: recomendatiosByDate) {

                if (Formater.compareDateWithCurrentDate(
                        Formater.getDateFromString(recomendationByDate.getTitle())) <= 0){
                    currentRecomendations.add(recomendationByDate);
                } else{
                    oldRecomendations.add(recomendationByDate);
                }
            }
        } catch (ParseException e){
            e.printStackTrace();
        }

        mMedicineView.populateCurrentRecommendationsRecycleView(Formater.mergeCustomMapLists(
                mMedicineView.getCurrentRecommendationsRecycleView(),
                currentRecomendations));

        mMedicineView.populateOldRecommendationsRecycleView(Formater.mergeCustomMapLists(
                mMedicineView.getOldRecommendationsRecycleView(),
                oldRecomendations));
    }


}
