package cardio.com.cardio.common.model.view;

import android.util.Log;

import java.util.Map;

public class DropDown extends Item{

    private Map<String, String> options;
    private String hint;
    private String value;

    public DropDown(Map<String, String> options, String hint) {
        super(LayoutConstantes.DROP_DOWN_LAYOUT);
        this.options = options;
        this.hint = hint;
    }

    public Map<String, String> getOptions() {
        return options;
    }

    public void setOptions(Map<String, String> options) {
        this.options = options;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean isEmpty() {
        return (value == null || hint == null || value.equals(hint));
    }
}
