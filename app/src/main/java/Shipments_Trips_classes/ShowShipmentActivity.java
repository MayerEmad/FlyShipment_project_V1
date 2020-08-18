package Shipments_Trips_classes;
import android.annotation.SuppressLint;
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
import adapters_and_items.ShipmentItem;

@SuppressLint("Registered")
public class ShowShipmentActivity extends AppCompatActivity{

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
        Intent intent = new Intent(ShowShipmentActivity.this, MainActivity.class);
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


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_shipment);

        Bundle extras = getIntent().getExtras();
        int pos=extras.getInt("ShipmentItemPosition");
        int getFromUser=extras.getInt("user");
        if(getFromUser==1) ITEM=MyViewModel.getUserShipmentLiveData().getValue().get(pos);
        else ITEM=MyViewModel.getShipmentLiveData().getValue().get(pos);

        fromText = (EditText) findViewById(R.id.show_shipment_from);
        fromText.setText(ITEM.getCountry_from());
        fromText.setEnabled(false);
        toText = (EditText) findViewById(R.id.show_shipment_to);
        toText.setText(ITEM.getCountry_to());
        toText.setEnabled(false);
        dateText = (EditText) findViewById(R.id.show_shipment_date);
        dateText.setText(ITEM.getLast_date());
        dateText.setEnabled(false);
        nameText = (EditText) findViewById(R.id.show_shipment_item_name);
        nameText.setText(ITEM.getProduct_name());
        nameText.setEnabled(false);
        urlText = (EditText) findViewById(R.id.show_shipment_item_urll);
        urlText.setText(ITEM.getProduct_url());
        urlText.setEnabled(false);
        theImageView=(ImageView)findViewById(R.id.show_shipment_the_image);
        theImageView.setForeground(null);
        Glide.with(this).load(ITEM.getProduct_image())
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(theImageView);
        numberText = (EditText) findViewById(R.id.show_shipment_item_number);
        numberText.setText(String.valueOf(((int) ITEM.getItemsNumber())));
        numberText.setEnabled(false);
        priceText = (EditText) findViewById(R.id.show_shipment_item_price);
        priceText.setText(String.valueOf(ITEM.getItemPrice()));
        priceText.setEnabled(false);
        weightText = (EditText) findViewById(R.id.show_shipment_item_weight);
        weightText.setText(String.valueOf(ITEM.getItemWeight()));
        weightText.setEnabled(false);

        totalPriceText = (TextView) findViewById(R.id.show_shipment_item_total_price);
        totalWeightText = (TextView) findViewById(R.id.show_shipment_item_total_weight);
        update(ITEM.getItemsNumber());

        Button backArrowButton=(Button)findViewById(R.id.show_shipment_back_button);
        backArrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrow_back_function();
            }
        });
    }

}


