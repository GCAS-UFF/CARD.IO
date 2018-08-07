package cardio.com.cardio.common.model.model;

import java.util.LinkedHashMap;
import java.util.Map;

import cardio.com.cardio.common.Firebase.FirebaseHelper;

public class Exercicio extends Action {


    private String exercise;
    private String intensity;
    private int duration;

    public Exercicio() {
        super(FirebaseHelper.EXERCICIO_KEY);
    }

    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
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

    @Override
    public Map<String,String> toMap (){
        Map<String,String> result = new LinkedHashMap<>();

        if (this.isPerformed()){
            result.putAll(super.toMap());
            result.put("Exercício: ", exercise);
            result.put("Intensidade: ", intensity);
            result.put("Duração: ", duration + " min");

            result.put("", "");
        }

        return result;
    }
}
