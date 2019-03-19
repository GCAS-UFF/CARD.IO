package cardio.uff.cardio.medicineDialog.view;

import java.text.ParseException;

import cardio.uff.cardio.common.model.model.Recomentation;

public interface MedicineDialogView {
    void showMedicineInformation(Recomentation recomentation);

    boolean isFormValid();

    void showMessage(int res);

    Recomentation getRecomendation() throws ParseException;

    void dismiss();
}
