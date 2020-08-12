package APIs;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.flyshippment_project.MyViewModel;
import com.example.flyshippment_project.Repository;

import java.util.ArrayList;
import java.util.List;

import adapters_and_items.RequestItem;
import adapters_and_items.ShipmentRequestItem;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiRequests extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    public void ShipmentAskForTrip(int shipment_id, int tripId)
    {
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("https://originaliereny.com/shipping/public/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        theApiFunctions client=retrofit.create(theApiFunctions.class);
        Call<RequestItem> call = client.sendRequestFormShipmentToTrip(new RequestItem(shipment_id,tripId));
        call.enqueue(new Callback<RequestItem>() {
            @Override
            public void onResponse(Call<RequestItem> call, Response<RequestItem> response) {
                if (!response.isSuccessful())
                    Log.i("ApiRequest", "Response has error = "+response.message()+" code = "+response.code());
                else
                    Log.i("ApiRequest", "request to trip is sent successfully = "+response.message());
            }

            @Override
            public void onFailure(Call<RequestItem> call, Throwable t) {
                Log.i("ApiRequest", "failed to send request to trip\n"+
                        t.getLocalizedMessage()+"\n"+
                        t.getCause()+"\n"+
                        t.getCause());
            }
        });
    }

    public void GetShipmentsRequests()
    {
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("https://originaliereny.com/shipping/public/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        theApiFunctions client=retrofit.create(theApiFunctions.class);
        Call<List<ShipmentRequestItem>> call = client.get_shipments_requests(Repository.TheProfileItem.getUser_id());
        call.enqueue(new Callback<List<ShipmentRequestItem>>() {
            @Override
            public void onResponse(Call<List<ShipmentRequestItem>> call, Response<List<ShipmentRequestItem>> response) {
                if (!response.isSuccessful())
                    Log.i("ApiShipmentRequest", "Response has error = "+response.message()+" code = "+response.code());
                else{
                    Log.i("ApiShipmentRequest", "shipment requests received = "+response.message());
                    ArrayList<ShipmentRequestItem> list = (ArrayList<ShipmentRequestItem>) response.body();
                    MyViewModel.setShipmentRequestsLiveData(list);
                   // Log.i("Pretty Response ------",new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                }
            }

            @Override
            public void onFailure(Call<List<ShipmentRequestItem>> call, Throwable t) {
                Log.i("ApiShipmentRequest", "failed to receive shipment requests\n"+
                        t.getLocalizedMessage()+"\n"+
                        t.getCause()+"\n"+
                        t.getCause());
            }
        });
    }

    public void ApproveShipmentRequest(int request_id)
    {
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("https://originaliereny.com/shipping/public/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        theApiFunctions client=retrofit.create(theApiFunctions.class);
        Call<ShipmentRequestItem> call = client.approve_shipments_request(request_id);
        call.enqueue(new Callback<ShipmentRequestItem>() {
            @Override
            public void onResponse(Call<ShipmentRequestItem> call, Response<ShipmentRequestItem> response) {
                if (!response.isSuccessful())
                    Log.i("ApiShipmentRequest", "Response has error = "+response.message()+" code = "+response.code());
                else{
                    Log.i("ApiShipmentRequest", "shipment request accepted = "+response.message());
                }
            }
            @Override
            public void onFailure(Call<ShipmentRequestItem> call, Throwable t) {
                Log.i("ApiShipmentRequest", "failed to accept shipment request\n"+
                        t.getLocalizedMessage()+"\n"+ t.getCause()+"\n"+ t.getCause());
            }
        });
    }

    public void RejectShipmentRequest(int request_id)
    {
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("https://originaliereny.com/shipping/public/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        theApiFunctions client=retrofit.create(theApiFunctions.class);
        Call<ShipmentRequestItem> call = client.reject_shipments_request(request_id);
        call.enqueue(new Callback<ShipmentRequestItem>() {
            @Override
            public void onResponse(Call<ShipmentRequestItem> call, Response<ShipmentRequestItem> response) {
                if (!response.isSuccessful())
                    Log.i("ApiShipmentRequest", "Response has error = "+response.message()+" code = "+response.code());
                else{
                    Log.i("ApiShipmentRequest", "shipment request rejected = "+response.message());
                }
            }
            @Override
            public void onFailure(Call<ShipmentRequestItem> call, Throwable t) {
                Log.i("ApiShipmentRequest", "failed to reject shipment request\n"+
                        t.getLocalizedMessage()+"\n"+ t.getCause()+"\n"+ t.getCause());
            }
        });
    }
}
