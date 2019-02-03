package cardio.com.cardio.common.model.view;

import java.util.List;

public class CustomMapObject{
    private String id;
    private List<CustomPair> customPairs;

    public CustomMapObject(String id, List<CustomPair> customPairs) {
        this.id = id;
        this.customPairs = customPairs;
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
}
