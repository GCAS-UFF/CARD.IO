package cardio.uff.cardio.exercise.presenter;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import cardio.uff.cardio.common.model.model.Recomentation;
import cardio.uff.cardio.common.model.view.CustomMapsList;
import cardio.uff.cardio.common.util.Formater;
import cardio.uff.cardio.exercise.model.ExerciseModel;
import cardio.uff.cardio.exercise.view.ExerciseView;

public class ExercisePresenterImp implements ExercisePresenter {

    private ExerciseModel mExerciseModelImp;
    private ExerciseView mExerciseViewImp;

    public ExercisePresenterImp(ExerciseView mExerciseViewImp, ExerciseModel mExerciseModelImp) {
        this.mExerciseModelImp = mExerciseModelImp;
        this.mExerciseViewImp = mExerciseViewImp;
        mExerciseModelImp.setmExercisePresenterImp(this);
    }

    @Override
    public void initializeProfileInformation() {
        boolean isProfessionalProfile = mExerciseModelImp.isProfessionalProfile();

        if (isProfessionalProfile){
            mExerciseViewImp.showPrescribeButton();
        } else {
            mExerciseViewImp.hidePrescribeButton();
        }
    }

    @Override
    public void initializeRecomendationList() {
        mExerciseModelImp.setRecommendationListener();
    }

    @Override
    public void finishLoadRecommendedExerciseData(List<Recomentation> recomendationList) {
        List<CustomMapsList> recomendatiosByDate = mExerciseModelImp.getRecomendationByDate(recomendationList);

        List <CustomMapsList> oldRecomendations = new ArrayList<>();
        List <CustomMapsList> currentRecomendations = new ArrayList<>();

        try {
            for (CustomMapsList recomendationByDate: recomendatiosByDate) {

                if (Formater.compareDateWithCurrentDateWtthoutMinutes(
                        Formater.getDateFromString(recomendationByDate.getTitle())) <= 0){
                    currentRecomendations.add(recomendationByDate);
                } else{
                    oldRecomendations.add(recomendationByDate);
                }
            }
        } catch (ParseException e){
            e.printStackTrace();
        }

        mExerciseViewImp.populateCurrentRecommendationsRecycleView(currentRecomendations);
        mExerciseViewImp.populateOldRecommendationsRecycleView(oldRecomendations);
    }

    @Override
    public void finishedLoadedPerformedLoquidData(List<Recomentation> recomendationList) {
        List<CustomMapsList> recomendatiosByDate = mExerciseModelImp.getPerformmedByDate(recomendationList);

        List <CustomMapsList> oldRecomendations = new ArrayList<>();
        List <CustomMapsList> currentRecomendations = new ArrayList<>();

        try {
            for (CustomMapsList recomendationByDate: recomendatiosByDate) {

                if (Formater.compareDateWithCurrentDateWtthoutMinutes(
                        Formater.getDateFromString(recomendationByDate.getTitle())) <= 0){
                    currentRecomendations.add(recomendationByDate);
                } else{
                    oldRecomendations.add(recomendationByDate);
                }
            }
        } catch (ParseException e){
            e.printStackTrace();
        }

        mExerciseViewImp.populateCurrentRecommendationsRecycleView(Formater.mergeCustomMapLists(
                mExerciseViewImp.getCurrentRecommendationsRecycleView(),
                currentRecomendations
        ));

        mExerciseViewImp.populateOldRecommendationsRecycleView(Formater.mergeCustomMapLists(
                mExerciseViewImp.getOldRecommendationsRecycleView(),
                oldRecomendations
        ));
    }

    @Override
    public void onClickPrescribeLiquid() {
        mExerciseViewImp.openPrescribeDialog();
    }
}
