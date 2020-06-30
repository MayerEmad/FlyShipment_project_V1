package APIs;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.flyshippment_project.FileUtil;
import com.example.flyshippment_project.Repository;

import java.io.File;
import java.util.Arrays;

import adapters_and_items.ProfileItem;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

 /*
 FixMe
  -----------this call must be done Once and at the beginning
  -----------must be sycronance Task --->use execute() not enqueue()
 */

public class ApiUserInfo extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void DoTaskInBack(Integer id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://originaliereny.com/shipping/public/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        theApiFunctions client = retrofit.create(theApiFunctions.class);

        Call<ProfileItem> call = client.get_api_userInfo(id);

        call.enqueue(new Callback<ProfileItem>() {
            @Override
            public void onResponse(Call<ProfileItem> call, Response<ProfileItem> response) {
                if (!response.isSuccessful()) {
                    Log.i("ApiUserInfo badresponse", "Response has error X(");
                    return;
                }
                Repository.TheProfileItem = response.body();
                Log.i("ApiUserInfo GoodRespons", "Response ----------> ="+response.body());
            }

            @Override
            public void onFailure(Call<ProfileItem> call, Throwable t) {
                Log.i("ApiUserInfo failed", "Response ----------> ="+t.getStackTrace());

            }
        });
    }

    public void UpdateUserInfoApi(Integer id , Context mContext) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://originaliereny.com/shipping/public/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        theApiFunctions client = retrofit.create(theApiFunctions.class);

        //FIXME
        ProfileItem user=Repository.TheProfileItem;
        RequestBody fullName = RequestBody.create(MediaType.parse("text/plain"), user.getUser_name());
        RequestBody phone = RequestBody.create(MediaType.parse("text/plain"), user.getUser_phone());
        RequestBody email = RequestBody.create(MediaType.parse("text/plain"), user.getUser_mail());
        RequestBody identification = RequestBody.create(MediaType.parse("text/plain"), user.getUser_passport());
        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), user.getUser_nick_name());
        String filePath= user.getUser_image_url();

        Log.i("aaa", "UpdateUserInfoApi:---------> "+filePath);
        File userImageFile = new File(FileUtil.getPath(Uri.parse(filePath),mContext));
        RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), userImageFile);
        MultipartBody.Part image = MultipartBody.Part.createFormData("image", userImageFile.getName(), fileReqBody);

        Call<ProfileItem> call = client.updateUserInfoItem(id,image,email,phone,identification,fullName,name);
        call.enqueue(new Callback<ProfileItem>() {
            @Override
            public void onResponse(Call<ProfileItem> call, Response<ProfileItem> response) {
                if (!response.isSuccessful()) {
                    Log.i("ApiUserInfo badresponse", "Update has error X(");
                    return;
                }
                Repository.TheProfileItem = response.body();
                Log.i("ApiUserInfo GoodRespons", "Update Done ----------> ="+response.body()+response.message());
            }

            @Override
            public void onFailure(Call<ProfileItem> call, Throwable t) {
                Log.i("ApiUserInfo failed", "Update_Response ----------> ="+ Arrays.toString(t.getStackTrace())+"\n\n"
                        +t.getCause()+"\n\n"+t.getMessage()+"\n\n"+t.getLocalizedMessage() );
            }
        });

    }

}
