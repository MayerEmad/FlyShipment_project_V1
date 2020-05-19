package more_classes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
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

        ImageButton backArrowButton = (ImageButton) findViewById(R.id.profile_back_button);
        Button editProfileButton = (Button) findViewById(R.id.profile_edit_button);

        TextView userNameText = (TextView) findViewById(R.id.profile_user_name_textview);
        RatingBar userRate= (RatingBar) findViewById(R.id.profile_ratingbar);
        ImageView userImage= (ImageView) findViewById(R.id.profile_image_imageview);

        CheckBox phoneCheckBox=(CheckBox)findViewById(R.id.profile_phone_checkBox);
        CheckBox passportCheckBox=(CheckBox)findViewById(R.id.profile_passport_checkbox);
        TextView userDeals=(TextView)findViewById(R.id.profile_deals_number_textview);
        TextView userTrips=(TextView)findViewById(R.id.profile_trips_number_textview);
        TextView userShipments=(TextView)findViewById(R.id.profile_shipments_number_textview);

        CheckedTextView emailCheck=(CheckedTextView) findViewById(R.id.profile_email_checktext);
        CheckedTextView phoneCheck=(CheckedTextView) findViewById(R.id.profile_phone_checktext);
        TextView userEmail=(TextView)findViewById(R.id.profile_email_textview);
        TextView userPhone=(TextView)findViewById(R.id.profile_phone_textview);

        backArrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrow_back_function();
            }
        });

        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfilePageActivity.this, EditProfilePageActivity.class);
                startActivity(intent);
            }
        });
    }
}
