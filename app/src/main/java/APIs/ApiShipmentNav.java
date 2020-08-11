package APIs;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.flyshippment_project.MyViewModel;
import com.example.flyshippment_project.Repository;

import java.util.ArrayList;
import java.util.List;

import adapters_and_items.ShipmentItem;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiShipmentNav extends AppCompatActivity {
    private ArrayList<ShipmentItem> list;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    public void GetUserShipmentFromServer()
    {
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("https://originaliereny.com/shipping/public/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        theApiFunctions client=retrofit.create(theApiFunctions.class);
        Call<List<ShipmentItem>> call = client.get_user_shipments(Repository.TheProfileItem.getUser_id());
        call.enqueue(new Callback<List<ShipmentItem>>()
        {
            @Override
            public void onResponse(Call<List<ShipmentItem>> call, Response<List<ShipmentItem>> response) {
                if (!response.isSuccessful()) {
                    Log.i("ApiUserShipment get", "Response has error = "+response.errorBody()+" code = "+response.code());
                }
                list = (ArrayList<ShipmentItem>) response.body();
                if(list==null) Repository.getUserShipmentsFromApi();
                MyViewModel.setUserShipmentLiveData(list);
                // Log.i("Pretty Response ------",new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));

            }
            @Override
            public void onFailure(Call<List<ShipmentItem>> call, Throwable t) {
              Log.i("ApiUserShipment get", "failed "+t.getLocalizedMessage()+"\n"+
                                                                t.getCause()+"\n"+
                                                                 t.getCause());
            }
        });
    }
}
