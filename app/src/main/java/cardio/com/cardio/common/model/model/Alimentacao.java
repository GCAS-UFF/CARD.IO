package cardio.com.cardio.common.model.model;

import java.util.LinkedHashMap;
import java.util.Map;

import cardio.com.cardio.common.Firebase.FirebaseHelper;

public class Alimentacao extends Action {

    private String food;
    private int quantidade;

    public Alimentacao() {
        super(FirebaseHelper.ALIMENTACAO_KEY);
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public Map<String,String> toMap (){
        Map<String,String> result = new LinkedHashMap<>();

        if (this.isPerformed()){
            result.putAll(super.toMap());
            result.put("Alimento: ", food);
        }

        result.put("Quantidade: ", String.valueOf(quantidade) + " ml");

        if (this.isPerformed()){
            result.put("", "");
        }
        return result;
    }
}
