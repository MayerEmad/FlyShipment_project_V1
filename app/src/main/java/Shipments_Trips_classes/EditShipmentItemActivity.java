package Shipments_Trips_classes;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.flyshippment_project.DatePickerFragmentForA;
import com.example.flyshippment_project.MainActivity;
import com.example.flyshippment_project.MyViewModel;
import com.example.flyshippment_project.R;
import com.example.flyshippment_project.Repository;

import java.io.IOException;
import java.util.ArrayList;

import adapters_and_items.ProfileItem;
import adapters_and_items.ShipmentItem;

public class EditShipmentItemActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    private String fromCountry = "";
    private String toCountry = "";
    private String lastDate = "";
    private String itemName = "";
    private String itemPrice = "";
    private String itemWeight = "";
    private String itemNumber = "";
    private String itemUrl = "";
    private String itemImageUrl = null;
    private boolean imageEdited=false;

    private EditText fromText;
    private EditText toText;
    private EditText dateText;
    private EditText nameText;
    private EditText urlText;
    private EditText numberText;
    private EditText priceText;
    private EditText weightText;
    private TextView totalPriceText;
    private TextView totalWeightText;
    private ImageView theImageView;

    private ShipmentItem ITEM;
    private static int GALLERY_REQUEST = 1;

    private void arrow_back_function() {
        Intent intent = new Intent(EditShipmentItemActivity.this, MainActivity.class);
        intent .putExtra("openShipmentNav",true);
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        startActivity(intent);
    }
    private void update(double itemsNumber) {
        numberText.setText(String.valueOf((int) itemsNumber));
        String priceStr = priceText.getText().toString();
        String weightStr = weightText.getText().toString();

        if (!priceStr.equals("")) {
            double itemPrice = Double.parseDouble(priceStr);
            totalPriceText.setText("Total price= " + String.valueOf(itemPrice * itemsNumber) + "$");
        }
        if (!weightStr.equals("")) {
            double itemWeight = Double.parseDouble(weightStr);
            totalWeightText.setText("Total weight= " + String.valueOf(itemWeight * itemsNumber) + "gm");
        }
    }

    private boolean noEmptyField() {
        if (fromCountry.isEmpty() || toCountry.isEmpty() || lastDate.isEmpty() || itemName.isEmpty() ||
                itemPrice.isEmpty() || itemWeight.isEmpty() || itemNumber.equals("0") ||
                itemUrl.isEmpty() || itemImageUrl ==null) return false;
        return true;
    }

    void setting(ProfileItem USERINFO) {
        ITEM.setCountry_from(fromCountry);
        ITEM.setCountry_to(toCountry);
        ITEM.setLast_date(lastDate);
        ITEM.setProduct_name(itemName);
        ITEM.setProduct_url(itemUrl);
        ITEM.setItemPrice(Double.parseDouble(itemPrice));
        ITEM.setItemWeight(Double.parseDouble(itemWeight));
        ITEM.setItems_number(Integer.parseInt(itemNumber));
        ITEM.setProduct_image(itemImageUrl);
        ITEM.setProfile_name(USERINFO.getUser_name());
        ITEM.setProfile_image(USERINFO.getUser_image_url());
        ITEM.setUser_rate(USERINFO.getUser_rate());
    }

    void getting() {
        fromCountry = fromText.getText().toString();
        toCountry = toText.getText().toString();
        lastDate = dateText.getText().toString();
        itemName = nameText.getText().toString();
        itemUrl = urlText.getText().toString();
        itemPrice = priceText.getText().toString();
        itemWeight = weightText.getText().toString();
        itemNumber = numberText.getText().toString();
        itemImageUrl=ITEM.getProduct_image();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_shipment);

        final ProfileItem USERINFO= Repository.TheProfileItem ;
        Bundle extras = getIntent().getExtras();
        int pos=extras.getInt("ShipmentItemPosition");
        ITEM=MyViewModel.getUserShipmentLiveData().getValue().get(pos);

        fromText = (EditText) findViewById(R.id.edit_shipment_from);
         fromText.setText(ITEM.getCountry_from());
        toText = (EditText) findViewById(R.id.edit_shipment_to);
         toText.setText(ITEM.getCountry_to());
        dateText = (EditText) findViewById(R.id.edit_shipment_date);
         dateText.setText(ITEM.getLast_date());
         dateText.setFocusable(false);
        nameText = (EditText) findViewById(R.id.edit_shipment_item_name);
         nameText.setText(ITEM.getProduct_name());
        urlText = (EditText) findViewById(R.id.edit_shipment_item_urll);
         urlText.setText(ITEM.getProduct_url());
        theImageView=(ImageView)findViewById(R.id.edit_shipment_the_image);
        theImageView.setForeground(null);
         Glide.with(this).load(ITEM.getProduct_image())
                 .skipMemoryCache(true)
                 .diskCacheStrategy(DiskCacheStrategy.NONE)
                 .into(theImageView);
        numberText = (EditText) findViewById(R.id.edit_shipment_item_number);
         numberText.setText(String.valueOf(((int) ITEM.getItemsNumber())));
        priceText = (EditText) findViewById(R.id.edit_shipment_item_price);
         priceText.setText(String.valueOf(ITEM.getItemPrice()));
        weightText = (EditText) findViewById(R.id.edit_shipment_item_weight);
         weightText.setText(String.valueOf(ITEM.getItemWeight()));
       itemImageUrl=ITEM.getProduct_image();

        totalPriceText = (TextView) findViewById(R.id.edit_shipment_item_total_price);
        totalWeightText = (TextView) findViewById(R.id.edit_shipment_item_total_weight);
        update(ITEM.getItemsNumber());

        Button plusBtn = (Button) findViewById(R.id.edit_shipment_plus_item);
        Button minusBtn = (Button) findViewById(R.id.edit_shipment_minus_item);
        Button takeImageBtn = (Button) findViewById(R.id.edit_shipment_get_image_button);
        Button editShipmentBtn = (Button) findViewById(R.id.edit_shipment_edit_button);
        Button backArrowButton = (Button) findViewById(R.id.edit_shipment_back_button);
        Button deleteItemButton = (Button) findViewById(R.id.edit_shipment_delete_button);
        Button uploadItemButton = (Button) findViewById(R.id.edit_shipment_upload_button);

        if(ITEM.getIsEditable()==0)
        {
            editShipmentBtn.setEnabled(false);
            uploadItemButton.setEnabled(false);
            deleteItemButton.setEnabled(false);
        }

        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker= new DatePickerFragmentForA();
                datePicker.show(getSupportFragmentManager(),"date picker");
            }
        });
        deleteItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Repository.deleteShipmentItem(ITEM.getShipment_id());
                Toast.makeText(EditShipmentItemActivity.this,"Shipment Deleted" , Toast.LENGTH_SHORT).show();
                arrow_back_function();
            }
        });

        backArrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrow_back_function();
            }
        });

        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numberStr = numberText.getText().toString();
                double number = Double.parseDouble(numberStr);
                number++;
                update(number);
            }
        });

        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numberStr = numberText.getText().toString();
                double number = Double.parseDouble(numberStr);
                number--;
                if (number > -1) update(number);
            }
        });
        takeImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType("image/*");
                startActivityForResult(i, GALLERY_REQUEST);
            }
        });
        editShipmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                getting();
                if (noEmptyField())
                {
                    setting(USERINFO);
                    Repository.updateShipmentItem(ITEM,EditShipmentItemActivity.this,imageEdited,getApplicationContext());
                    Toast.makeText(EditShipmentItemActivity.this, "Shipment Edited :)", Toast.LENGTH_SHORT).show();
                    // Go back ShipmentNavFragment
                    arrow_back_function();
                }
                else {
                    Toast.makeText(EditShipmentItemActivity.this, "Empty Field", Toast.LENGTH_SHORT).show();
                }
            }
        });
        uploadItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                getting();

                if(!Repository.completeProfileData())
                    Toast.makeText(EditShipmentItemActivity.this, "need to complete profile data", Toast.LENGTH_SHORT).show();
                else if (noEmptyField())
                {
                    setting(USERINFO);
                    ITEM.setIsEditable(0);
                    Repository.updateShipmentItem(ITEM,EditShipmentItemActivity.this,imageEdited,getApplicationContext());
                   // Toast.makeText(EditShipmentItemActivity.this, "Shipment Edited :)", Toast.LENGTH_SHORT).show();
                    // Go back ShipmentNavFragment
                    arrow_back_function();
                }
                else {
                    Toast.makeText(EditShipmentItemActivity.this, "Empty Field", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //getting Image from camera
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode ==RESULT_OK && requestCode==GALLERY_REQUEST)
        {
             Uri selectedImage = data.getData();
            // Log.i("onActivityResult", "------------------->\n "+itemImageUrl);
             itemImageUrl =selectedImage.toString();
             ITEM.setProduct_image(itemImageUrl);
             imageEdited=true;
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                theImageView = (ImageView) findViewById(R.id.edit_shipment_the_image);
                theImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                Toast.makeText(EditShipmentItemActivity.this,"Error" , Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        month++;
        String CurrentDateString=year+"-"+month+"-"+dayOfMonth;
        dateText.setText(CurrentDateString);
    }
}


