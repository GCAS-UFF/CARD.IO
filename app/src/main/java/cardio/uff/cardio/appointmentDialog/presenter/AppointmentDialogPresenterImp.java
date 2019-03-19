package cardio.uff.cardio.appointmentDialog.presenter;

import java.util.Map;

import cardio.uff.cardio.R;
import cardio.uff.cardio.appointmentDialog.model.AppointmentDialogModel;
import cardio.uff.cardio.appointmentDialog.view.AppointmentDialogView;
import cardio.uff.cardio.common.model.model.Consulta;

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
