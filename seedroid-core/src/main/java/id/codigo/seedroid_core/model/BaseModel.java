package id.codigo.seedroid_core.model;


/**
 * Created by Lukma on 5/10/2016.
 */
public class BaseModel {
    private int status;
    private String message;
    private String displayMessage;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDisplayMessage() {
        return displayMessage;
    }

    public void setDisplayMessage(String displayMessage) {
        this.displayMessage = displayMessage;
    }
}
