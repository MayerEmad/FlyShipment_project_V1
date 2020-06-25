package adapters_and_items;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.flyshippment_project.FileUtil;
import com.example.flyshippment_project.MyViewModel;
import com.example.flyshippment_project.Repository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import Shipments_Trips_classes.CreateShipmentItemActivity;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
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
                    Log.i("APIShipmemtSearch get", "Response has error = "+response.errorBody()
                            +" code = "+response.code());
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

    public void UploadInBack(ShipmentItem item, Context mContext)
    {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("https://originaliereny.com/shipping/public/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        theApiFunctions service=retrofit.create(theApiFunctions.class);

        RequestBody itemName = RequestBody.create(MediaType.parse("text/plain"), item.getProduct_name());
        RequestBody from_country = RequestBody.create(MediaType.parse("text/plain"), item.getCountry_from());
        RequestBody to_country = RequestBody.create(MediaType.parse("text/plain"), item.getCountry_to());
        RequestBody user_info_id = RequestBody.create(MediaType.parse("text/plain"), "1");  //FixMe hardcoded
        RequestBody deadline = RequestBody.create(MediaType.parse("text/plain"), item.getLast_date());
        RequestBody productUrl = RequestBody.create(MediaType.parse("text/plain"), item.getProduct_url());
        RequestBody price = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(item.getReward()));
        RequestBody weight = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(item.getWeight()));
        RequestBody count = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(item.getItemsNumber()));
        //   multipart/form-data
        String filePath= item.getProduct_image();
       // Log.i("filePath", "------------------> "+filePath);
        //Log.i("APIShipment", "----------------->\n" + CreateShipmentItemActivity.class +"----------is shipActivity\n");

        File productImageFile = new File(FileUtil.getPath(Uri.parse(filePath),mContext));
        RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), productImageFile);
        MultipartBody.Part image = MultipartBody.Part.createFormData("image", productImageFile.getName(), fileReqBody);

        Call<ShipmentItem> call=service.uploadShipmentItem(
                image,itemName,from_country,to_country,user_info_id,deadline,productUrl,price,weight,count
        );
        call.enqueue(new Callback<ShipmentItem>() {
            @Override
            public void onResponse(Call<ShipmentItem> call, Response<ShipmentItem> response) {
                if (!response.isSuccessful()) {
                    Log.i("APIShipmemtSearch get", "Response has error--------------- = "+response.body()
                            +"\n------------- code = "+response.code()
                            +"\n------------- message = "+response.message()
                            +"\n------------- errorBody = "+response.errorBody()
                            +"\n------------- raw = "+response.raw());
                    return;
                }
               else Log.i("APIShipmentSearch", "onResponse:------------------->  succeed on uploading "+response.body());
            }
            @Override
            public void onFailure(Call<ShipmentItem> call, Throwable t) {
                Log.i("APIShipmentSearch", "onFailure:-------->  failed to upload cause"+
                        t.getLocalizedMessage()+"\n"+t.getStackTrace().toString()+"\n"+t.getCause());
            }
        });
    }

}
