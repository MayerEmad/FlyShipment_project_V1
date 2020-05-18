package adapters_and_items;

import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Repository
{
    // for RecyclerShipment we use LiveData
    // Data is stored in  SearchViewModel.getShipmentLiveData().getValue();
    public static ArrayList<ShipmentItem> getShipmentsFromApi()
    {
        // TODO -->Background Task to get Shipments
        ApiShipmentSearch task=new ApiShipmentSearch();
        task.DoTaskInBack();
      //  Log.i("Repository getShips", "------> getting data from server...");
        return MyViewModel.getShipmentLiveData().getValue();
    }

    public static ArrayList<TripItem> getTripsFromApi()
    {
        // TODO -->Background Task to get Trips
        ApiTripSearch task=new ApiTripSearch();
        task.DoTaskInBack();
        return MyViewModel.getTripLiveData().getValue();
    }

    public static ArrayList<ShipmentItem> getUserShipmentsFromApi()
    {
        return MyViewModel.getUserShipmentLiveData().getValue();
    }

    public static ArrayList<TripItem> getUserTripsFromApi()
    {
        return  MyViewModel.getUserTripLiveData().getValue();
    }
}

