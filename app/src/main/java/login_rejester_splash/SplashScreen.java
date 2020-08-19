package login_rejester_splash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.flyshippment_project.MainActivity;
import com.example.flyshippment_project.R;

public class SplashScreen extends AppCompatActivity {

    SharedPreferences sp;
    private final int SPLASH_DISPLAY_LENGTH = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        int secondsDelayed = 1;
        new Handler().postDelayed(new Runnable() {
            public void run() {
               sp = getSharedPreferences("checkbox", 0);
                boolean cb1 = sp.getBoolean("isLogin", false);
                if (!cb1){
                   startActivity(new Intent(SplashScreen.this, MainActivity.class));
                }
                else {
                   startActivity(new Intent(SplashScreen.this, WelcomeActivity.class));
                }
                finish();
            }
        }, secondsDelayed * 3000);

    }

}

