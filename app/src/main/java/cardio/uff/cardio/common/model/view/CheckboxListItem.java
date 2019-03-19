package cardio.uff.cardio.common.model.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CheckboxListItem extends Item{

    private Map<String, Boolean> options;
    private String question;

    public CheckboxListItem(Map<String, Boolean> options, String question) {
        super(LayoutConstantes.CHECKBOX_LIST_LAYOUT);
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

    public List<String> getOptionsSelecteds (){
        List<String> optionsSelecteds = new ArrayList<>();

        for (Map.Entry<String, Boolean> entry : getOptions().entrySet()){
            if (entry.getValue()){
                optionsSelecteds.add(entry.getKey());
            }
        }

        return optionsSelecteds;
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
