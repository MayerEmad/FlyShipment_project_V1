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


public class ApiTripSearch extends AppCompatActivity {
    private ArrayList<TripItem> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void GetTripItemsFromServer() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://originaliereny.com/shipping/public/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        theApiFunctions client = retrofit.create(theApiFunctions.class);
        Call<List<TripItem>> call = client.get_api_trips();
        call.enqueue(new Callback<List<TripItem>>() {
            @Override
            public void onResponse(Call<List<TripItem>> call, Response<List<TripItem>> response) {
                if (!response.isSuccessful()) {
                    Log.i("APITripSearch get", "Response has error X(");
                    //Toast.makeText(ApiTripSearch.this, "Response has error X(", Toast.LENGTH_SHORT).show();
                    return;
                }
                list = (ArrayList<TripItem>) response.body();
                if (list == null) {
                    // Log.i("ApiShipment onResponse", "-----> ask for response again");
                   // Repository.getTripsFromApi();
                }
                MyViewModel.setTripLiveData(list);
                 Log.i("Pretty Response ------",new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));

            }

            @Override
            public void onFailure(Call<List<TripItem>> call, Throwable t) {
                Log.i("APITripSearch get", "Response failed :(");
                // Toast.makeText(ApiTripSearch.this, "Response failed :(", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void UploadTripItem(TripItem item) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://originaliereny.com/shipping/public/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        theApiFunctions service = retrofit.create(theApiFunctions.class);

        String userId = String.valueOf(Repository.TheProfileItem.getUser_id());
        String from_country = item.getCountry_from();
        String to_country = item.getCountry_to();
        Double weight = item.getAvailable_weight();
        String deadline = item.getMeeting_date();
        TripItem uploadingTripItem = new TripItem(from_country, to_country, deadline, weight, userId);

        Call<TripItem> call = service.uploadTripItem(uploadingTripItem);

        call.enqueue(new Callback<TripItem>() {
            @Override
            public void onResponse(Call<TripItem> call, Response<TripItem> response) {
                if (!response.isSuccessful()) {
                    Log.i("APITripSearch post", response.message() + " ------- " + response.errorBody());
                    //Toast.makeText(ApiTripSearch.this, "Response has error X(", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.i("APITripSearch Uploading", "onResponse:--------->  succeed on uploading " + response.body());
            }

            @Override
            public void onFailure(Call<TripItem> call, Throwable t) {
                Log.i("APITripSearch Uploading", "onFailure:----------------->  failed to upload " +
                        "  " + t.getLocalizedMessage() + "\n --------" + t.getCause());
            }
        });
    }

    public void UpdateTripItem(TripItem item) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://originaliereny.com/shipping/public/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        theApiFunctions service = retrofit.create(theApiFunctions.class);

        String userId = String.valueOf(Repository.TheProfileItem.getUser_id());
        String from_country = item.getCountry_from();
        String to_country = item.getCountry_to();
        Double weight = item.getAvailable_weight();
        String deadline = item.getMeeting_date();
        TripItem uploadingTripItem = new TripItem(from_country, to_country, deadline, weight, userId);

        Call<TripItem> call = service.updateTripItem(item.getTrip_id(),uploadingTripItem);

        call.enqueue(new Callback<TripItem>() {
            @Override
            public void onResponse(Call<TripItem> call, Response<TripItem> response) {
                if (!response.isSuccessful()) {
                    Log.i("APITripSearch response",  " BadResponse -------------> " + response.errorBody());
                    //Toast.makeText(ApiTripSearch.this, "Response has error X(", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.i("APITripSearch Uploading", "onResponse:--------->  succeed on updating " + response.body());
            }

            @Override
            public void onFailure(Call<TripItem> call, Throwable t) {
                Log.i("APITripSearch Uploading", "onFailure:----------------->  failed to update " +
                        "  " + t.getLocalizedMessage() + "\n --------" + t.getCause());
            }
        });
    }

    public void DeleteTripItem(Integer id)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://originaliereny.com/shipping/public/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        theApiFunctions service = retrofit.create(theApiFunctions.class);
        Call<TripItem> call = service.deleteTripItem(id);
        call.enqueue(new Callback<TripItem>()
        {
            @Override
            public void onResponse(Call<TripItem> call, Response<TripItem> response) {
                if (!response.isSuccessful()) {
                    Log.i("APITripSearch Deleting",  " BadResponse -------------> " + response.errorBody());
                }
                Log.i("APITripSearch Deleting", "onResponse:--------->  succeed on deleting " + response.message());
            }
            @Override
            public void onFailure(Call<TripItem> call, Throwable t) {
                Log.i("APITripSearch Deleting", "onFailure:----------------->  failed to Delete " +
                        "  " + t.getLocalizedMessage() + "\n --------" + t.getCause());
            }
        });
    }
}