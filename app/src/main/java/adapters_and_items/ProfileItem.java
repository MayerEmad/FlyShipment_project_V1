package adapters_and_items;

import android.util.Log;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfileItem
{
    @SerializedName("user_info_id") @Expose  private int user_id;
    @SerializedName("fullName") @Expose private String user_name;
    @SerializedName("image") @Expose private String user_image_url;
    @SerializedName("email") @Expose  private String user_mail;
    @SerializedName("phone") @Expose private String user_phone;
    @SerializedName("identification") @Expose private String user_passport;
    @SerializedName("rate") @Expose private double user_rate;
    @SerializedName("deal") @Expose private int user_deals;
    @SerializedName("trip") @Expose private int user_trips;
    @SerializedName("ship") @Expose private int user_shipments;


    public ProfileItem(int user_id, String user_name, String user_image_url, String user_mail, String user_phone,
                       String user_passport, double user_rate, int user_deals, int user_trips, int user_shipments) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_image_url = user_image_url;
        this.user_mail = user_mail;
        this.user_phone = user_phone;
        this.user_passport = user_passport;
        this.user_rate = user_rate;
        this.user_deals = user_deals;
        this.user_trips = user_trips;
        this.user_shipments = user_shipments;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        String formattedName="";
        boolean space=false;
        for(int i=0;i<user_name.length();i++) {
            if(user_name.charAt(i)!=' ')
                formattedName+=user_name.charAt(i);
            else if(!space){
                formattedName+=user_name.charAt(i);
                space=true;
            }
        }
        return formattedName;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_image_url() { return this.user_image_url; }

    public void setUser_image_url(String user_image_url) { this.user_image_url=user_image_url;
        Log.i("aaaSet", "setUser_image_url: ="+user_image_url);}

    public String getUser_mail() {
        return this.user_mail;
    }

    public void setUser_mail(String user_mail) {
        this.user_mail = user_mail;
    }

    public String getUser_phone() {
        return this.user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getUser_passport() {
     return this.user_passport;
    }

    public void setUser_passport(String user_passport) {
        this.user_passport = user_passport;
    }

    public double getUser_rate() {
        return this.user_rate;
    }

    public void setUser_rate(double user_rate) {
        this.user_rate = user_rate;
    }

    public int getUser_deals() {
        return this.user_deals;
    }

    public void setUser_deals(int user_deals) {
        this.user_deals = user_deals;
    }

    public int getUser_trips() {
        return this.user_trips;
    }

    public void setUser_trips(int user_trips) {
        this.user_trips = user_trips;
    }

    public int getUser_shipments() {
        return this.user_shipments;
    }

    public void setUser_shipments(int user_shipments) {
        this.user_shipments = user_shipments;
    }
}
