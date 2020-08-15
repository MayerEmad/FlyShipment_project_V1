package APIs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.flyshippment_project.MyViewModel;
import com.example.flyshippment_project.Repository;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import adapters_and_items.RequestItem;
import adapters_and_items.ShipmentDealItem;
import adapters_and_items.ShipmentRequestItem;
import inbox_classes.ShipmentDealPathActivity;
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

    public void ShipmentAskForTrip(int shipment_id, int tripId) {
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

    // ------------------- Requests -------------------------

    public void GetShipmentsRequests() {
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

    public void ApproveShipmentRequest(int request_id) {
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

    public void RejectShipmentRequest(int request_id) {
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

    public void AfterRejectShipmentRequest(int request_id) {
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("https://originaliereny.com/shipping/public/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        theApiFunctions client=retrofit.create(theApiFunctions.class);
        Call<ShipmentRequestItem> call = client.remove_rejected_shipment_from_request(request_id);
        call.enqueue(new Callback<ShipmentRequestItem>() {
            @Override
            public void onResponse(Call<ShipmentRequestItem> call, Response<ShipmentRequestItem> response) {
                if (!response.isSuccessful())
                    Log.i("ApiShipmentRequest", "Response has error = "+response.message()+" code = "+response.code());
                else{
                    Log.i("ApiShipmentRequest", "rejected shipment removed from requests= "+response.message());
                }
            }
            @Override
            public void onFailure(Call<ShipmentRequestItem> call, Throwable t) {
                Log.i("ApiShipmentRequest", "failed remove rejected shipment from requests \n"+
                        t.getLocalizedMessage()+"\n"+ t.getCause()+"\n"+ t.getCause());
            }
        });
    }

    // ------------------ Deals -------------------------

    public void GetShipmentDeals() {
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("https://originaliereny.com/shipping/public/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        theApiFunctions client=retrofit.create(theApiFunctions.class);

        Call<List<ShipmentDealItem>> call = client.get_shipments_deals(Repository.TheProfileItem.getUser_id());
        call.enqueue(new Callback<List<ShipmentDealItem>>() {
            @Override
            public void onResponse(Call<List<ShipmentDealItem>> call, Response<List<ShipmentDealItem>> response) {
                if (!response.isSuccessful())
                    Log.i("ApiShipmentRequest", "Response has error = "+response.message()+" code = "+response.code());
                else{
                    Log.i("ApiShipmentRequest", "shipments deals received = "+response.message());
                    ArrayList<ShipmentDealItem> list = (ArrayList<ShipmentDealItem>) response.body();
                    MyViewModel.setShipmentDealsLiveData(list);
                     //Log.i("ApiShipmentRequest",new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                }
            }
            @Override
            public void onFailure(Call<List<ShipmentDealItem>> call, Throwable t) {
                Log.i("ApiShipmentRequest", "failed to receive shipment deals\n"+
                        t.getLocalizedMessage()+"\n"+ t.getCause()+"\n"+ t.getCause());
            }
        });
    }

    public void MoveStepShipmentDealPath(final int deal_id, final Context appCon) {
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("https://originaliereny.com/shipping/public/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        theApiFunctions client=retrofit.create(theApiFunctions.class);

        Call<ShipmentDealItem> call = client.move_step_shipment_deal_path(deal_id);
        call.enqueue(new Callback<ShipmentDealItem>() {
            @Override
            public void onResponse(Call<ShipmentDealItem> call, Response<ShipmentDealItem> response) {
                if (!response.isSuccessful())
                    Log.i("ApiShipmentRequest", "Response has error = "+response.message()+" code = "+response.code());
                else {
                    Log.i("ApiShipmentRequest", "shipments deal path moved = " + response.message());

                    //FIXME --------------- not the best way -------------------
                    ArrayList<ShipmentDealItem> deals = MyViewModel.getShipmentDealsLiveData().getValue();
                    for(ShipmentDealItem deal : deals) {
                        if (deal.getDeal_id() == deal_id)
                        { deal.setStatus_state(deal.getStatus_state() + 1);break; }
                    }
                    Intent intent = new Intent(appCon, ShipmentDealPathActivity.class);
                    intent.putExtra("DEAL_ID",deal_id);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    appCon.startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<ShipmentDealItem> call, Throwable t) {
                Log.i("ApiShipmentRequest", "failed to move shipment deal path\n"+
                        t.getLocalizedMessage()+"\n"+ t.getCause()+"\n"+ t.getCause());
            }
        });
    }

    public void SendShipmentDealRate(float rate, int user_id) {
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("https://originaliereny.com/shipping/public/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        theApiFunctions client=retrofit.create(theApiFunctions.class);

        Call<ShipmentDealItem> call = client.send_shipment_deal_rate(user_id,rate);
        call.enqueue(new Callback<ShipmentDealItem>() {
            @Override
            public void onResponse(Call<ShipmentDealItem> call, Response<ShipmentDealItem> response) {
                if (!response.isSuccessful())
                    Log.i("ApiShipmentRequest", "Response has error = "+response.message()+" code = "+response.code());
                else {
                    Log.i("ApiShipmentRequest", "shipments deal rate send= " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ShipmentDealItem> call, Throwable t) {
                Log.i("ApiShipmentRequest", "failed to send shipment deal rate\n"+
                        t.getLocalizedMessage()+"\n"+ t.getCause()+"\n"+ t.getCause());
            }
        });
    }

}
