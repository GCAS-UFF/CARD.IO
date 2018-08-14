package cardio.com.cardio.common.model.model;

import android.content.Context;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import cardio.com.cardio.common.Firebase.FirebaseHelper;

public class Alimentacao extends Action {

    private String food;
    private int quantity;

    public Alimentacao() {
        super(FirebaseHelper.ALIMENTACAO_KEY);
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public Map<String,String> toMap (){
        Map<String,String> result = new LinkedHashMap<>();

        if (this.isPerformed()){
            result.putAll(super.toMap());
            result.put("Alimento: ", food);
        }

        result.put("Quantidade: ", String.valueOf(quantity) + " ml");

        if (this.isPerformed()){
            result.put("", "");
        }
        return result;
    }
}
