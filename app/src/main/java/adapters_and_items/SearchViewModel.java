package adapters_and_items;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import adapters_and_items.ShipmentItem;
import adapters_and_items.TripItem;

public class SearchViewModel extends ViewModel
{
    //For Shipments
    private static MutableLiveData<ArrayList<ShipmentItem>> ShipmentListLiveData = new MutableLiveData<ArrayList<ShipmentItem>>();
    public static LiveData<ArrayList<ShipmentItem>> getShipmentLiveData() { return ShipmentListLiveData; }
    public static void setShipmentLiveData(ArrayList<ShipmentItem> data) { ShipmentListLiveData.setValue(data); }

    // For Trips
    private static MutableLiveData<ArrayList<TripItem>> TripListLiveData = new MutableLiveData<ArrayList<TripItem>>();
    public static LiveData<ArrayList<TripItem>> getTripLiveData() {
        return TripListLiveData;
    }
    public static void setTripLiveData(ArrayList<TripItem> data) { TripListLiveData.setValue(data); }

    //For user Shipments
    private static MutableLiveData<ArrayList<ShipmentItem>> UserShipmentListLiveData = new MutableLiveData<ArrayList<ShipmentItem>>();
    public static LiveData<ArrayList<ShipmentItem>> getUserShipmentLiveData() { return UserShipmentListLiveData; }
    public static void setUserShipmentLiveData(ArrayList<ShipmentItem> data) { UserShipmentListLiveData.setValue(data); }

    //For user Trips
    private static MutableLiveData<ArrayList<ShipmentItem>> UserTripLiveData = new MutableLiveData<ArrayList<ShipmentItem>>();
    public static LiveData<ArrayList<ShipmentItem>> getUserTripLiveData() { return UserTripLiveData; }
    public static void setUserTripLiveData(ArrayList<ShipmentItem> data) { UserTripLiveData.setValue(data); }

}

