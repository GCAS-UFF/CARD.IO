package cardio.com.cardio.common.model.model;

public class Recomentation {

    private Action action;
    private int frequencyByDay;

    public Recomentation() {
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
        action.setPerformed(false);
    }

    public int getFrequencyByDay() {
        return frequencyByDay;
    }

    public void setFrequencyByDay(int frequencyByDay) {
        this.frequencyByDay = frequencyByDay;
    }
}
