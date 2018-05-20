package cardio.com.cardio.model;

import android.text.InputType;

public class CaixaDeTexto extends Item {

    public static final int INPUT_DECIMAL = InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL;
    public static final int INPUT_NUMBER = InputType.TYPE_CLASS_NUMBER;
    public static final int INPUT_TEXT = InputType.TYPE_CLASS_TEXT;

    private String label;
    private String unidade;
    private String input;
    private int inputType;
    private String hint;

    public CaixaDeTexto(String label, String unidade, int inputType) {
        super(LayoutConstantes.LAYOUT_CAIXA_DE_TEXTO);
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
}
