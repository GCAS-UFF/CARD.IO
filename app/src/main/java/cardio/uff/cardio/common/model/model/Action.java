package cardio.uff.cardio.common.model.model;

import com.google.firebase.database.Exclude;

import java.util.LinkedHashMap;
import java.util.Map;

public class Action {

    private final String ACTION_PERFORMED = "AcoesRealizadas";
    private final String ACTION_RECOMENDED = "AcoesRecomendadas";

    private String id;
    private boolean performed;
    private String type;
    private long executedDate;

    public Action() {
    }

    public Action(String type) {
        this.type = type;
    }

    @Exclude
    public String getId() {
        return id;
    }
    @Exclude
    public void setId(String id) {
        this.id = id;
    }

    @Exclude
    public boolean isPerformed() {
        return performed;
    }

    @Exclude
    public void setPerformed(boolean performed) {
        this.performed = performed;
    }

    @Exclude
    public String getType() {
        return type;
    }

    public long getExecutedDate() {
        return executedDate;
    }

    public void setExecutedDate(long executedDate) {
        this.executedDate = executedDate;
    }

    @Exclude
    public String getConstantId (){
        if (isPerformed()) return ACTION_PERFORMED;
        return ACTION_RECOMENDED;
    }

    public Map<String,String> toMap (){
        Map<String,String> result = new LinkedHashMap<>();

        return result;
    }
}
