package com.example.flyshippment_project;

import android.content.Context;

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

    //--------------------shipments-------------------------
    // return LiveData from MyModelView
    public static ArrayList<ShipmentItem> getShipmentsFromApi()
    {
        ApiShipmentSearch task=new ApiShipmentSearch();
        task.DoTaskInBack(); //  Log.i("Repository getShips", "------> getting data from server...");
        return MyViewModel.getShipmentLiveData().getValue();
    }
    public static void uploadShipmentItem(ShipmentItem item, Context CreateShipmentItemActivityContext)
    {
        ApiShipmentSearch task=new ApiShipmentSearch();
        task.UploadInBack(item,CreateShipmentItemActivityContext);
    }
    public static void updateShipmentItem(ShipmentItem item, Context EditShipmentItemActivityContext)
    {
        ApiShipmentSearch task=new ApiShipmentSearch();
        task.UpdateShipmentItem(item,EditShipmentItemActivityContext);
    }

    //--------------------------Trips-----------------------
    public static ArrayList<TripItem> getTripsFromApi()
    {
        ApiTripSearch task=new ApiTripSearch();
        task.DoTaskInBack();
        return MyViewModel.getTripLiveData().getValue();
    }

    public static void uploadTripItem(TripItem item)
    {
        ApiTripSearch task=new ApiTripSearch();
        task.UploadInBack(item);
    }

    public static void updateTripItem(TripItem item)
    {
        ApiTripSearch task=new ApiTripSearch();
        task.UpdateTripItem(item);
    }

//---------------------------------------

    public static ArrayList<ShipmentItem> getUserShipmentsFromApi()
    {
        return MyViewModel.getUserShipmentLiveData().getValue();
    }

    public static ArrayList<TripItem> getUserTripsFromApi()
    {
        return  MyViewModel.getUserTripLiveData().getValue();
    }
}

