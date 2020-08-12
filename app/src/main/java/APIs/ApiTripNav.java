package APIs;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.flyshippment_project.MyViewModel;
import com.example.flyshippment_project.Repository;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import adapters_and_items.TripItem;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiTripNav extends AppCompatActivity {
    private ArrayList<TripItem> list;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    public void GetUserTripItemsFromServer() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://originaliereny.com/shipping/public/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        theApiFunctions client = retrofit.create(theApiFunctions.class);
        Call<List<TripItem>> call = client.get_user_trips(Repository.TheProfileItem.getUser_id());
        call.enqueue(new Callback<List<TripItem>>() {
            @Override
            public void onResponse(Call<List<TripItem>> call, Response<List<TripItem>> response) {
                if (!response.isSuccessful()) {
                    Log.i("ApiUserTrip get", "Response has error X(");
                    // retry
                }
                else
                {
                    list = (ArrayList<TripItem>) response.body();
                    MyViewModel.setUserTripLiveData(list);
                    //Log.i("Pretty Response ------",new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                }

            }

            @Override
            public void onFailure(Call<List<TripItem>> call, Throwable t) {
                Log.i("ApiUserTrip get", "Response failed :(");
            }
        });
    }
}
