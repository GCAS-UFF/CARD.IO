package cardio.uff.cardio.liquidDialog.view;

import java.text.ParseException;
import java.util.Map;

import cardio.uff.cardio.common.model.model.Recomentation;

public interface LiquidDialogView {

    Recomentation getRecomendation() throws ParseException;

    boolean isFormValid();

    void showMessage(int res);

    void populateQuantityReferenceDropDown(Map<String, String> options);

    void dismiss();
}
