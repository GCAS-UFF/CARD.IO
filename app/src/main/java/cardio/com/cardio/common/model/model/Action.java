package cardio.com.cardio.common.model.model;

import com.google.firebase.database.Exclude;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cardio.com.cardio.common.util.Formater;

public class Action {

    private final String ACTION_PERFORMED = "AcoesRealizadas";
    private final String ACTION_RECOMENDED = "AcoesRecomendadas";

    private String id;
    private boolean performed;
    private String type;
    private long createDate;

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

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    @Exclude
    public String getConstantId (){
        if (isPerformed()) return ACTION_PERFORMED;
        return ACTION_RECOMENDED;
    }

    public Map<String,String> toMap (){
        Map<String,String> result = new HashMap<>();

        String performedStr = (isPerformed()) ? "Realizado" : "Não Realizado";

        result.put("Status: ", performedStr);
        result.put("Data de Criação: ", Formater.getStringFromDate(new Date(getCreateDate())));

        return result;
    }
}
