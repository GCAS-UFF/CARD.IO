package cardio.uff.cardio.appointment.model;

import java.util.List;

import cardio.uff.cardio.appointment.presenter.AppointmentPresenter;
import cardio.uff.cardio.common.model.model.Consulta;
import cardio.uff.cardio.common.model.view.CustomMapsList;

public interface AppointmentModel {

    void setAppointmentPresenter(AppointmentPresenter appointmentPresenter);

    boolean isProfessionalProfile();

    void setRecommendationListener();

    String getCurrentPatientKey();

    List<CustomMapsList> getAppointmentByDate(List<Consulta> consultas);
}
