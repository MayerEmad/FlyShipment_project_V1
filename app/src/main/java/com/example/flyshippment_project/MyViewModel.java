package com.example.flyshippment_project;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.jar.Attributes;

import adapters_and_items.ShipmentDealItem;
import adapters_and_items.ShipmentItem;
import adapters_and_items.ShipmentRequestItem;
import adapters_and_items.TripItem;

public class MyViewModel extends ViewModel
{
    //For Shipments
    private static MutableLiveData<ArrayList<ShipmentItem>> ShipmentListLiveData;
    public static LiveData<ArrayList<ShipmentItem>> getShipmentLiveData() {
        if(ShipmentListLiveData==null)
            ShipmentListLiveData=new MutableLiveData<ArrayList<ShipmentItem>>();
        return ShipmentListLiveData;
    }
    public static void setShipmentLiveData(ArrayList<ShipmentItem> data) {
      //  Log.i("MyViewModel Set", "-----> before observer");
        ShipmentListLiveData.setValue(data);
    }

    // For Trips
    private static MutableLiveData<ArrayList<TripItem>> TripListLiveData;
    public static LiveData<ArrayList<TripItem>> getTripLiveData() {
        if(TripListLiveData==null)
            TripListLiveData=new MutableLiveData<ArrayList<TripItem>>();
        return TripListLiveData;
    }
    public static void setTripLiveData(ArrayList<TripItem> data) {
        TripListLiveData.setValue(data);
    }

    //For user Shipments
    private static MutableLiveData<ArrayList<ShipmentItem>> UserShipmentListLiveData;
    public static LiveData<ArrayList<ShipmentItem>> getUserShipmentLiveData() {
        if(UserShipmentListLiveData==null)
            UserShipmentListLiveData= new MutableLiveData<ArrayList<ShipmentItem>>();
        return UserShipmentListLiveData;
    }
    public static void setUserShipmentLiveData(ArrayList<ShipmentItem> data) {
        UserShipmentListLiveData.setValue(data);
    }

    //For user Trips
    private static MutableLiveData<ArrayList<TripItem>> UserTripLiveData ;
    public static LiveData<ArrayList<TripItem>> getUserTripLiveData() {
        if(UserTripLiveData==null)
            UserTripLiveData=new MutableLiveData<ArrayList<TripItem>>();
        return UserTripLiveData;
    }
    public static void setUserTripLiveData(ArrayList<TripItem> data) {
        UserTripLiveData.setValue(data);
    }

    //For Shipments requests
    private static MutableLiveData<ArrayList<ShipmentRequestItem>> ShipmentRequestsListLiveData;

    public static LiveData<ArrayList<ShipmentRequestItem>> getShipmentRequestsLiveData() {
        if(ShipmentRequestsListLiveData==null)
            ShipmentRequestsListLiveData= new MutableLiveData<>();
        return ShipmentRequestsListLiveData;
    }
    public static void setShipmentRequestsLiveData(ArrayList<ShipmentRequestItem> data) {
        ShipmentRequestsListLiveData.setValue(data);
    }
    // For shipments deals
    private static MutableLiveData<ArrayList<ShipmentDealItem>> ShipmentDealsListLiveData;

    public static LiveData<ArrayList<ShipmentDealItem>> getShipmentDealsLiveData() {
        if(ShipmentDealsListLiveData==null)
            ShipmentDealsListLiveData= new MutableLiveData<>();
        return ShipmentDealsListLiveData;
    }
    public static void setShipmentDealsLiveData(ArrayList<ShipmentDealItem> data) {
        ShipmentDealsListLiveData.setValue(data);
    }
}

