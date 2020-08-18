package login_rejester_splash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.flyshippment_project.MainActivity;
import com.example.flyshippment_project.R;

import adapters_and_items.ProfileItem;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    public SharedPreferences prefs , PREF_USER_ID;
    Button login;
    EditText email, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (Button) findViewById(R.id.btlog);
        email = (EditText) findViewById(R.id.etlogemail);
        pass = (EditText) findViewById(R.id.etlogpass);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(email.getText().toString().matches("") ){
                    email.setError("Required");
                }
                else if(pass.getText().toString().matches("")){
                    pass.setError("Required");
                }

                else {
                    calloginApi();
                }


            }

    });
    }

        private void calloginApi() {

        APIManager.getInstance().getAPI().login(email.getText().toString(),pass.getText().toString()).enqueue(new Callback<ProfileItem>() {
            @Override
            public void onResponse(Call<ProfileItem> call, Response<ProfileItem> response) {
                String msg=response.message();
                if (response.isSuccessful()&&response.body().getUser_id()!=0)
                {
                  /*  prefs = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor et = prefs.edit();
                    et.putBoolean("isLogin", true);
                    et.commit();*/

                    PREF_USER_ID = getSharedPreferences("userid" , MODE_PRIVATE);
                    SharedPreferences.Editor idet = PREF_USER_ID.edit();
                    idet.putInt("userid", response.body().getUser_id());
                    idet.commit();

                    Toast.makeText(getApplicationContext(), "welcome"+response.body().getUser_nick_name(),Toast.LENGTH_LONG).show();
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(i);
                } else {
                        Toast.makeText(getApplicationContext(), "Wrong Password or Email",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ProfileItem> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Failed",
                                Toast.LENGTH_LONG).show();
            }
        });
           /* APIManager.getInstance().getAPI().login(email.getText().toString(), pass.getText().toString())
                    .enqueue(new Callback<LoginResponsemodel>() {
                        @Override
                        public void onResponse(Call<LoginResponsemodel> call, Response<LoginResponsemodel> response) {

                            if (response.isSuccessful()) {
                                LoginResponsemodel msg = response.body();
                                if(msg.getMessage().equals("Login Done")){

                                prefs =getSharedPreferences("checkbox", MODE_PRIVATE);
                                SharedPreferences.Editor et = prefs.edit();
                                et.putBoolean("isLogin", true);
                                et.commit();

                                PREF_USER_ID = getSharedPreferences("userid" , MODE_PRIVATE);
                                SharedPreferences.Editor idet = PREF_USER_ID.edit();
                                idet.putInt("userid", msg.getData().getUserInfoId());
                                idet.commit();

                                Log.i("id", "onResponse: "+  msg.getData().getUserInfoId());
                                Toast.makeText(getApplicationContext(), msg.getMessage(),
                                        Toast.LENGTH_LONG).show();
                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(i);
                            }
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginResponsemodel> call, Throwable t) {

                            Toast.makeText(getApplicationContext(), "Failed",
                                    Toast.LENGTH_LONG).show();

                        }
                    });*/
        }
}
