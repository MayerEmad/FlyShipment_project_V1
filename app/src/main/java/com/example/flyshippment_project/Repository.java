package com.example.flyshippment_project;

import java.util.ArrayList;

import adapters_and_items.ApiShipmentSearch;
import adapters_and_items.ApiTripSearch;
import adapters_and_items.ShipmentItem;
import adapters_and_items.TripItem;

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

