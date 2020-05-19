package com.example.flyshippment_project;

import java.util.ArrayList;

import adapters_and_items.ApiShipmentSearch;
import adapters_and_items.ApiTripSearch;
import adapters_and_items.ProfileItem;
import adapters_and_items.ShipmentItem;
import adapters_and_items.TripItem;

public class Repository
{
    public static ProfileItem TheProfileItem;
    public static void getFoOnceUserInfo(){
        // TODO : Background [sync] Task to get userInfo
    }

    // return LiveData from MyModelView
    public static ArrayList<ShipmentItem> getShipmentsFromApi()
    {
        // TODO : Background async Task to get Shipments
        ApiShipmentSearch task=new ApiShipmentSearch();
        task.DoTaskInBack(); //  Log.i("Repository getShips", "------> getting data from server...");
        return MyViewModel.getShipmentLiveData().getValue();
    }

    public static ArrayList<TripItem> getTripsFromApi()
    {
        // TODO : Background async Task to get Trips
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

