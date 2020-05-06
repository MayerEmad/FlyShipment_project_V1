package adapters_and_items;
import android.graphics.Bitmap;

public class ShipmentItem {

    private static final int NO_IMAGE_PROVIDED = -1;  //TODO (Edit) put a customn photo

    private String product_name, country_from, country_to,profile_name,last_date;
    private double user_rate,reward,weight,items_number;
    private Bitmap product_image,profile_image;
    private int ImageId = NO_IMAGE_PROVIDED;


    // constructor
    public ShipmentItem(Bitmap prodImg, double prodWeight, double itemsNum, String name, String from, String to, String date,
                        double money, Bitmap profImg, String profName, double rate)
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

    public Bitmap getProduct_image() { return product_image; }
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
    public Bitmap getProfile_image() { return profile_image; }




    /*public boolean hasImage() {
        return ImageId != NO_IMAGE_PROVIDED;
    }*/

}