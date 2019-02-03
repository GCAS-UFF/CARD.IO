package cardio.com.cardio.common.model.view;

import java.util.List;

public class CustomMapsList {
    private String title;
    private List<CustomMapObject> customMapObjectList;

    public CustomMapsList(String title, List<CustomMapObject> customMapObjectList) {
        this.title = title;
        this.customMapObjectList = customMapObjectList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<CustomMapObject> getCustomMapObjectList() {
        return customMapObjectList;
    }

    public void setCustomMapObjectList(List<CustomMapObject> customMapObjectList) {
        this.customMapObjectList = customMapObjectList;
    }
}
