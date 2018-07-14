package cardio.com.cardio.model;

public abstract class Item {

    private int tipo;

    public Item(int tipo) {
        this.tipo = tipo;
    }

    public int getTipo() {
        return tipo;
    }

    public abstract boolean isEmpty();
}
