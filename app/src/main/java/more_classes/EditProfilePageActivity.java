package more_classes;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.flyshippment_project.FileUtil;
import com.example.flyshippment_project.R;
import com.example.flyshippment_project.Repository;
import java.io.IOException;
import adapters_and_items.ProfileItem;

public class EditProfilePageActivity  extends AppCompatActivity
{
    private static int GALLERY_REQUEST = 1;
    private ProfileItem USERINFO= Repository.TheProfileItem;
    private Uri changedImageUri=Uri.parse(USERINFO.getUser_image_url());  //FIXME
    private boolean imageEdited=false;

    private ImageView userImage;
    private TextView userEmailEditText;
    private TextView userPhoneEditText;
    private TextView userPassportEditText;

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    private void arrow_back_function() {
        Intent intent = new Intent(EditProfilePageActivity.this, ProfilePageActivity.class);
        startActivity(intent);
    }

    private void change_photo() {

        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType("image/*");
        startActivityForResult(i, GALLERY_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode ==RESULT_OK && requestCode==GALLERY_REQUEST)
        {
             changedImageUri = data.getData();
             imageEdited=true;
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), changedImageUri);
                userImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                Toast.makeText(EditProfilePageActivity.this,"Error" , Toast.LENGTH_SHORT).show();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_page);
        verifyStoragePermissions(EditProfilePageActivity.this);

         ImageButton backArrowButton = (ImageButton) findViewById(R.id.edit_profile_back_button);
         Button saveChangesBtn=(Button)findViewById(R.id.edit_profile_save_button);
         userImage= (ImageView) findViewById(R.id.edit_profile_image_image_view);
         TextView userImageText = (TextView) findViewById(R.id.edit_profile_change_text_view);
         final TextView userFirstNameText = (TextView) findViewById(R.id.edit_profile_first_name_edit_text);
         final TextView userLastNameText = (TextView) findViewById(R.id.edit_profile_last_name_edit_text);
         CheckedTextView emailCheckText=(CheckedTextView) findViewById(R.id.edit_profile_email_check_text);
         final CheckedTextView phoneCheckText=(CheckedTextView) findViewById(R.id.edit_profile_phone_check_text);
         final CheckedTextView passportCheckText=(CheckedTextView) findViewById(R.id.edit_profile_passport_check_text);

          userEmailEditText=(TextView)findViewById(R.id.edit_profile_email_edit_text);
          userEmailEditText.setEnabled(false);
          userPhoneEditText=(TextView)findViewById(R.id.edit_profile_phone_edit_text);
          userPassportEditText=(TextView)findViewById(R.id.edit_profile_passport_edit_text);


        //----------------- filling Image ----------------------

            userImage.setForeground(null);
            Glide.with(this).load(USERINFO.getUser_image_url())
                    .placeholder(R.drawable.round_error_black_18dp)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(userImage);



        //-----------------  filling first names ,last name  ---------------------------

        if(USERINFO.getUser_name()!=null){
            String[] namesArr = USERINFO.getUser_name().split(" ");
            userFirstNameText.setText(namesArr[0]);
            if(namesArr.length>1)userLastNameText.setText(namesArr[1]);
        }

        //----------------- filling Image , Email , phone , passport ----------------------

        if(USERINFO.getUser_mail()==null) {
            emailCheckText.setCheckMarkDrawable(R.drawable.round_error_black_18dp);
            userEmailEditText.setHint("required");
        }
        else{
            emailCheckText.setCheckMarkDrawable(R.drawable.round_check_circle_white_18dp);
            userEmailEditText.setText(USERINFO.getUser_mail());
        }

        if(USERINFO.getUser_phone()==null || USERINFO.getUser_phone().equals("required")) {
            phoneCheckText.setCheckMarkDrawable(R.drawable.round_error_black_18dp);
            Toast.makeText(EditProfilePageActivity.this, "black mark", Toast.LENGTH_SHORT).show();
            userPhoneEditText.setHint("required");
            userPhoneEditText.setHintTextColor(getColor(R.color.red));
        }
        else {
            phoneCheckText.setCheckMarkDrawable(R.drawable.round_check_circle_white_18dp);
            userPhoneEditText.setText(USERINFO.getUser_phone());
        }

        if(USERINFO.getUser_passport()==null || USERINFO.getUser_passport().equals("required")){
            passportCheckText.setCheckMarkDrawable(R.drawable.round_error_black_18dp);
            userPassportEditText.setHint("required");
            userPassportEditText.setHintTextColor(getColor(R.color.red));
        }
        else{
            passportCheckText.setCheckMarkDrawable(R.drawable.round_check_circle_white_18dp);
            userPassportEditText.setText(USERINFO.getUser_passport());
        }

        //----------------- button actions ----------------------
        backArrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrow_back_function();
            }
        });

        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               change_photo();
            }
        });

        userImageText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                change_photo();
            }
        });

        saveChangesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String email=userEmailEditText.getText().toString();
                if(!email.isEmpty()) USERINFO.setUser_mail(email);

                String phone=userPhoneEditText.getText().toString();
                if(!phone.isEmpty() && Repository.validPhone(phone)) USERINFO.setUser_phone(phone);
                else USERINFO.setUser_phone("required");

                String passP=userPassportEditText.getText().toString();
                if(!passP.isEmpty() && Repository.validPassport(passP)) USERINFO.setUser_passport(passP);
                else USERINFO.setUser_passport("required");

                USERINFO.setUser_image_url(changedImageUri.toString());

                String name="";
                if(userFirstNameText!=null) name+=userFirstNameText.getText().toString().trim();name+=" ";
                if(userLastNameText!=null)  name+=userLastNameText.getText().toString().trim();
                if(!name.equals(""))USERINFO.setUser_name(name);

                Repository.updateUserInfo(EditProfilePageActivity.this,imageEdited, getApplicationContext());

                arrow_back_function();

            }
        });
    }

}
