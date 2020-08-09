package Shipments_Trips_classes;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.flyshippment_project.DatePickerFragmentForA;
import com.example.flyshippment_project.MainActivity;
import com.example.flyshippment_project.MyViewModel;
import com.example.flyshippment_project.R;
import com.example.flyshippment_project.Repository;

import java.util.ArrayList;

import adapters_and_items.ProfileItem;
import adapters_and_items.TripItem;

public class CreateTripItemActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    private String fromCountry = "";
    private String toCountry = "";
    private String lastDate = "";
    private String freeWeight = "";

    private EditText fromText;
    private EditText toText;
    private EditText dateText;
    private EditText weightText;
    private Button addTripBtn;

    private void arrow_back_function() {
        Intent intent = new Intent(CreateTripItemActivity.this, MainActivity.class);
        intent .putExtra("openTripNav",true);
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        startActivity(intent);
    }
    private boolean noEmptyField() {
        if (fromCountry.isEmpty() || toCountry.isEmpty() || lastDate.isEmpty() || freeWeight.isEmpty()) return false;
        return true;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trip);

        final ProfileItem USERINFO= Repository.TheProfileItem ;

        fromText = (EditText) findViewById(R.id.create_trip_from);
        toText = (EditText) findViewById(R.id.create_trip_to);
        weightText = (EditText) findViewById(R.id.create_trip_available_weight);
        dateText = (EditText) findViewById(R.id.create_trip_last_date);
        dateText.setFocusable(false);

        addTripBtn = (Button) findViewById(R.id.create_trip_add_button);
        Button backArrowButton = (Button) findViewById(R.id.create_trip_back_button);

        dateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker= new DatePickerFragmentForA();
                datePicker.show(getSupportFragmentManager(),"date picker");
            }
        });
        backArrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrow_back_function();
            }
        });
        addTripBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromCountry = fromText.getText().toString();
                toCountry = toText.getText().toString();
                lastDate = dateText.getText().toString();
                freeWeight=weightText.getText().toString();

                if (noEmptyField())
                {

                    TripItem item =new TripItem(
                            -1, fromCountry, toCountry, lastDate, Double.parseDouble(freeWeight),USERINFO.getUser_image_url(),
                            USERINFO.getUser_name(), USERINFO.getUser_rate());

                    //uploading...
                    Repository.uploadTripItem(item);

                    ArrayList<TripItem> list= Repository.getUserTripsFromApi();
                    if(list==null) {
                        MyViewModel.setUserTripLiveData(new ArrayList<TripItem>());
                        list=Repository.getUserTripsFromApi();
                    }
                    list.add(item);

                    Toast.makeText(CreateTripItemActivity.this, "trip saved :)", Toast.LENGTH_SHORT).show();
                    // Go back TripNavFragment
                    arrow_back_function();
                } else {
                    Toast.makeText(CreateTripItemActivity.this, "Empty Field", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        month++;
        String CurrentDateString=year+"-"+month+"-"+dayOfMonth;
        dateText.setText(CurrentDateString);
    }
}
