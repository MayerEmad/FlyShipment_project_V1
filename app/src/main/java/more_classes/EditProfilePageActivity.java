package more_classes;

import android.content.Intent;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.flyshippment_project.FileUtil;
import com.example.flyshippment_project.R;
import com.example.flyshippment_project.Repository;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.concurrent.ExecutionException;

import Shipments_Trips_classes.CreateShipmentItemActivity;
import adapters_and_items.ProfileItem;

public class EditProfilePageActivity  extends AppCompatActivity
{
    private static int GALLERY_REQUEST = 1;
    private ProfileItem USERINFO= Repository.TheProfileItem;
    private Uri changedImageUri=Uri.parse(USERINFO.getUser_image_url());
    private boolean imageEdited=false;

    private ImageView userImage;
    private TextView userEmailEditText;
    private TextView userPhoneEditText;
    private TextView userPassportEditText;

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

         ImageButton backArrowButton = (ImageButton) findViewById(R.id.edit_profile_back_button);
          userImage= (ImageView) findViewById(R.id.edit_profile_image_image_view);
         TextView userImageText = (TextView) findViewById(R.id.edit_profile_change_text_view);
         final TextView userFirstNameText = (TextView) findViewById(R.id.edit_profile_first_name_edit_text);
         final TextView userLastNameText = (TextView) findViewById(R.id.edit_profile_last_name_edit_text);
         CheckedTextView emailCheckText=(CheckedTextView) findViewById(R.id.edit_profile_email_check_text);
         CheckedTextView phoneCheckText=(CheckedTextView) findViewById(R.id.edit_profile_phone_check_text);
         CheckedTextView passportCheckText=(CheckedTextView) findViewById(R.id.edit_profile_passport_check_text);
          userEmailEditText=(TextView)findViewById(R.id.edit_profile_email_edit_text);
          userPhoneEditText=(TextView)findViewById(R.id.edit_profile_phone_edit_text);
          userPassportEditText=(TextView)findViewById(R.id.edit_profile_passport_edit_text);


        //----------------- filling Image ----------------------

            userImage.setForeground(null);
            Glide.with(this).load(USERINFO.getUser_image_url())
                    .placeholder(R.drawable.round_error_black_18dp)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(userImage);

            /* try {
                 File file=Glide.with(this).asFile().load(USERINFO.getUser_image_url()).submit().get();
                 Log.i("ApiUserInfo Edit", "image path:---------> "+file.getPath());
                 changedImageUri= Uri.parse(file.getPath());
             } catch (ExecutionException e) {
                 e.printStackTrace();
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }*/


        //-----------------  filling first names ,last name  ---------------------------

        if(USERINFO.getUser_name()!=null){
            String[] namesArr = USERINFO.getUser_name().split(" ");
            userFirstNameText.setText(namesArr[0]);
            if(namesArr.length>1)userLastNameText.setText(namesArr[1]);
        }

        //----------------- filling Image , Email , phone , passport ----------------------

        if(USERINFO.getUser_phone()==null) {
            phoneCheckText.setCheckMarkDrawable(R.drawable.round_error_black_18dp);
            userPhoneEditText.setHint("need to fill");
        }
        else {
            phoneCheckText.setCheckMarkDrawable(R.drawable.round_check_circle_white_18dp);
            userPhoneEditText.setText(USERINFO.getUser_phone());
        }

        if(USERINFO.getUser_mail()==null) {
            emailCheckText.setCheckMarkDrawable(R.drawable.round_error_black_18dp);
            userEmailEditText.setHint("need to fill");
        }
        else{
            emailCheckText.setCheckMarkDrawable(R.drawable.round_check_circle_white_18dp);
            userEmailEditText.setText(USERINFO.getUser_mail());
        }

        if(USERINFO.getUser_passport()==null){
            passportCheckText.setCheckMarkDrawable(R.drawable.round_error_black_18dp);
            userPassportEditText.setHint("need to fill");
        }
        else{
            passportCheckText.setCheckMarkDrawable(R.drawable.round_check_circle_white_18dp);
            userPassportEditText.setText(USERINFO.getUser_passport());
        }

        Button saveChangesBtn=(Button)findViewById(R.id.edit_profile_save_button);

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

                  USERINFO.setUser_image_url(changedImageUri.toString());
              if(!userEmailEditText.getText().toString().isEmpty())
                  USERINFO.setUser_mail(userEmailEditText.getText().toString());
              if(!userPhoneEditText.getText().toString().isEmpty())
                  USERINFO.setUser_phone(userPhoneEditText.getText().toString());
              if(!userPassportEditText.getText().toString().isEmpty())
                  USERINFO.setUser_passport(userPassportEditText.getText().toString());
              String name="";
              if(userFirstNameText!=null)
                  name+=userFirstNameText.getText().toString().trim();name+=" ";
              if(userLastNameText!=null)
                  name+=userLastNameText.getText().toString().trim();
              if(!name.equals(""))USERINFO.setUser_name(name);
              Repository.updateUserInfo(EditProfilePageActivity.this,imageEdited);
            }
        });
    }

}
