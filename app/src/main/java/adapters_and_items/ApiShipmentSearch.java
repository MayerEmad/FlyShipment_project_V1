package adapters_and_items;

import android.net.Uri;
import android.os.Bundle;
import android.os.FileUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.flyshippment_project.MyViewModel;
import com.example.flyshippment_project.Repository;
import com.google.gson.GsonBuilder;

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
               // Log.i("Pretty Response ------",new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));

                if(list==null){
                   // Log.i("ApiShipment onResponse", "-----> ask for response again");
                    Repository.getShipmentsFromApi();
                }
              //  Log.i("ApiShipment onResponse", "-----> go to ModelView set function");
                MyViewModel.setShipmentLiveData(list);
            }
            @Override
            public void onFailure(Call<List<ShipmentItem>> call, Throwable t) {
                //Toast.makeText(ApiShipmentSearch.this, "Response failed :(", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void UploadInBack(ShipmentItem item)
    {
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("https://originaliereny.com/shipping/public/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        theApiFunctions service=retrofit.create(theApiFunctions.class);

        RequestBody itemName = RequestBody.create(MediaType.parse("multipart/form-data"), item.getProduct_name());
        RequestBody from_country = RequestBody.create(MediaType.parse("multipart/form-data"), item.getCountry_from());
        RequestBody to_country = RequestBody.create(MediaType.parse("multipart/form-data"), item.getCountry_to());
        RequestBody user_info_id = RequestBody.create(MediaType.parse("multipart/form-data"), "1001");
        RequestBody deadline = RequestBody.create(MediaType.parse("multipart/form-data"), item.getLast_date());RequestBody user_rate = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(item.getUserRate()));
        RequestBody price = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(item.getReward()));
        RequestBody weight = RequestBody.create(MediaType.parse("multipart/form-data"), item.getStrWeight());
        RequestBody count = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(item.getItemsNumber()));


        File productImageFile = new File(item.getProduct_image());
        RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), productImageFile);
        MultipartBody.Part image = MultipartBody.Part.createFormData("image", productImageFile.getName(), fileReqBody);


        Call<ResponseBody> call=service.uploadShipmentItem(
                image,itemName,from_country,to_country,user_info_id,deadline,price,weight,count
        );
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.i("APIShipmentSearch", "onResponse:-------->  succeed on uploading");
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("APIShipmentSearch", "onFailure:-------->  failed to upload");
                Log.i("Pretty Response ------",t.getLocalizedMessage());
            }
        });
    }

}
