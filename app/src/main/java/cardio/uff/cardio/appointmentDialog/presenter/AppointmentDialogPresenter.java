package cardio.uff.cardio.appointmentDialog.presenter;

import java.util.Map;

import cardio.uff.cardio.common.model.model.Consulta;

public interface AppointmentDialogPresenter {

    void initializeAppointmentInformation(String mId);

    void finishLoadedMedicationDialogData(Consulta appointment);

    void onClickButtonOK();

    void finishLoadAdressMeadata(Map<String, String> options);

    void finishLoadSpecialitiesMeadata(Map<String, String> options);

    void finishSendAppointment (boolean success);
}
