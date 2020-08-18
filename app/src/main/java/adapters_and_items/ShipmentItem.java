package adapters_and_items;

import com.google.gson.annotations.SerializedName;

public class ShipmentItem {

    private static final int NO_IMAGE_PROVIDED = -1;

    @SerializedName("ship_info_id") private int shipment_id;
    @SerializedName("user_info_id") private int user_id;
    @SerializedName("itemName") private String product_name;
    @SerializedName("from_country") private String country_from;
    @SerializedName("to_country")  private String country_to;
    @SerializedName("full_name")  private String profile_name;
    @SerializedName("deadline")  private String last_date;
    @SerializedName("image")  private String product_image;
    @SerializedName("user_image")  private String profile_image;
    @SerializedName("user_rate")  private double user_rate;
    @SerializedName("price")  private double reward;
    @SerializedName("weight")  private double weight;
    @SerializedName("count")  private double items_number;
    @SerializedName("url")  private String product_url;
    @SerializedName("editable")  private int isEditable;

    // update constructor
    public ShipmentItem( int shipment_id,String name,String from, String to, String date, double itemsNum,
                         double prodWeight, double price, String productUrl ,int isEditable)
    {
        this.shipment_id = shipment_id;
        weight=prodWeight;
        items_number=itemsNum;
        product_name=name;
        country_from =from;
        country_to =to;
        last_date=date;
        reward=price;
        product_url=productUrl;
        this.isEditable=isEditable;
    }

    // constructor
    public ShipmentItem(int shipment_id, int user_id, String prodImg, double prodWeight, double itemsNum, String name, String from, String to,
                        String date, double price, String profImg, String profName, double rate, String productUrl, int isEditable)
    {
        this.shipment_id = shipment_id;
        this.user_id=user_id;
        this.product_image=prodImg;
        weight=prodWeight;
        items_number=itemsNum;
        product_name=name;
        country_from =from;
        country_to =to;
        last_date=date;
        reward=price;
        profile_image=profImg;
        profile_name=profName;
        user_rate=rate;
        product_url=productUrl;
        this.isEditable=isEditable;
    }


    public String getProduct_image() { return this.product_image; }
    public double getItemWeight(){ return weight; }
    public double getTotalWeight(){ return weight*items_number; }
    public String getStrTotalWeight(){ return  Double.toString(weight*items_number)+"Kg"; }
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
    public double getItemPrice() { return reward; }
    public String getStrReward() {
        return "reward $"+Double.toString(reward);
    }
    public String getProfile_image() { return profile_image; }
    public double getItemsNumber() { return items_number; }
    public String getProduct_url() {
        return product_url;
    }
    public int getShipment_id() {
        return shipment_id;
    }
    public int getIsEditable() { return isEditable; }
    public int getUser_id() { return user_id; }

    public void setIsEditable(int isEditable) { this.isEditable = isEditable; }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public void setCountry_from(String country_from) {
        this.country_from = country_from;
    }

    public void setCountry_to(String country_to) {
        this.country_to = country_to;
    }

    public void setProfile_name(String profile_name) {
        this.profile_name = profile_name;
    }

    public void setLast_date(String last_date) {
        this.last_date = last_date;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public void setUser_rate(double user_rate) {
        this.user_rate = user_rate;
    }

    public void setItemPrice(double reward) {
        this.reward = reward;
    }

    public void setItemWeight(double weight) {
        this.weight = weight;
    }

    public void setItems_number(double items_number) {
        this.items_number = items_number;
    }

    public void setProduct_url(String product_url) {
        this.product_url = product_url;
    }

    public void setShipment_id(int shipment_id) {
        this.shipment_id = shipment_id;
    }
}