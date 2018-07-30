package cardio.com.cardio.common.model.view;

public class DateTextBox extends Item{

    public static final String INPUT_DATE = "date";
    public static final String INPUT_TIME = "time";

    private String label;
    private String inputType;
    private String value;

    public DateTextBox(String label, String  inputType) {
        super(LayoutConstantes.DATE_TEXT_BOX_LAYOUT);
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