package adapters_and_items;

import java.util.ArrayList;

public class Repository
{

    // for RecyclerShipment we use LiveData
    // Data is stored in  SearchViewModel.getShipmentLiveData().getValue();
    public static ArrayList<ShipmentItem> getShipmentsFromApi()
    {
        // TODO -->Background Task to get Shipments
        ApiShipmentSearch task=new ApiShipmentSearch();
        task.DoTaskInBack();
        ArrayList<ShipmentItem>list=SearchViewModel.getShipmentLiveData().getValue();
        if(list==null){
            SearchViewModel.setShipmentLiveData(new ArrayList<ShipmentItem>());
            list=SearchViewModel.getShipmentLiveData().getValue();
        }
        return list ;  //Update Recycler View Adapters
    }

    public static ArrayList<TripItem> getTripsFromApi()
    {
        // TODO -->Background Task to get Trips
        ApiTripSearch task=new ApiTripSearch();
        task.DoTaskInBack();
        ArrayList<TripItem>list=SearchViewModel.getTripLiveData().getValue();
        if(list==null){
            SearchViewModel.setTripLiveData(new ArrayList<TripItem>());
            list=SearchViewModel.getTripLiveData().getValue();
        }
        return list ;  //Update Recycler View Adapters
    }

    public static ArrayList<ShipmentItem> getUserShipmentsFromApi()
    {
        return SearchViewModel.getUserShipmentLiveData().getValue();  //Update Recycler View Adapters
    }

    public static ArrayList<TripItem> getUserTripsFromApi()
    {
        ArrayList<TripItem>list=SearchViewModel.getUserTripLiveData().getValue();
        if(list==null){
            SearchViewModel.setUserTripLiveData(new ArrayList<TripItem>());
            list=SearchViewModel.getUserTripLiveData().getValue();
        }
        return list ;  //Update Recycler View Adapters
    }
}

