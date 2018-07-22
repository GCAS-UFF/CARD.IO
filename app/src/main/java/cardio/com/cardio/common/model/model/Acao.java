package cardio.com.cardio.common.model.model;

import com.google.firebase.database.Exclude;

public class Acao {

    private final String ACTION_PERFORMED = "AcoesRealizadas";
    private final String ACTION_RECOMENDED = "AcoesRecomendadas";

    private String id;
    private boolean performed;
    private String type;
    private long createDate;

    public Acao(String type) {
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
}
