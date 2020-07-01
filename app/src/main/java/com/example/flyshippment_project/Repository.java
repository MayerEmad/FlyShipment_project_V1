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

    //------------------------shipments-------------------------
    // return LiveData from MyModelView

    public static ArrayList<ShipmentItem> getShipmentsFromApi()
    {
        ApiShipmentSearch task=new ApiShipmentSearch();
        task.GetShipmentItemsFromServer(); //  Log.i("Repository getShips", "------> getting data from server...");
        return MyViewModel.getShipmentLiveData().getValue();
    }
    public static void uploadShipmentItem(ShipmentItem item, Context CreateShipmentItemActivityContext)
    {
        ApiShipmentSearch task=new ApiShipmentSearch();
        task.UploadShipmentItem(item,CreateShipmentItemActivityContext);
    }
    public static void updateShipmentItem(ShipmentItem item, Context EditShipmentItemActivityContext)
    {
        ApiShipmentSearch task=new ApiShipmentSearch();
        task.UpdateShipmentItem(item,EditShipmentItemActivityContext);
    }
    public static void deleteShipmentItem(Integer id)
    {
        ApiShipmentSearch task=new ApiShipmentSearch();
        task.DeleteShipmentItem(id);
    }

    //-------------------------Trips-----------------------

    public static ArrayList<TripItem> getTripsFromApi()
    {
        ApiTripSearch task=new ApiTripSearch();
        task.GetTripItemsFromServer();
        return MyViewModel.getTripLiveData().getValue();
    }
    public static void uploadTripItem(TripItem item)
    {
        ApiTripSearch task=new ApiTripSearch();
        task.UploadTripItem(item);
    }
    public static void updateTripItem(TripItem item)
    {
        ApiTripSearch task=new ApiTripSearch();
        task.UpdateTripItem(item);
    }
    public static void deleteTripItem(Integer id)
    {
        ApiTripSearch task=new ApiTripSearch();
        task.DeleteTripItem(id);
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

