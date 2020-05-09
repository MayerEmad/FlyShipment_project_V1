package adapters_and_items;
import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

public class ShipmentItem {

    private static final int NO_IMAGE_PROVIDED = -1;

    @SerializedName("itemName") private String product_name;
    @SerializedName("from_country") private String country_from;
    @SerializedName("to_country")  private String country_to;
    @SerializedName("user_name")  private String profile_name;
    @SerializedName("deadline")  private String last_date;
    @SerializedName("image")  private String product_image;
    @SerializedName("url")    private String profile_image;
    @SerializedName("user_rate")  private double user_rate;
    @SerializedName("price")  private double reward;
    @SerializedName("weight")  private double weight;
    @SerializedName("count")  private double items_number;
    private int ImageId = NO_IMAGE_PROVIDED;

    // constructor
    public ShipmentItem(String prodImg, double prodWeight, double itemsNum, String name, String from, String to, String date,
                          double money, String profImg, String profName, double rate)
    {
        product_image=prodImg;
        weight=prodWeight;
        items_number=itemsNum;
        product_name=name;
        country_from =from;
        country_to =to;
        last_date=date;
        reward=money;
        profile_image=profImg;
        profile_name=profName;
        user_rate=rate;
    }


    public String getProduct_image() { return product_image; }
    public double getWeight(){ return weight*items_number; }
    public String getStrWeight(){
        return  Double.toString(weight*items_number)+"Kg";
    }
    public String getProduct_name() {
        return product_name;
    }
    public String getCountry_from() {
        return country_from;
    }
    public String getCountry_to() {
        return country_to;
    }
    public String getProfile_name() {
        return profile_name;
    }
    public String getLast_date() {
        return last_date;
    }
    public float getUserRate() {
        return (float)user_rate;
    }
    public double getReward() { return reward; }
    public String getStrReward() {
        return "reward $"+Double.toString(reward);
    }
    public String getProfile_image() { return profile_image; }

    /*public boolean hasImage() {
        return ImageId != NO_IMAGE_PROVIDED;
    }*/

}