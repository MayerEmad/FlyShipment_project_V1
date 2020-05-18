package more_classes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.flyshippment_project.MainActivity;
import com.example.flyshippment_project.R;


public class ProfilePageActivity extends AppCompatActivity {

    private void arrow_back_function() {
        Intent intent = new Intent(ProfilePageActivity.this, MainActivity.class);
        intent .putExtra("openMoreNav",true);
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        TextView userNameText = (TextView) findViewById(R.id.profile_user_name);
        ImageButton backArrowButton = (ImageButton) findViewById(R.id.profile_back_button);
        Button editProfileButton = (Button) findViewById(R.id.profile_edit_button);

        backArrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrow_back_function();
                //Log.i("ProfilePageActivity", "onClick: ");
            }
        });

    }
}
