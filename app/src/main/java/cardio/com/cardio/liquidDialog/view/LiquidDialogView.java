package cardio.com.cardio.liquidDialog.view;

import java.text.ParseException;
import java.util.Map;

import cardio.com.cardio.common.model.model.Recomentation;

public interface LiquidDialogView {

    Recomentation getRecomendation() throws ParseException;

    boolean isFormValid();

    void showMessage(int res);

    void populateQuantityReferenceDropDown(Map<String, String> options);

    void dismiss();
}
