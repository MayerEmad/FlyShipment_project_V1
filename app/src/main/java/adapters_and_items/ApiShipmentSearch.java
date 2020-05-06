package adapters_and_items;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
        Call<List<ShipmentItem>> call = client.get_api_response_1();
        call.enqueue(new Callback<List<ShipmentItem>>()
        {
            @Override
            public void onResponse(Call<List<ShipmentItem>> call, Response<List<ShipmentItem>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(ApiShipmentSearch.this, "Response has error X(", Toast.LENGTH_SHORT).show();
                    return;
                }
                list = (ArrayList<ShipmentItem>) response.body();
                SearchViewModel.setShipmentLiveData(list);
                //for(int i=0;i<list.size();i++) Log.i("response------>", list.get(i).getProfile_name());
            }
            @Override
            public void onFailure(Call<List<ShipmentItem>> call, Throwable t) {
                Toast.makeText(ApiShipmentSearch.this, "Response failed :(", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
