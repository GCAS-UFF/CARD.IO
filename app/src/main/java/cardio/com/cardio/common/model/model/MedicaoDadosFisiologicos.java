package cardio.com.cardio.common.model.model;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import cardio.com.cardio.common.Firebase.FirebaseHelper;

public class MedicaoDadosFisiologicos extends Action {
    private float weigth;
    private int bpm;
    private String bloodPressure;
    private String swelling;
    private String fatigue;

    public MedicaoDadosFisiologicos() {
        super(FirebaseHelper.MEDICAO_DADOS_FISIOLOGICOS_KEY);
    }

    public float getWeigth() {
        return weigth;
    }

    public void setWeigth(float weigth) {
        this.weigth = weigth;
    }

    public int getBpm() {
        return bpm;
    }

    public void setBpm(int bpm) {
        this.bpm = bpm;
    }

    public String getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public String getSwelling() {
        return swelling;
    }

    public void setSwelling(String swelling) {
        this.swelling = swelling;
    }

    public String getFatigue() {
        return fatigue;
    }

    public void setFatigue(String fatigue) {
        this.fatigue = fatigue;
    }

    @Override
    public Map<String,String> toMap (){
        Map<String,String> result = new LinkedHashMap<>();

        if (this.isPerformed()){
            result.putAll(super.toMap());
            result.put("Peso: ", weigth + " kg");
            result.put("Batimentos cardíacos: ", bpm + " bpm");
            result.put("Pressão arterial: ", bloodPressure + " mmHg");
            result.put("Inchaço: ", swelling);
            result.put("Fadiga: ", fatigue);
            result.put("", "");
        }

        return result;
    }
}
