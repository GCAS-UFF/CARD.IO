package cardio.com.cardio.liquidDialog.presenter;

import java.text.ParseException;
import java.util.Map;

import cardio.com.cardio.R;
import cardio.com.cardio.liquidDialog.model.LiquidDialogModel;
import cardio.com.cardio.liquidDialog.view.LiquidDialogView;

public class LiquidDialogPresenterImp implements LiquidDialogPresenter {

    private LiquidDialogView mLiquidDialogView;
    private LiquidDialogModel mLiquidDialogModel;

    public LiquidDialogPresenterImp (LiquidDialogView liquidDialogView, LiquidDialogModel liquidDialogModel) {
        this.mLiquidDialogView = liquidDialogView;
        this.mLiquidDialogModel = liquidDialogModel;
        mLiquidDialogModel.setLiquidDialogPresenter(this);
    }

    @Override
    public void initializeLiquidInformation(String id) {
        mLiquidDialogModel.initializeMetaDataListeners();
    }

    @Override
    public double calculateLiquid(String quantity, String dosageReference) {

        double dousage = Double.valueOf(dosageReference);
        double quantityInt = Double.valueOf(quantity);

        return dousage * quantityInt;
    }

    @Override
    public void finishLoadBebidasMetadata(Map<String, String> options) {
        mLiquidDialogView.populateQuantityReferenceDropDown(options);
    }

    @Override
    public void finishSendRecomendation(boolean success) {
        if (success) {
            mLiquidDialogView.showMessage(R.string.message_success_recomendation);
            mLiquidDialogView.dismiss();
        }
        else
            mLiquidDialogView.showMessage(R.string.message_error_recomendation);
    }

    @Override
    public void onClickButtonOK() {
        try {
            if (mLiquidDialogView.isFormValid()) {
                mLiquidDialogModel.saveIntoFirebase(mLiquidDialogView.getRecomendation());
            } else {
                mLiquidDialogView.showMessage(R.string.message_error_field_empty);
            }
        } catch (ParseException e){
            e.printStackTrace();
        }
    }
}
