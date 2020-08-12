package adapters_and_items;

import com.google.gson.annotations.SerializedName;

public class ShipmentRequestItem {

    @SerializedName("request_id") private int request_id;
    @SerializedName("ship_id") private int shipment_id;
    @SerializedName("from_country") private String country_from;
    @SerializedName("to_country") private String country_to;
    @SerializedName("itemName") private String product_name;
    @SerializedName("itemImage") private String product_image;
    @SerializedName("count")  private int items_number;
    @SerializedName("price")  private double item_price;
    @SerializedName("weight") private double item_weight;
    @SerializedName("date")  private String date;
    // request sender
    @SerializedName("ship_user_id") private int sender_user_id;
    @SerializedName("ship_user_name") private String sender_user_name;
    @SerializedName("ship_user_image") private String sender_user_image;
    @SerializedName("ship_user_rate") private double sender_user_rate;
    // request receiver
    @SerializedName("traveller_id")  private int trip_id;
    @SerializedName("traveller_user_id") private int receiver_user_id;
    @SerializedName("traveller_user_name") private String receiver_user_name;
    @SerializedName("traveller_user_image") private String receiver_user_image;
    @SerializedName("traveller_user_rate") private double receiver_user_rate;

    @SerializedName("is_Approved") private int approving_state;
   // we need to add accepting

    public ShipmentRequestItem(int request_id, int shipment_id, String country_from, String country_to, String product_name,
                               String product_image, int items_number, double item_price, double item_weight, String date,
                               int sender_user_id, String sender_user_name, String sender_user_image, double sender_user_rate,
                               int trip_id, int receiver_user_id, String receiver_user_name, String receiver_user_image,
                               double receiver_user_rate, int approving_state) {
        this.request_id = request_id;
        this.shipment_id = shipment_id;
        this.country_from = country_from;
        this.country_to = country_to;
        this.product_name = product_name;
        this.product_image = product_image;
        this.items_number = items_number;
        this.item_price = item_price;
        this.item_weight = item_weight;
        this.date = date;
        this.sender_user_id = sender_user_id;
        this.sender_user_name = sender_user_name;
        this.sender_user_image = sender_user_image;
        this.sender_user_rate = sender_user_rate;
        this.trip_id = trip_id;
        this.receiver_user_id = receiver_user_id;
        this.receiver_user_name = receiver_user_name;
        this.receiver_user_image = receiver_user_image;
        this.receiver_user_rate = receiver_user_rate;
        this.approving_state = approving_state;
    }

    public int getRequest_id() {
        return request_id;
    }

    public int getShipment_id() {
        return shipment_id;
    }

    public String getCountry_from() {
        return country_from;
    }

    public String getCountry_to() {
        return country_to;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getProduct_image() {
        return product_image;
    }

    public int getItems_number() {
        return items_number;
    }

    public double getItem_price() {
        return item_price;
    }

    public double getItem_weight() {
        return item_weight;
    }

    public String getDate() {
        return date;
    }

    public int getSender_user_id() {
        return sender_user_id;
    }

    public String getSender_user_name() {
        return sender_user_name;
    }

    public String getSender_user_image() {
        return sender_user_image;
    }

    public float getSender_user_rate() {
        return (float)sender_user_rate;
    }

    public int getTrip_id() {
        return trip_id;
    }

    public int getReceiver_user_id() {
        return receiver_user_id;
    }

    public String getReceiver_user_name() {
        return receiver_user_name;
    }

    public String getReceiver_user_image() {
        return receiver_user_image;
    }

    public float getReceiver_user_rate() {
        return (float)receiver_user_rate;
    }

    public int getApproving_state() {
        return approving_state;
    }

    public String getStrTotalWeight(){ return  Double.toString(item_weight*items_number)+"Kg"; }
}
