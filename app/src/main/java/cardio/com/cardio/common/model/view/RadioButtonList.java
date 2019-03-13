package cardio.com.cardio.common.model.view;

import android.util.Log;

import java.util.Map;

public class RadioButtonList extends Item {

    private Map<String, Boolean> options;
    private String question;

    public RadioButtonList(Map<String, Boolean> options, String question) {
        super(LayoutConstantes.RADIO_BUTTON_LIST_LAYOUT);
        this.options = options;
        this.question = question;
    }

    public Map<String, Boolean> getOptions() {
        return options;
    }

    public void setOptions(Map<String, Boolean> options) {
        this.options = options;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOptionSelected (){
        for (Map.Entry<String, Boolean> entry : getOptions().entrySet()){
            if (entry.getValue()){
                return entry.getKey();
            }
        }

        return null;
    }

    @Override
    public boolean isEmpty() {
        for (Map.Entry<String, Boolean> entry : getOptions().entrySet()){

            if (entry.getValue()){
                return false;
            }
        }

        return true;
    }
}
