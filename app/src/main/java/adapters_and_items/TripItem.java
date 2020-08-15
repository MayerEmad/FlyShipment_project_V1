package adapters_and_items;

import com.google.gson.annotations.SerializedName;

public class TripItem {
    @SerializedName("traveller_info_id")
    private int trip_id;
    @SerializedName("user_info_id")
    private String user_id;
    @SerializedName("from_country")
    private String country_from;
    @SerializedName("to_country")
    private String country_to;
    @SerializedName("full_name")
    private String profile_name;
    @SerializedName("date")
    private String meeting_date;
    @SerializedName("user_rate")
    private double user_rate;
    @SerializedName("available_weight")
    private double available_weight;
    @SerializedName("user_image")
    private String profile_image_url;
    @SerializedName("editable")
    private int isEditable;


    //uploading constructor
    public TripItem(String from, String to, String date, double availableWeight, String userId,int isEditable)
    {
        country_from =from;
        country_to =to;
        meeting_date=date;
        available_weight=availableWeight;
        user_id=userId;
        this.isEditable=isEditable;
    }

    public TripItem(int trip_id, String from, String to, String date, double availableWeight, String profImgUrl, String profName,
                    double rate, int isEditable)
    {
        this.trip_id = trip_id;
        country_from =from;
        country_to =to;
        meeting_date=date;
        available_weight=availableWeight;
        // consumed_weight=consumedWeight;
        profile_image_url=profImgUrl;
        profile_name=profName;
        user_rate=rate;
        this.isEditable=isEditable;
    }

    public int getTrip_id() { return this.trip_id; }

    public String getCountry_from() {
        return country_from;
    }

    public String getCountry_to() {
        return country_to;
    }

    public String getProfile_name() { return profile_name; }

    public String getMeeting_date() {
        return meeting_date;
    }

    public float getUser_rate() {
        return (float)user_rate;
    }

    public String getStrAvailable_weight() { return  "availabl weight "+Double.toString(available_weight)+"Kg"; }

    public Double getAvailable_weight() { return  available_weight; }

    public String getConsumed_weight() { return "consumed weight "+Double.toString(0)+"Kg"; }

    public String getProfile_image_url() {
        return profile_image_url;
    }

    public int getIsEditable() { return isEditable; }

    public void setIsEditable(int isEditable) { this.isEditable = isEditable; }

    public void setCountry_from(String country_from) {
        this.country_from = country_from;
    }

    public void setCountry_to(String country_to) {
        this.country_to = country_to;
    }

    public void setMeeting_date(String meeting_date) {
        this.meeting_date = meeting_date;
    }

    public void setAvailable_weight(double available_weight) {
        this.available_weight = available_weight;
    }
}
