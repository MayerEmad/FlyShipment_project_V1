package adapters_and_items;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.flyshippment_project.MyViewModel;
import com.example.flyshippment_project.Repository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


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
        theApiFunctions client=retrofit.create(theApiFunctions.class);
        Call<List<TripItem>> call = client.get_api_trips();
        call.enqueue(new Callback<List<TripItem>>()
        {
            @Override
            public void onResponse(Call<List<TripItem>> call, Response<List<TripItem>> response) {
                if (!response.isSuccessful()) {
                    Log.i("APITripSearch get", "Response has error X(");
                    //Toast.makeText(ApiTripSearch.this, "Response has error X(", Toast.LENGTH_SHORT).show();
                    return;
                }
                list = (ArrayList<TripItem>) response.body();
                if(list==null){
                    // Log.i("ApiShipment onResponse", "-----> ask for response again");
                    Repository.getTripsFromApi();
                }
                MyViewModel.setTripLiveData(list);
                //for(int i=0;i<list.size();i++) Log.i("response------>", list.get(i).getProfile_name());
            }
            @Override
            public void onFailure(Call<List<TripItem>> call, Throwable t) {
                Toast.makeText(ApiTripSearch.this, "Response failed :(", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void UploadInBack(TripItem item)
    {
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("https://originaliereny.com/shipping/public/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        theApiFunctions service=retrofit.create(theApiFunctions.class);

        /*RequestBody user_info_id = RequestBody.create(MediaType.parse("multipart/form-data"), "1212");
        RequestBody from_country = RequestBody.create(MediaType.parse("multipart/form-data"), item.getCountry_from());
        RequestBody to_country = RequestBody.create(MediaType.parse("multipart/form-data"), item.getCountry_to());
        RequestBody deadline = RequestBody.create(MediaType.parse("multipart/form-data"), item.getMeeting_date());
        RequestBody weight = RequestBody.create(MediaType.parse("multipart/form-data"), item.getAvailable_weight());

        Call<ResponseBody> call=service.uploadTripItem(user_info_id,from_country,to_country,weight,deadline);*/
        Call<TripItem> call=service.uploadTripItem("1212".trim(),item.getCountry_from().trim(),
                item.getCountry_to().trim(),item.getMeeting_date().trim(),item.getAvailable_weight().trim());

        call.enqueue(new Callback<TripItem>() {
            @Override
            public void onResponse(Call<TripItem> call, Response<TripItem> response) {
                if (!response.isSuccessful()) {
                    Log.i("APITripSearch post", response.message() +" ------- "+response.errorBody());
                    //Toast.makeText(ApiTripSearch.this, "Response has error X(", Toast.LENGTH_SHORT).show();
                    return;
                }
                else Log.i("APITripSearch Uploading", "onResponse:-------->  succeed on uploading ");
            }
            @Override
            public void onFailure(Call<TripItem> call, Throwable t) {
                Log.i("APITripSearch Uploading", "onFailure:-------->  failed to upload "+t.getCause());
            }
        });
    }

}
