package adapters_and_items;

public class TripItem {
    private static final int NO_IMAGE_PROVIDED = -1;  //TODO (Edit) put a customn photo

    private String country_from;
    private String country_to;
    private String profile_name;
    private String meeting_date;
    private double user_rate;
    private double available_weight;
    private String profile_image_url;

    private int ImageId = NO_IMAGE_PROVIDED;

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

    public String getAvailable_weight() { return  "availabl weight "+Double.toString(available_weight)+"Kg"; }

    public String getConsumed_weight() { return "consumed weight "+Double.toString(0)+"Kg"; }

    public String getProfile_image_url() {
        return profile_image_url;
    }
}
