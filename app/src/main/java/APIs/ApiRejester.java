package APIs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import login_rejester_splash.LoginActivity;
import login_rejester_splash.RejesterActivity;
import login_rejester_splash.RespnseModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiRejester extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }


    public void StartRejestering(final Context appCon, String a, String b, String c, String d)
    {
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("https://originaliereny.com/shipping/public/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        theApiFunctions client=retrofit.create(theApiFunctions.class);
        Call<RespnseModel> call = client.register(a,b,c,d);

        call.enqueue(new Callback<RespnseModel>() {
            @Override
            public void onResponse(Call<RespnseModel> call, Response<RespnseModel> response) {
                String msg = response.message();
                if (response.isSuccessful()) {
                    Log.i("ApiRejester", "Rejester Done " + msg);
                    Toast.makeText(appCon, "Register Done", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(appCon, LoginActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    appCon.startActivity(i);
                }
                else {
                        Toast.makeText(appCon,"Register has error", Toast.LENGTH_LONG).show();
                        Log.i("ApiRejester", "Rejester error "+msg);
                    }

            }

            @Override
            public void onFailure(Call<RespnseModel> call, Throwable t) {
                Log.i("ApiRejester", "Rejester failed "+t.getLocalizedMessage()+"\n"+t.getCause()+"\n"
                +t.getMessage()+"\n"+t.getStackTrace());
            }
        });
    }
}
