package cardio.uff.cardio.liquidDialog.model;

import cardio.uff.cardio.common.model.model.Recomentation;
import cardio.uff.cardio.liquidDialog.presenter.LiquidDialogPresenter;

public interface LiquidDialogModel {

    void setLiquidDialogPresenter(LiquidDialogPresenter liquidDialogPresenter);

    void initializeMetaDataListeners();

    void saveIntoFirebase(Recomentation recomentation);
}
