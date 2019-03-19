package cardio.uff.cardio.appointmentDialog.model;

import cardio.uff.cardio.appointmentDialog.presenter.AppointmentDialogPresenter;
import cardio.uff.cardio.common.model.model.Consulta;

public interface AppointmentDialogModel {

    void setAppointmentPresenter(AppointmentDialogPresenter appointmentDialogPresenter);

    void initializeAppointmentListener(String id);

    void initializeMetaDataListeners();

    void saveIntoFirebase (Consulta appointment);
}
