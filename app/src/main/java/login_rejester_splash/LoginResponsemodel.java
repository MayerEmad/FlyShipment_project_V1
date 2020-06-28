package login_rejester_splash;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponsemodel {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Datalogmodel data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Datalogmodel getData() {
        return data;
    }

    public void setData(Datalogmodel data) {
        this.data = data;
    }
}
