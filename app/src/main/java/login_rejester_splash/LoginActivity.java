package login_rejester_splash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.flyshippment_project.MainActivity;
import com.example.flyshippment_project.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    public SharedPreferences prefs;
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
            APIManager.getInstance().getAPI().login(email.getText().toString(), pass.getText().toString())
                    .enqueue(new Callback<RespnseModel>() {
                        @Override
                        public void onResponse(Call<RespnseModel> call, Response<RespnseModel> response) {

                            if (response.isSuccessful()) {
                                RespnseModel msg = response.body();
                                if(msg.getmessage().equals("Login Done")){

                                prefs =getSharedPreferences("checkbox", MODE_PRIVATE);
                                SharedPreferences.Editor et = prefs.edit();
                                et.putBoolean("isLogin", true);
                                et.commit();

                                Toast.makeText(getApplicationContext(), msg.message,
                                        Toast.LENGTH_LONG).show();
                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(i);
                            }
                            }
                        }

                        @Override
                        public void onFailure(Call<RespnseModel> call, Throwable t) {

                            Toast.makeText(getApplicationContext(), "Failed",
                                    Toast.LENGTH_LONG).show();

                        }
                    });
        }
}
