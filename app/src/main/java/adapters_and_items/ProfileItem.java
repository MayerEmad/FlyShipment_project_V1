package adapters_and_items;

import com.google.gson.annotations.SerializedName;

public class ProfileItem {
    @SerializedName("user_name") private String profile_name;

    public ProfileItem(String profile_name)
    {
        this.profile_name=profile_name;
    }

    public String getProfile_name() {
        return profile_name;
    }

    public void setProfile_name(String profile_name) {
        this.profile_name = profile_name;
    }
}
