package adapters_and_items;

import android.os.AsyncTask;

import java.util.ArrayList;

import search_classes.SearchViewModel;

public class Repository
{
    private static final String SHIPMENT_ITEMS_REQUEST_URL="https://originaliereny.com/shipping/public/api/shipInfo";
    private static final String TRIP_ITEMS_REQUEST_URL="https://originaliereny.com/shipping/public/api/travellerInfo";

    // for RecyclerShipment we use LiveData
    // Data is stored in  SearchViewModel.getShipmentLiveData().getValue();
    public static ArrayList<ShipmentItem> getShipmentsFromApi()
    {
        ShipmentApiAsyncTask task = new ShipmentApiAsyncTask();
        task.execute();
        return SearchViewModel.getShipmentLiveData().getValue();
    }

    public static ArrayList<TripItem> getTripsFromApi()
    {
        TripApiAsyncTask task = new TripApiAsyncTask();
        task.execute();
        return SearchViewModel.getTripLiveData().getValue();
    }


    // TODO --------------- Networking in BackGround Shipments---------------
    private static class ShipmentApiAsyncTask extends AsyncTask<Void, Void, ArrayList<ShipmentItem>>
    {
        @Override
        protected ArrayList<ShipmentItem> doInBackground(Void... voids)
        {
            ArrayList<ShipmentItem> result = ApiShipmentSearch.getApiShipmentItemsData(SHIPMENT_ITEMS_REQUEST_URL);
            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<ShipmentItem> data) {
            SearchViewModel.setShipmentLiveData(data);   //Update Recycler View Adapters
        }
    }

    // TODO --------------- Networking in BackGround Trips---------------
    private static class TripApiAsyncTask extends AsyncTask<Void, Void, ArrayList<TripItem>>
    {
        @Override
        protected ArrayList<TripItem> doInBackground(Void... voids)
        {
            ArrayList<TripItem> result = ApiTripSearch.getApiTripItemsData(TRIP_ITEMS_REQUEST_URL);
            return result;
        }

        @Override
        protected void onPostExecute(ArrayList<TripItem> data) {
            SearchViewModel.setTripLiveData(data);   //Update Recycler View Adapters
        }
    }
}

