package cardio.com.cardio.appointmentDialog.model;

import cardio.com.cardio.appointment.presenter.AppointmentPresenter;
import cardio.com.cardio.appointmentDialog.presenter.AppointmentDialogPresenter;
import cardio.com.cardio.common.model.model.Consulta;

public interface AppointmentDialogModel {

    void setAppointmentPresenter(AppointmentDialogPresenter appointmentDialogPresenter);

    void initializeAppointmentListener(String id);

    void initializeMetaDataListeners();

    void saveIntoFirebase (Consulta appointment);
}
