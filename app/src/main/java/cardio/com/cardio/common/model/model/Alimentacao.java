package cardio.com.cardio.common.model.model;

import java.util.HashMap;
import java.util.Map;

import cardio.com.cardio.common.Firebase.ActionsFirebaseConstants;

public class Alimentacao extends Action {

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

    public Map<String,String> toMap (){
        Map<String,String> result = new HashMap<>();

        result.put("Alimento: ", aliment);
        result.put("Quantidade: ", String.valueOf(quantity));

        return result;
    }
}
