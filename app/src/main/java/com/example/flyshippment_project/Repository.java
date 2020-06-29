package com.example.flyshippment_project;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

import APIs.ApiShipmentSearch;
import APIs.ApiTripSearch;
import APIs.ApiUserInfo;
import adapters_and_items.ProfileItem;
import adapters_and_items.ShipmentItem;
import adapters_and_items.TripItem;

public class Repository
{
    //--------------------UserInfo-------------------------

    public static ProfileItem TheProfileItem=null;

    public static void getUserInfo(SharedPreferences id){
        ApiUserInfo task=new ApiUserInfo();
        task.DoTaskInBack(id.getInt("userid",4));
    }

    public static void updateUserInfo(int id,Context EditProfilePageContext) {
        ApiUserInfo task=new ApiUserInfo();
        task.UpdateUserInfoApi(id,EditProfilePageContext);
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

