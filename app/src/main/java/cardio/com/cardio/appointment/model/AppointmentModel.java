package cardio.com.cardio.appointment.model;

import java.util.List;

import cardio.com.cardio.appointment.presenter.AppointmentPresenter;
import cardio.com.cardio.common.model.model.Consulta;
import cardio.com.cardio.common.model.view.CustomMapsList;

public interface AppointmentModel {

    void setAppointmentPresenter(AppointmentPresenter appointmentPresenter);

    boolean isProfessionalProfile();

    void setRecommendationListener();

    String getCurrentPatientKey();

    List<CustomMapsList> getAppointmentByDate(List<Consulta> consultas);
}
