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

import adapters_and_items.TripItem;

public class EditTripItemActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private String fromCountry = "";
    private String toCountry = "";
    private String lastDate = "";
    private String freeWeight = "";

    private EditText fromText;
    private EditText toText;
    private EditText dateText;
    private EditText weightText;

    private void arrow_back_function() {
        Intent intent = new Intent(EditTripItemActivity.this, MainActivity.class);
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
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_trip);

        Bundle extras = getIntent().getExtras();
        int pos=extras.getInt("TripItemPosition");
        final TripItem ITEM=MyViewModel.getUserTripLiveData().getValue().get(pos);

        fromText = (EditText) findViewById(R.id.edit_trip_from);
         fromText.setText(ITEM.getCountry_from());
        toText = (EditText) findViewById(R.id.edit_trip_to);
         toText.setText(ITEM.getCountry_to());
        weightText = (EditText) findViewById(R.id.edit_trip_available_weight);
         weightText.setText(String.valueOf(ITEM.getAvailable_weight()));
        dateText = (EditText) findViewById(R.id.edit_trip_last_date);
         dateText.setText(ITEM.getMeeting_date());
         dateText.setFocusable(false);

        Button editTripBtn = (Button) findViewById(R.id.edit_trip_add_button);
        Button uploadTripBtn = (Button) findViewById(R.id.edit_trip_uploade_button);
        Button backArrowButton = (Button) findViewById(R.id.edit_trip_back_button);
        Button deleteItemButton = (Button) findViewById(R.id.edit_trip_delete_button);

        if(ITEM.getIsEditable()==0){
            uploadTripBtn.setEnabled(false);
            editTripBtn.setEnabled(false);
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
                Repository.deleteTripItem(ITEM.getTrip_id());
            }
        });

        backArrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrow_back_function();
            }
        });

        editTripBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fromCountry = fromText.getText().toString();
                toCountry = toText.getText().toString();
                lastDate = dateText.getText().toString();
                freeWeight=weightText.getText().toString();

                if (noEmptyField())
                {
                    ITEM.setCountry_from(fromCountry);
                    ITEM.setCountry_to(toCountry);
                    ITEM.setMeeting_date(lastDate);
                    ITEM.setAvailable_weight(Double.parseDouble(freeWeight));

                    // updating...
                    Repository.updateTripItem(ITEM);
                    Toast.makeText(EditTripItemActivity.this, "trip edited :)", Toast.LENGTH_SHORT).show();
                    // Go back TripNavFragment
                    arrow_back_function();
                } else {
                    Toast.makeText(EditTripItemActivity.this, "Empty Field", Toast.LENGTH_SHORT).show();
                }
            }
        });

        uploadTripBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromCountry = fromText.getText().toString();
                toCountry = toText.getText().toString();
                lastDate = dateText.getText().toString();
                freeWeight=weightText.getText().toString();

                if (noEmptyField())
                {
                    ITEM.setCountry_from(fromCountry);
                    ITEM.setCountry_to(toCountry);
                    ITEM.setMeeting_date(lastDate);
                    ITEM.setAvailable_weight(Double.parseDouble(freeWeight));
                    ITEM.setIsEditable(0);
                    // uploading...
                    Repository.updateTripItem(ITEM);
                    Toast.makeText(EditTripItemActivity.this, "trip uploaded :)", Toast.LENGTH_SHORT).show();
                    // Go back TripNavFragment
                    arrow_back_function();
                } else {
                    Toast.makeText(EditTripItemActivity.this, "Empty uploaded", Toast.LENGTH_SHORT).show();
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
