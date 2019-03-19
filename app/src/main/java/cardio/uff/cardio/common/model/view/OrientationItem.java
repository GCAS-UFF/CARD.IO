package cardio.uff.cardio.common.model.view;

public class OrientationItem extends Item{


    private String title;
    private String description;

    public OrientationItem(String title, String description) {
        super(LayoutConstantes.ORIENTATION_ITEM);
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean isEmpty() {
        return (title == null || title.isEmpty() || description == null || description.isEmpty());
    }
}
