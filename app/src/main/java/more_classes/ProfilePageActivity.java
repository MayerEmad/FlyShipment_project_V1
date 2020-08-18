package more_classes;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
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

        TextView userNameText = (TextView) findViewById(R.id.profile_user_name_text_view);
        RatingBar userRate= (RatingBar) findViewById(R.id.profile_ratingbar);
        ImageView userImage= (ImageView) findViewById(R.id.profile_image_image_view);

        TextView userDeals=(TextView)findViewById(R.id.profile_deals_number_textview);
        TextView userTrips=(TextView)findViewById(R.id.profile_trips_number_textview);
        TextView userShipments=(TextView)findViewById(R.id.profile_shipments_number_textview);

        CheckedTextView emailCheckText=(CheckedTextView) findViewById(R.id.profile_email_check_text);
        CheckedTextView phoneCheckText=(CheckedTextView) findViewById(R.id.profile_phone_check_text);
        CheckedTextView passportCheckText=(CheckedTextView) findViewById(R.id.profile_passport_check_text);
        TextView userEmailTextV=(TextView)findViewById(R.id.profile_email_edit_text);
        TextView userPhoneTextV=(TextView)findViewById(R.id.profile_phone_edit_text);
        TextView userPassportTextV=(TextView)findViewById(R.id.profile_passport_edit_text);

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

            userImage.setForeground(null);
            Glide.with(this).load(user.getUser_image_url())
                    .placeholder(R.drawable.round_error_black_18dp)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(userImage);


        userDeals.setText(String.valueOf(user.getUser_deals()));
        userTrips.setText(String.valueOf(user.getUser_trips()));
        userShipments.setText(String.valueOf(user.getUser_shipments()));

        if(user.getUser_mail()==null) {
            emailCheckText.setCheckMarkDrawable(R.drawable.round_error_black_18dp);
            userEmailTextV.setText("required");
        }
        else{
            emailCheckText.setCheckMarkDrawable(R.drawable.round_check_circle_white_18dp);
            userEmailTextV.setText(user.getUser_mail());
        }

        if(user.getUser_phone()==null ||  user.getUser_phone().equals("required")) {
            phoneCheckText.setCheckMarkDrawable(R.drawable.round_error_black_18dp);
            userPhoneTextV.setHint("required");
            userPhoneTextV.setHintTextColor(getColor(R.color.red));
        }
        else {
            phoneCheckText.setCheckMarkDrawable(R.drawable.round_check_circle_white_18dp);
            userPhoneTextV.setText(user.getUser_phone());
        }
        if(user.getUser_passport()==null ||  user.getUser_passport().equals("required")){
            passportCheckText.setCheckMarkDrawable(R.drawable.round_error_black_18dp);
            userPassportTextV.setHint("required");
            userPhoneTextV.setHintTextColor(getColor(R.color.red));
        }
        else{
            passportCheckText.setCheckMarkDrawable(R.drawable.round_check_circle_white_18dp);
            userPassportTextV.setText(user.getUser_passport());
        }
    }
}
