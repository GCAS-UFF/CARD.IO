package cardio.com.cardio.model;

public class CaixaDeTextoData extends Item{

    public static final String INPUT_DATE = "date";
    public static final String INPUT_TIME = "time";

    private String label;
    private String inputType;

    public CaixaDeTextoData(String label, String  inputType) {
        super(LayoutConstantes.LAYOUT_CAIXA_DE_TEXTO_DATA);
        this.label = label;
        this.inputType = inputType;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getInputType() {
        return inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }
}