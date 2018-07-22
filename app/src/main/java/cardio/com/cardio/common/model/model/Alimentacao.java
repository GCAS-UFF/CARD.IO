package cardio.com.cardio.common.model.model;

import cardio.com.cardio.common.Firebase.ActionsFirebaseConstants;

public class Alimentacao extends Acao {

    private String aliment;
    private int quantity;

    public Alimentacao() {
        super(ActionsFirebaseConstants.ALIMENTACAO_KEY);
    }

    public String getAliment() {
        return aliment;
    }

    public void setAliment(String aliment) {
        this.aliment = aliment;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
