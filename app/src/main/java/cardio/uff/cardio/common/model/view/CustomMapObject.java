package cardio.uff.cardio.common.model.view;

import java.util.List;

public class CustomMapObject{
    private String id;
    private List<CustomPair> customPairs;
    private boolean haveAddButton;

    public CustomMapObject(String id, List<CustomPair> customPairs, boolean haveAddButton) {
        this.id = id;
        this.customPairs = customPairs;
        this.haveAddButton = haveAddButton;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<CustomPair> getCustomPairs() {
        return customPairs;
    }

    public void setCustomPairs(List<CustomPair> customPairs) {
        this.customPairs = customPairs;
    }

    public boolean isHaveAddButton() {
        return haveAddButton;
    }

    public void setHaveAddButton(boolean haveAddButton) {
        this.haveAddButton = haveAddButton;
    }
}
