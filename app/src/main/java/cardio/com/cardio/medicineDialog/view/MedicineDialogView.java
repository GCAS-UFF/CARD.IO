package cardio.com.cardio.medicineDialog.view;

import java.text.ParseException;

import cardio.com.cardio.common.model.model.Recomentation;

public interface MedicineDialogView {
    void showMedicineInformation(Recomentation recomentation);

    boolean isFormValid();

    void showMessage(int res);

    Recomentation getRecomendation() throws ParseException;

    void dismiss();
}
