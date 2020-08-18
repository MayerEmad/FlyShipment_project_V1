package APIs;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.flyshippment_project.FileUtil;
import com.example.flyshippment_project.MainActivity;
import com.example.flyshippment_project.MyViewModel;
import com.example.flyshippment_project.Repository;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import adapters_and_items.ShipmentItem;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
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

    public void GetShipmentItemsFromServer(final Context appCon)
    {
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("https://originaliereny.com/shipping/public/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        theApiFunctions client=retrofit.create(theApiFunctions.class);
        Call<List<ShipmentItem>> call = client.get_api_shipments();
        call.enqueue(new Callback<List<ShipmentItem>>()
        {
            @Override
            public void onResponse(Call<List<ShipmentItem>> call, Response<List<ShipmentItem>> response) {
                if (!response.isSuccessful()) {
                    Log.i("APIShipmemtSearch get", "Response has error = "+response.errorBody()+" code = "+response.code());
                }
                list = (ArrayList<ShipmentItem>) response.body();
                //Toast.makeText(appCon, "we received shipments", Toast.LENGTH_SHORT).show();
               // Log.i("Pretty Response ------",new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
                MyViewModel.setShipmentLiveData(list);
            }
            @Override
            public void onFailure(Call<List<ShipmentItem>> call, Throwable t) {
               // Toast.makeText(appCon, "", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void UploadShipmentItem(ShipmentItem item, Context mContext)
    {
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("https://originaliereny.com/shipping/public/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        theApiFunctions service=retrofit.create(theApiFunctions.class);

        RequestBody itemName = RequestBody.create(MediaType.parse("text/plain"), item.getProduct_name());
        RequestBody from_country = RequestBody.create(MediaType.parse("text/plain"), item.getCountry_from());
        RequestBody to_country = RequestBody.create(MediaType.parse("text/plain"), item.getCountry_to());
        RequestBody user_info_id = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(Repository.TheProfileItem.getUser_id()));
        RequestBody deadline = RequestBody.create(MediaType.parse("text/plain"), item.getLast_date());
        RequestBody productUrl = RequestBody.create(MediaType.parse("text/plain"), item.getProduct_url());
        RequestBody price = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(item.getItemPrice()));
        RequestBody weight = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(item.getItemWeight()));
        RequestBody count = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(item.getItemsNumber()));
        RequestBody isEditable = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(item.getIsEditable()));//always=1

        String filePath= item.getProduct_image();
        File productImageFile = new File(FileUtil.getPath(Uri.parse(filePath),mContext));
        RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), productImageFile);
        MultipartBody.Part image = MultipartBody.Part.createFormData("image", productImageFile.getName(), fileReqBody);

        Call<ShipmentItem> call=service.uploadShipmentItem(
                image,itemName,from_country,to_country,user_info_id,deadline,productUrl,price,weight,count,isEditable
        );
        call.enqueue(new Callback<ShipmentItem>() {
            @Override
            public void onResponse(Call<ShipmentItem> call, Response<ShipmentItem> response)
            {
                if (!response.isSuccessful()) {
                    Log.i("ApiShipmemtSearch get", "Response has error--------------- = "+response.body());
                    Repository.getUserShipmentsFromApi();
                }
               else {
                    Log.i("ApiShipmentSearch", "onResponse:------------------->  succeed on uploading "+response.body());
                }
            }
            @Override
            public void onFailure(Call<ShipmentItem> call, Throwable t) {
                Log.i("ApiShipmentSearch", "onFailure:-------->  failed to upload cause "+
                        t.getLocalizedMessage()+"\n"+t.getStackTrace().toString()+"\n"+t.getCause());
            }
        });
    }

    public void UpdateShipmentItem(ShipmentItem item, Context mContext)
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

        int itemId=item.getShipment_id();
        RequestBody itemName = RequestBody.create(MediaType.parse("text/plain"), item.getProduct_name());
        RequestBody from_country = RequestBody.create(MediaType.parse("text/plain"), item.getCountry_from());
        RequestBody to_country = RequestBody.create(MediaType.parse("text/plain"), item.getCountry_to());
        RequestBody user_info_id = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(Repository.TheProfileItem.getUser_id()));
        RequestBody deadline = RequestBody.create(MediaType.parse("text/plain"), item.getLast_date());
        RequestBody productUrl = RequestBody.create(MediaType.parse("text/plain"), item.getProduct_url());
        RequestBody price = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(item.getItemPrice()));
        RequestBody weight = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(item.getItemWeight()));
        RequestBody count = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(item.getItemsNumber()));
        RequestBody isEditable = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(item.getIsEditable()));//always=1


        String filePath= item.getProduct_image();
        Log.i("---Test---", "filepath= -----------------------------"+filePath);
        File productImageFile = new File(FileUtil.getPath(Uri.parse(filePath),mContext));
        RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), productImageFile);
        MultipartBody.Part image = MultipartBody.Part.createFormData("image", productImageFile.getName(), fileReqBody);

        Call<ShipmentItem> call=service.updateShipmentItem(
                itemId,image,itemName,from_country,to_country,user_info_id,deadline,productUrl,price,weight,count,isEditable
        );
        call.enqueue(new Callback<ShipmentItem>() {
            @Override
            public void onResponse(Call<ShipmentItem> call, Response<ShipmentItem> response)
            {
                if (!response.isSuccessful()) {
                    Log.i("ApiShipmem update", "Response has error--------------- = "+response.body());
                }
                else {
                    Log.i("ApiShipmem update", "onResponse:------------------->  succeed on updating "+response.message());
                }
            }
            @Override
            public void onFailure(Call<ShipmentItem> call, Throwable t) {
                Log.i("ApiShipmem update", "onFailure:-------->  failed to update cause "+
                        t.getLocalizedMessage()+"\n"+t.getStackTrace().toString()+"\n"+t.getCause());
            }
        });
    }

    public void UpdateShipmentItemNoImage(ShipmentItem item, Context mContext)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://originaliereny.com/shipping/public/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        theApiFunctions service = retrofit.create(theApiFunctions.class);

        int itemId=item.getShipment_id();
        String from_country = item.getCountry_from();
        String to_country = item.getCountry_to();
        double weight = item.getItemWeight();
        double items_number= item.getItemsNumber();
        double item_price=item.getItemPrice();
        String date = item.getLast_date();
        String item_name=item.getProduct_name();
        String item_url=item.getProduct_url();
        final int isEditable=item.getIsEditable();

        ShipmentItem updatedShipmentItem = new ShipmentItem(itemId,item_name,from_country,to_country,date,
                items_number, weight, item_price, item_url,isEditable);

        Call<ShipmentItem> call = service.updateShipmentItemNoImage(itemId,updatedShipmentItem);

        call.enqueue(new Callback<ShipmentItem>() {
            @Override
            public void onResponse(Call<ShipmentItem> call, Response<ShipmentItem> response)
            {
                if (!response.isSuccessful()) {
                    Log.i("ApiShipment update", "Response has error--------------- = "+response.message());
                }
                else {
                    Log.i("ApiShipmentSer update", "onResponse:-------------->  succeed on updating without image "+response.message());
                }
            }
            @Override
            public void onFailure(Call<ShipmentItem> call, Throwable t) {
                Log.i("ApiShipment update", "onFailure:-------->  failed to update cause "+
                        t.getLocalizedMessage()+"\n"+t.getStackTrace().toString()+"\n"+t.getCause());
            }
        });
    }

    public void DeleteShipmentItem(Integer id)
    {
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("https://originaliereny.com/shipping/public/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        theApiFunctions service=retrofit.create(theApiFunctions.class);
        Call<ShipmentItem> call=service.deleteShipmentItem(id);
        call.enqueue(new Callback<ShipmentItem>() {
            @Override
            public void onResponse(Call<ShipmentItem> call, Response<ShipmentItem> response)
            {
                if (!response.isSuccessful()) {
                    Log.i("ApiShipment Deleting", "Response has error--------------- = "+response.body());
                }
                else {
                    Log.i("ApiShipment Deleting", "onResponse:------------------->  succeed on deleting "+response.message());
                }
            }
            @Override
            public void onFailure(Call<ShipmentItem> call, Throwable t) {
                Log.i("ApiShipment Deleting", "onFailure:-------->  failed to delete "+
                        t.getLocalizedMessage()+"\n"+t.getStackTrace().toString()+"\n"+t.getCause());
            }
        });
    }
}
