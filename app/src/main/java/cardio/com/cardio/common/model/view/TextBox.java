package cardio.com.cardio.common.model.view;

import android.text.InputType;

public class TextBox extends Item {

    public static final int INPUT_DECIMAL = InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL;
    public static final int INPUT_NUMBER = InputType.TYPE_CLASS_NUMBER;
    public static final int INPUT_TEXT = InputType.TYPE_CLASS_TEXT;
    public static final int INPUT_PASSWORD = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD;

    private String label;
    private String unidade;
    private String input;
    private int inputType;
    private String hint;
    private String value;

    public TextBox(String label, String unidade, int inputType) {
        super(LayoutConstantes.TEXT_BOX_LAYOUT);
        this.label = label;
        this.unidade = unidade;
        this.inputType = inputType;
    }

    public String getLabel() {
        return label;
    }

    public String getUnidade() {
        return unidade;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public int getInputType() {
        return inputType;
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
        return (value == null || value.isEmpty());
    }
}
