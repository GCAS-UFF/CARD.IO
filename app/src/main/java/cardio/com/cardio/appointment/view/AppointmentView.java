package cardio.com.cardio.appointment.view;

import java.util.List;

import cardio.com.cardio.common.model.view.CustomMapsList;

public interface AppointmentView {

    void showPrescribeButton();

    void hidePrescribeButton();

    void populateFutureAppointmentRecycleView(List<CustomMapsList> customMapsLists);

    void populateOldAppointmentRecycleView(List<CustomMapsList> customMapsLists);

    void openPrescribeDialog();
}
