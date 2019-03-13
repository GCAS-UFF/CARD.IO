package cardio.com.cardio.appointmentDialog.presenter;

import java.text.ParseException;
import java.util.Map;

import cardio.com.cardio.R;
import cardio.com.cardio.appointmentDialog.model.AppointmentDialogModel;
import cardio.com.cardio.appointmentDialog.view.AppointmentDialogView;
import cardio.com.cardio.common.model.model.Consulta;

public class AppointmentDialogPresenterImp implements AppointmentDialogPresenter {

    private AppointmentDialogView mAppointmentDialogView;
    private AppointmentDialogModel mAppointmentDialogModel;

    public AppointmentDialogPresenterImp(AppointmentDialogView mAppointmentDialogView,
                                         AppointmentDialogModel mAppointmentDialogModel) {
        this.mAppointmentDialogView = mAppointmentDialogView;
        this.mAppointmentDialogModel = mAppointmentDialogModel;
        mAppointmentDialogModel.setAppointmentPresenter(this);
    }


    @Override
    public void initializeAppointmentInformation(String mId) {
        mAppointmentDialogModel.initializeAppointmentListener(mId);
        mAppointmentDialogModel.initializeMetaDataListeners();
    }

    @Override
    public void finishLoadedMedicationDialogData(Consulta appointment) {
        mAppointmentDialogView.showAppointmentInformation(appointment);
    }

    @Override
    public void finishLoadAdressMeadata(Map<String, String> options){
        mAppointmentDialogView.populateAddressDropDown(options);
    }

    @Override
    public void finishLoadSpecialitiesMeadata(Map<String, String> options){
        mAppointmentDialogView.populateSpecialitiesDropDown(options);
    }

    @Override
    public void finishSendAppointment(boolean success) {
        if (success) {
            mAppointmentDialogView.showMessage(R.string.message_success_recomendation);
            mAppointmentDialogView.dismiss();
        }
        else
            mAppointmentDialogView.showMessage(R.string.message_error_recomendation);

    }

    @Override
    public void onClickButtonOK() {
        if (mAppointmentDialogView.isFormValid()) {
                mAppointmentDialogModel.saveIntoFirebase(mAppointmentDialogView.getAppointment());
        }
        else {
            mAppointmentDialogView.showMessage(R.string.message_error_field_empty);
        }
    }
}
