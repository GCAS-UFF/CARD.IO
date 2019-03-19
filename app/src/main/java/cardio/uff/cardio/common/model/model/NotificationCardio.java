package cardio.uff.cardio.common.model.model;

public class NotificationCardio {
    private String id;
    private String tile;
    private String text;
    private String actionType;
    private Action actionId;
    private long scheduledTime;

    public NotificationCardio() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTile() {
        return tile;
    }

    public void setTile(String tile) {
        this.tile = tile;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public Action getActionId() {
        return actionId;
    }

    public void setActionId(Action actionId) {
        this.actionId = actionId;
    }

    public long getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(long scheduledTime) {
        this.scheduledTime = scheduledTime;
    }
}
