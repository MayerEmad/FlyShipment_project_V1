package adapters_and_items;

import com.google.gson.annotations.SerializedName;

public class RequestItem {
    @SerializedName("ship_id") private int shipment_id;
    @SerializedName("traveller_id") private int trip_id;

    public RequestItem(int shipment_id, int trip_id) {
        this.shipment_id = shipment_id;
        this.trip_id = trip_id;
    }
}
