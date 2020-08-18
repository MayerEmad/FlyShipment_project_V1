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
import adapters_and_items.TripItem;

public class ShowTripActivity extends AppCompatActivity {
    private EditText fromText;
    private EditText toText;
    private EditText dateText;
    private EditText weightText;

    private void arrow_back_function() {
        Intent intent = new Intent(ShowTripActivity.this, MainActivity.class);
        intent .putExtra("openTripNav",true);
        overridePendingTransition(0, 0);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_trip);

        Bundle extras = getIntent().getExtras();
        int pos=extras.getInt("TripItemPosition");
        final TripItem ITEM=MyViewModel.getTripLiveData().getValue().get(pos);

        fromText = (EditText) findViewById(R.id.show_trip_from);
        fromText.setText(ITEM.getCountry_from());
        fromText.setEnabled(false);
        toText = (EditText) findViewById(R.id.show_trip_to);
        toText.setText(ITEM.getCountry_to());
        toText.setEnabled(false);
        weightText = (EditText) findViewById(R.id.show_trip_available_weight);
        weightText.setText(String.valueOf(ITEM.getAvailable_weight()));
        weightText.setEnabled(false);
        dateText = (EditText) findViewById(R.id.show_trip_last_date);
        dateText.setText(ITEM.getMeeting_date());
        dateText.setEnabled(false);

        Button backArrowButton = (Button) findViewById(R.id.show_trip_back_button);
        backArrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrow_back_function();
            }
        });

    }

}
