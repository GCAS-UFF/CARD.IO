package cardio.com.cardio.common.model.model;

import java.util.LinkedHashMap;
import java.util.Map;

import cardio.com.cardio.common.Firebase.FirebaseHelper;

public class Exercicio extends Action {


    private String name;
    private String intensity;
    private int duration;
    private Map<String, Boolean> symptons;

    public Exercicio() {
        super(FirebaseHelper.EXERCICIO_KEY);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntensity() {
        return intensity;
    }

    public void setIntensity(String intensity) {
        this.intensity = intensity;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Map<String, Boolean> getSymptons() {
        return symptons;
    }

    public void setSymptons(Map<String, Boolean> symptons) {
        this.symptons = symptons;
    }

    @Override
    public Map<String,String> toMap (){
        Map<String,String> result = new LinkedHashMap<>();

        result.put("Exercício: ", name);
        result.put("Intensidade: ", intensity);
        result.put("Duração: ", duration + " min");

        if (this.getExecutedDate() > 0){
            if(getSymptons() != null) {
                result.put("Sintomas:", "");
                for (Map.Entry<String, Boolean> entry : getSymptons().entrySet()){
                    if(entry.getValue())
                        result.put("  "+entry.getKey(), "Houve");
                    else
                        result.put("  "+entry.getKey(), "Não houve");
                }
            }

            result.put("", "");
        }

        return result;
    }
}
