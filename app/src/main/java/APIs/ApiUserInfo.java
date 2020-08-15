package APIs;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.flyshippment_project.FileUtil;
import com.example.flyshippment_project.MainActivity;
import com.example.flyshippment_project.Repository;

import java.io.File;
import java.util.Arrays;

import adapters_and_items.ProfileItem;
import more_classes.ProfilePageActivity;
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

    public void GetUserInfoFromApi(Integer id, final Context appCon) {
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
                }
                else
                {
                    Log.i("ApiUserInfo ", "Response ----------> ="+response.message());
                    if(Repository.TheProfileItem!=null){  //Refresh profile page
                        Repository.TheProfileItem = response.body();
                        Intent intent = new Intent(appCon,ProfilePageActivity.class );
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        appCon.startActivity(intent);
                    }
                   else
                       Repository.TheProfileItem = response.body();
                }

            }

            @Override
            public void onFailure(Call<ProfileItem> call, Throwable t) {
                Log.i("ApiUserInfo failed", "Response ----------> ="+t.getStackTrace()+"\n"
                +t.getCause()+"\n"+t.getLocalizedMessage());

            }
        });
    }

    public void UpdateUserInfoApi(final Context mContext, final Context appCon) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://originaliereny.com/shipping/public/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        theApiFunctions client = retrofit.create(theApiFunctions.class);

        final ProfileItem user=Repository.TheProfileItem;
        RequestBody fullName = RequestBody.create(MediaType.parse("text/plain"), user.getUser_name());
        RequestBody phone = RequestBody.create(MediaType.parse("text/plain"), user.getUser_phone());
        RequestBody identification = RequestBody.create(MediaType.parse("text/plain"), user.getUser_passport());
        String filePath= user.getUser_image_url();
        File userImageFile = new File(FileUtil.getPath(Uri.parse(filePath),mContext));
        RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), userImageFile);
        MultipartBody.Part image = MultipartBody.Part.createFormData("image", userImageFile.getName(), fileReqBody);

        Call<ProfileItem> call = client.updateUserInfoItem(user.getUser_id(),image,phone,identification,fullName);
        call.enqueue(new Callback<ProfileItem>() {
            @Override
            public void onResponse(Call<ProfileItem> call, Response<ProfileItem> response) {
                if (!response.isSuccessful()) {
                    Log.i("ApiUserInfo", "Update image has error X("+
                            "\n"+response.message()+ "\n"+response.errorBody()+ "\n"+response.body());
                }
                else {
                    Log.i("ApiUserInfo", "Update image Done ----------> ="+response.body()+response.message());
                    Toast.makeText(appCon, "profile updated :)", Toast.LENGTH_SHORT).show();
                    Repository.getUserInfo(user.getUser_id(),appCon);
                }
            }

            @Override
            public void onFailure(Call<ProfileItem> call, Throwable t) {
                Log.i("ApiUserInfo failed", "Update_Response ----------> ="+ Arrays.toString(t.getStackTrace())+"\n\n"
                        +t.getCause()+"\n\n"+t.getMessage()+"\n\n"+t.getLocalizedMessage() );
            }
        });

    }

    public void UpdateUserInfoApiNoImage(final Context mContext, final Context appCon ) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://originaliereny.com/shipping/public/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        theApiFunctions client = retrofit.create(theApiFunctions.class);

        final ProfileItem user=Repository.TheProfileItem;
        ProfileItem uploadedProfileItem=new ProfileItem(user.getUser_name(),user.getUser_phone(),user.getUser_passport());

        Call<ProfileItem> call = client.updateUserInfoNoImage(user.getUser_id(),uploadedProfileItem);
        call.enqueue(new Callback<ProfileItem>() {
            @Override
            public void onResponse(Call<ProfileItem> call, Response<ProfileItem> response) {
                if (!response.isSuccessful()) {
                    Log.i("ApiUserInfo ", "Update has error X("+
                            "\n"+response.message()+ "\n"+response.errorBody()+ "\n"+response.body());
                }
                else{
                    Log.i("ApiUserInfo ", "Update Done without image ----------> ="+response.body()+response.message());
                    Toast.makeText(appCon, "profile updated :)", Toast.LENGTH_SHORT).show();
                    Repository.getUserInfo(user.getUser_id(),appCon);
                }
            }

            @Override
            public void onFailure(Call<ProfileItem> call, Throwable t) {
                Log.i("ApiUserInfo failed", "Update_Response ----------> ="+ Arrays.toString(t.getStackTrace())+"\n\n"
                        +t.getCause()+"\n\n"+t.getMessage()+"\n\n"+t.getLocalizedMessage() );
            }
        });

    }

}
