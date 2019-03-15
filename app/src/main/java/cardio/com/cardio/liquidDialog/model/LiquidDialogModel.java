package cardio.com.cardio.liquidDialog.model;

import cardio.com.cardio.common.model.model.Recomentation;
import cardio.com.cardio.liquidDialog.presenter.LiquidDialogPresenter;

public interface LiquidDialogModel {

    void setLiquidDialogPresenter(LiquidDialogPresenter liquidDialogPresenter);

    void initializeMetaDataListeners();

    void saveIntoFirebase(Recomentation recomentation);
}
