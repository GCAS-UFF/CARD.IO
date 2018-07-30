package cardio.com.cardio.common.model.model;

import cardio.com.cardio.common.Firebase.ActionsFirebaseConstants;

public class Exercicio extends Action {


    private String exercise;
    private String intensity;
    private int duration;

    public Exercicio() {
        super(ActionsFirebaseConstants.EXERCICIO_KEY);
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
}
