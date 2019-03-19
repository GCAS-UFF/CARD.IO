package cardio.uff.cardio.appointment.presenter;

import java.util.List;

import cardio.uff.cardio.common.model.model.Consulta;

public interface AppointmentPresenter {

    void onClickPrescribeAppointment();

    void initializeProfileInformation();

    void initilizeRecomendationList();

    void finishLoadRecommendedMedicationData(List<Consulta> recomentationList);


}
