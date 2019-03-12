package cardio.com.cardio.appointment.presenter;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import cardio.com.cardio.appointment.model.AppointmentModel;
import cardio.com.cardio.appointment.view.AppointmentView;
import cardio.com.cardio.common.model.model.Consulta;
import cardio.com.cardio.common.model.view.CustomMapsList;
import cardio.com.cardio.common.util.Formater;

public class AppointmentPresenterImp implements AppointmentPresenter {

    private AppointmentView mAppointmentView;
    private AppointmentModel mAppointmentModel;

    public AppointmentPresenterImp(AppointmentView mAppointmentViewImp, AppointmentModel mAppointmentModelImp) {
        this.mAppointmentView = mAppointmentViewImp;
        this.mAppointmentModel = mAppointmentModelImp;
        mAppointmentModel.setAppointmentPresenter(this);
    }

    @Override
    public void initializeProfileInformation() {
        boolean isProfessionalProfile = mAppointmentModel.isProfessionalProfile();

        if (isProfessionalProfile){
            mAppointmentView.showPrescribeButton();
        } else {
            mAppointmentView.hidePrescribeButton();
        }
    }

    @Override
    public void initilizeRecomendationList() {
        mAppointmentModel.setRecommendationListener();
    }

    @Override
    public void onClickPrescribeAppointment() {
        mAppointmentView.openPrescribeDialog();
    }

    @Override
    public void finishLoadRecommendedMedicationData(List<Consulta> recomentationList) {
        List<CustomMapsList> recomendatiosByDate = mAppointmentModel.getAppointmentByDate(recomentationList);

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

        mAppointmentView.populateFutureAppointmentRecycleView(currentRecomendations);

        mAppointmentView.populateOldAppointmentRecycleView(oldRecomendations);
    }
}
