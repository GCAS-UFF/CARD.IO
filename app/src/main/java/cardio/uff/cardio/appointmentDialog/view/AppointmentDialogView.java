package cardio.uff.cardio.appointmentDialog.view;

import java.util.Map;

import cardio.uff.cardio.common.model.model.Consulta;

public interface AppointmentDialogView {
    void showAppointmentInformation(Consulta appointment);

    void populateAddressDropDown(Map<String, String> options);

    void populateSpecialitiesDropDown(Map<String, String> options);

    boolean isFormValid();

    Consulta getAppointment();

    void showMessage(int res);

    void dismiss();
}
