package login_rejester_splash;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.flyshippment_project.R;
import com.example.flyshippment_project.Repository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RejesterActivity extends AppCompatActivity {
    Button register;
    EditText name, email, pass, conpass,first,second , id;
    String full,userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rejester);


        register = (Button) findViewById(R.id.btreg);
        email = (EditText) findViewById(R.id.etregemail);
        name = (EditText) findViewById(R.id.etregname);
        pass = (EditText) findViewById(R.id.etregpass);
        conpass = (EditText) findViewById(R.id.etregcon);
        first=(EditText)findViewById(R.id.et_reg_first_name);
        second=(EditText)findViewById(R.id.et_reg_second_name);

        id = (EditText) findViewById(R.id.etid);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(email.getText().toString().matches("") ){
                    email.setError("Required");
                }
                else if(name.getText().toString().matches("")){
                    name.setError("Required");
                }
                else if(pass.getText().toString().matches("")){
                    pass.setError("Required");
                }
                else if(conpass.getText().toString().matches("")){
                    conpass.setError("Required");
                }
                else if(id.getText().toString().matches("")){
                    id.setError("Required");
                }
                else if(first.getText().toString().matches("")){
                    id.setError("Required");
                }
                else if(second.getText().toString().matches("")){
                    id.setError("Required");
                }
                else if(!(conpass.getText().toString().equals(pass.getText().toString()))){
                    conpass.setError("Must be same");
                }
                else {
                    full=first.getText().toString()+" "+second.getText().toString();
                    userName="";
                    String str=name.getText().toString().trim();
                    for(int i=0;i<str.length();i++)
                    {
                        if(str.charAt(i)==' ')break;
                        userName+=name.getText().toString().charAt(i);
                    }
                    Repository.startRejestering(getApplicationContext(),
                            email.getText().toString(), userName,
                            pass.getText().toString(),full);
                }



            }
        });

    }
    private void callsignupApi() {

        APIManager.getInstance().getAPI().register(email.getText().toString(), name.getText().toString(),
                pass.getText().toString(),full)
                .enqueue(new Callback<RespnseModel>() {
                    @Override
                    public void onResponse(Call<RespnseModel> call, Response<RespnseModel> response) {


                        if (response.isSuccessful())
                        {    //FIXME  error
                            RespnseModel msg = response.body();
                            String res = msg.getMessage();
                            Toast.makeText(getApplicationContext(),msg.getMessage(), Toast.LENGTH_LONG).show();
                            if(res.equals("Registration Done")){
                            Intent i = new Intent(RejesterActivity.this, LoginActivity.class);
                            startActivity(i);
                            }
                            else {
                                Toast.makeText(getApplicationContext(),msg.getMessage(), Toast.LENGTH_LONG).show();
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
