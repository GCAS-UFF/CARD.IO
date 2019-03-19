package cardio.uff.cardio.liquidDialog.presenter;

import java.util.Map;

public interface LiquidDialogPresenter {
    void initializeLiquidInformation(String id);

    double calculateLiquid(String quantity, String dosageReference);

    void onClickButtonOK();

    void finishLoadBebidasMetadata(Map<String, String> options);

    void finishSendRecomendation(boolean success);
}
