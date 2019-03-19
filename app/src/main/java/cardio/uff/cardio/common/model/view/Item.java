package cardio.uff.cardio.common.model.view;

public abstract class Item {

    private int tipo;
    private boolean isEditable = true;

    public Item(int tipo) {
        this.tipo = tipo;
    }

    public int getTipo() {
        return tipo;
    }

    public abstract boolean isEmpty();

    public void setEditable(boolean editable) {
        this.isEditable = editable;
    }

    public boolean isEditable() {
        return isEditable;
    }
}
