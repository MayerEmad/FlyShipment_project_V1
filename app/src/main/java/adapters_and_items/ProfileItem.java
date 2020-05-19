package adapters_and_items;

import com.google.gson.annotations.SerializedName;

public class ProfileItem
{
     private int user_id;
     private String user_name;
     private String user_image_url;
     private String user_mail;
     private String user_phone;
     private String user_passport;
     private double user_rate;
     private int user_deals;
     private int user_trips;
     private int user_shipments;


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
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_image_url() {
        return user_image_url;
    }

    public void setUser_image_url(String user_image_url) {
        this.user_image_url = user_image_url;
    }

    public String getUser_mail() {
        return user_mail;
    }

    public void setUser_mail(String user_mail) {
        this.user_mail = user_mail;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getUser_passport() {
        return user_passport;
    }

    public void setUser_passport(String user_passport) {
        this.user_passport = user_passport;
    }

    public double getUser_rate() {
        return user_rate;
    }

    public void setUser_rate(double user_rate) {
        this.user_rate = user_rate;
    }

    public int getUser_deals() {
        return user_deals;
    }

    public void setUser_deals(int user_deals) {
        this.user_deals = user_deals;
    }

    public int getUser_trips() {
        return user_trips;
    }

    public void setUser_trips(int user_trips) {
        this.user_trips = user_trips;
    }

    public int getUser_shipments() {
        return user_shipments;
    }

    public void setUser_shipments(int user_shipments) {
        this.user_shipments = user_shipments;
    }
}
