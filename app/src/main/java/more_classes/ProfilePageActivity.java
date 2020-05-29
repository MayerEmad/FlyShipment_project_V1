package more_classes;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.flyshippment_project.MainActivity;
import com.example.flyshippment_project.R;
import com.example.flyshippment_project.Repository;

import adapters_and_items.ProfileItem;


public class ProfilePageActivity extends AppCompatActivity {

    private void arrow_back_function() {
        Intent intent = new Intent(ProfilePageActivity.this, MainActivity.class);
        intent .putExtra("openMoreNav",true);
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        ImageButton backArrowButton = (ImageButton) findViewById(R.id.profile_back_button);
        Button editProfileButton = (Button) findViewById(R.id.profile_edit_button);

        TextView userNameText = (TextView) findViewById(R.id.profile_user_name_textview);
        RatingBar userRate= (RatingBar) findViewById(R.id.profile_ratingbar);
        ImageView userImage= (ImageView) findViewById(R.id.profile_image_imageview);

        TextView userDeals=(TextView)findViewById(R.id.profile_deals_number_textview);
        TextView userTrips=(TextView)findViewById(R.id.profile_trips_number_textview);
        TextView userShipments=(TextView)findViewById(R.id.profile_shipments_number_textview);

        CheckedTextView emailCheckText=(CheckedTextView) findViewById(R.id.profile_email_checktext);
        CheckedTextView phoneCheckText=(CheckedTextView) findViewById(R.id.profile_phone_checktext);
        CheckedTextView passportCheckText=(CheckedTextView) findViewById(R.id.profile_passport_checktext);
        TextView userEmailTextV=(TextView)findViewById(R.id.profile_email_textview);
        TextView userPhoneTextV=(TextView)findViewById(R.id.profile_phone_textview);
        TextView userPassportTextV=(TextView)findViewById(R.id.profile_passport_textview);

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

        // Binding user Data
        ProfileItem user=Repository.TheProfileItem;

        userNameText.setText(user.getUser_name());
        userRate.setRating((float) user.getUser_rate());

        if(user.getUser_image_url()==null){
            userImage.setForeground(null);
            Glide.with(this).load(user.getUser_image_url()).into(userImage);
        }else{
            Glide.with(this).load(R.drawable.round_error_black_18dp).into(userImage);
        }

        userDeals.setText(String.valueOf(user.getUser_deals()));
        userTrips.setText(String.valueOf(user.getUser_trips()));
        userShipments.setText(String.valueOf(user.getUser_shipments()));

        if(user.getUser_phone()!=null) {
            phoneCheckText.setCheckMarkDrawable(R.drawable.round_error_black_18dp);
            userPhoneTextV.setText("need to fill");
        }
        else {
            phoneCheckText.setCheckMarkDrawable(R.drawable.round_check_circle_white_18dp);
            userPhoneTextV.setText(user.getUser_phone());
        }
        if(user.getUser_mail()==null) {
            emailCheckText.setCheckMarkDrawable(R.drawable.round_error_black_18dp);
            userEmailTextV.setText("need to fill");
        }
        else{
            emailCheckText.setCheckMarkDrawable(R.drawable.round_check_circle_white_18dp);
            userEmailTextV.setText(user.getUser_mail());
        }
        if(user.getUser_passport()==null){
            passportCheckText.setCheckMarkDrawable(R.drawable.round_error_black_18dp);
            userPassportTextV.setText("need to fill");
        }
        else{
            passportCheckText.setCheckMarkDrawable(R.drawable.round_check_circle_white_18dp);
            userPassportTextV.setText(user.getUser_passport());
        }
    }
}
