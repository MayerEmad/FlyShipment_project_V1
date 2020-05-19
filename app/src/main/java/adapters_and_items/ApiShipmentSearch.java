package adapters_and_items;

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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiShipmentSearch extends AppCompatActivity
{
    private ArrayList<ShipmentItem> list;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    public void DoTaskInBack()
    {
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("https://originaliereny.com/shipping/public/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        theApiFunctions client=retrofit.create(theApiFunctions.class);
        Call<List<ShipmentItem>> call = client.get_api_shipments();
       // call.execute().body();
        call.enqueue(new Callback<List<ShipmentItem>>()
        {
            @Override
            public void onResponse(Call<List<ShipmentItem>> call, Response<List<ShipmentItem>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(ApiShipmentSearch.this, "Response has error X(", Toast.LENGTH_SHORT).show();
                    return;
                }
                list = (ArrayList<ShipmentItem>) response.body();
                //Log.i("Pretty Response ------",new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));

                if(list==null){
                   // Log.i("ApiShipment onResponse", "-----> ask for response again");
                    Repository.getShipmentsFromApi();
                }
              //  Log.i("ApiShipment onResponse", "-----> go to ModelView set function");
                MyViewModel.setShipmentLiveData(list);
            }
            @Override
            public void onFailure(Call<List<ShipmentItem>> call, Throwable t) {
                Toast.makeText(ApiShipmentSearch.this, "Response failed :(", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
