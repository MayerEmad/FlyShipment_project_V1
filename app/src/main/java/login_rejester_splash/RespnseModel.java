package login_rejester_splash;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RespnseModel {

    @SerializedName("message")
    @Expose
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    /*String message;

    public RespnseModel(String message ) {
        this.message = message;

    }



    public String getmessage() {
        return message;
    }

    public void setmessage(String name) {
        this.message = message;
    }*/
}
