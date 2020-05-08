package com.example.flyshippment_project;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;

import adapters_and_items.Repository;
import adapters_and_items.SearchViewModel;
import adapters_and_items.ShipmentItem;

public class CreateShipmentItemActivity extends AppCompatActivity {
    private String fromCountry = "";
    private String toCountry = "";
    private String lastDate = "";
    private String itemName = "";
    private String itemPrice = "";
    private String itemWeight = "";
    private String itemNumber = "";
    private String itemUrl = "";
    private Bitmap itemImage = null;

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
    private Button plusBtn;
    private Button minusBtn;
    private Button takeImageBtn;
    private Button addShipmentBtn;
    private ImageView theImageView;

    private static int GALLERY_REQUEST = 1;

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
                itemUrl.isEmpty() || itemImage==null) return false;
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_shipment);

        fromText = (EditText) findViewById(R.id.create_shipment_from);
        toText = (EditText) findViewById(R.id.create_shipment_to);
        dateText = (EditText) findViewById(R.id.create_shipment_date);
        nameText = (EditText) findViewById(R.id.create_shipment_item_name);
        urlText = (EditText) findViewById(R.id.create_shipment_item_urll);
        plusBtn = (Button) findViewById(R.id.create_shipment_plus_item);
        minusBtn = (Button) findViewById(R.id.create_shipment_minus_item);
        takeImageBtn = (Button) findViewById(R.id.create_shipment_get_image_button);
        addShipmentBtn = (Button) findViewById(R.id.create_shipment_add_button);
        //theImageView=(ImageView)findViewById(R.id.create_shipment_the_image);
        numberText = (EditText) findViewById(R.id.create_shipment_item_number);
        priceText = (EditText) findViewById(R.id.create_shipment_item_price);
        weightText = (EditText) findViewById(R.id.create_shipment_item_weight);
        totalPriceText = (TextView) findViewById(R.id.create_shipment_item_total_price);
        totalWeightText = (TextView) findViewById(R.id.create_shipment_item_total_weight);

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
        addShipmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromCountry = fromText.getText().toString();
                toCountry = toText.getText().toString();
                lastDate = dateText.getText().toString();
                itemName = nameText.getText().toString();
                itemUrl = urlText.getText().toString();
                itemPrice = priceText.getText().toString();
                itemWeight = weightText.getText().toString();
                itemNumber = numberText.getText().toString();
                //itemImage---->  get its value at onActivityResult
                if (noEmptyField())
                {
                    //FIXME add -->username,rate,profile_bitmap
                    ShipmentItem item =new ShipmentItem(
                            "",Double.parseDouble(itemWeight), Double.parseDouble(itemNumber),
                            itemName, fromCountry, toCountry, lastDate, Double.parseDouble(itemPrice),
                            "", "UserName", 5,itemImage,null);
                    ArrayList<ShipmentItem>list=Repository.getUserShipmentsFromApi();
                    if(list==null) {
                        SearchViewModel.setUserShipmentLiveData(new ArrayList<ShipmentItem>());
                        list=Repository.getUserShipmentsFromApi();
                    }
                    list.add(item);

                    Toast.makeText(CreateShipmentItemActivity.this, "Shipment saved :)", Toast.LENGTH_SHORT).show();
                    // Go back ShipmentNavFragment
                    Intent intent = new Intent(CreateShipmentItemActivity.this,MainActivity.class);
                    intent .putExtra("openShipmentNav",true);
                    overridePendingTransition(0, 0);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    finish();
                    startActivity(intent);
                } else {
                    Toast.makeText(CreateShipmentItemActivity.this, "Empty Field", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //TODO----> get image from camera
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode ==RESULT_OK && requestCode==GALLERY_REQUEST)
            {
                Uri selectedImage = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                    theImageView = (ImageView) findViewById(R.id.create_shipment_the_image);
                    theImageView.setImageBitmap(bitmap);
                    itemImage=bitmap;
                } catch (IOException e) {
                    Toast.makeText(CreateShipmentItemActivity.this,"Error" , Toast.LENGTH_SHORT).show();
                }
            }
    }
}
