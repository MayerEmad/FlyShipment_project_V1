package adapters_and_items;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.flyshippment_project.MyViewModel;
import com.example.flyshippment_project.Repository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//FixMe
// write code to get user Data
// this call must be done Once and at the beginning
// must be sycronance Task --->use execute() not enqueue()

public class ApiUserInfo extends AppCompatActivity
{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    public void DoTaskInBack()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://originaliereny.com/shipping/public/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        theApiFunctions client=retrofit.create(theApiFunctions.class);
        Call<ProfileItem> call = client.get_api_userInfo(1);  //FIXME hardcoded id
        // call.execute().body();
        call.enqueue(new Callback<ProfileItem>()
        {
            @Override
            public void onResponse(Call<ProfileItem> call, Response<ProfileItem> response) {
                if (!response.isSuccessful()) {
                    //Toast.makeText(ApiUserInfo.this, "Response has error X(", Toast.LENGTH_SHORT).show();
                    return;
                }
                Repository.TheProfileItem = response.body();
                //Log.i("Pretty Response ------",new GsonBuilder().setPrettyPrinting().create().toJson(response.body()));
            }

            @Override
            public void onFailure(Call<ProfileItem> call, Throwable t) {
                Toast.makeText(ApiUserInfo.this, "Response failed :(", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
