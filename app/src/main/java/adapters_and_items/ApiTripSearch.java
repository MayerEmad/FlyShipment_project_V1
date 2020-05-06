package adapters_and_items;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import search_classes.SearchViewModel;

interface ApiTripInterface
{
    @GET("travellerInfo")
    Call<List<TripItem>> get_api_response();
}

public class ApiTripSearch extends AppCompatActivity
{
    private ArrayList<TripItem> list;
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
        ApiTripInterface client=retrofit.create(ApiTripInterface.class);
        Call<List<TripItem>> call = client.get_api_response();
        call.enqueue(new Callback<List<TripItem>>()
        {
            @Override
            public void onResponse(Call<List<TripItem>> call, Response<List<TripItem>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(ApiTripSearch.this, "Response has error X(", Toast.LENGTH_SHORT).show();
                    return;
                }
                list = (ArrayList<TripItem>) response.body();
                SearchViewModel.setTripLiveData(list);
                //for(int i=0;i<list.size();i++) Log.i("response------>", list.get(i).getProfile_name());
            }
            @Override
            public void onFailure(Call<List<TripItem>> call, Throwable t) {
                Toast.makeText(ApiTripSearch.this, "Response failed :(", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
