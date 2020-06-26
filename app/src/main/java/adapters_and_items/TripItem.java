package adapters_and_items;

import com.google.gson.annotations.SerializedName;

public class TripItem {
    private static final int NO_IMAGE_PROVIDED = -1;  //TODO (Edit) put a customn photo

    @SerializedName("user_info_id")
    private String user_id;
    @SerializedName("from_country")
    private String country_from;
    @SerializedName("to_country")
    private String country_to;
    @SerializedName("user_name")
    private String profile_name;
    @SerializedName("date")
    private String meeting_date;
    @SerializedName("user_rate")
    private double user_rate;
    @SerializedName("available_weight")
    private double available_weight;
    @SerializedName("user_image")
    private String profile_image_url;

    private int ImageId = NO_IMAGE_PROVIDED;

    //uploading constructor
    public TripItem(String from, String to, String date, double availableWeight, String userId)
    {
        country_from =from;
        country_to =to;
        meeting_date=date;
        available_weight=availableWeight;
        user_id=userId;
    }
    public TripItem(String from, String to, String date, double availableWeight,String profImgUrl, String profName, double rate)
    {
        country_from =from;
        country_to =to;
        meeting_date=date;
        available_weight=availableWeight;
        // consumed_weight=consumedWeight;
        profile_image_url=profImgUrl;
        profile_name=profName;
        user_rate=rate;
    }

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
}
