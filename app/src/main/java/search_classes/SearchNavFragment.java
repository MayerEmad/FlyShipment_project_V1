package search_classes;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import adapters_and_items.AdapterViewerSearch;

import com.example.flyshippment_project.DatePickerFragment;
import com.example.flyshippment_project.Repository;
import com.example.flyshippment_project.MyViewModel;
import adapters_and_items.ShipmentItem;
import adapters_and_items.TripItem;

import com.example.flyshippment_project.R;
import com.google.android.material.tabs.TabLayout;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class SearchNavFragment extends Fragment implements DatePickerDialog.OnDateSetListener
{
    public SearchNavFragment() {
        // Required empty public constructor
    }
    static class mydate {
        int day,month,year;
        mydate(int year, int month, int day) {
            this.year = year;
            this.month = month;
            this.day = day;
        }
        private static mydate convertFromStringToMydate(String dateStr)
        {
            String[] numbersArr = dateStr.split("-");
            return new mydate(Integer.parseInt(numbersArr[0]), Integer.parseInt(numbersArr[1]), Integer.parseInt(numbersArr[2]));
        }

        private boolean isBeforeMyDate(String dateStr)
        {
            mydate otherDate=convertFromStringToMydate(dateStr);
            if(otherDate.year<this.year) return true;
            else if(otherDate.year==this.year && otherDate.month<this.month) return true;
            else if(otherDate.year==this.year && otherDate.month==this.month && otherDate.day<this.day)return true;
            else if(otherDate.year==this.year && otherDate.month==this.month && otherDate.day==this.day)return true;
            else return false;
        }
       
        @NonNull
        @Override
        public String toString() {
            return this.year+"-"+this.month+"-"+this.day;
        }
    }
    private String fromCountry="";
    private String toCountry="";
    private double weight;
    private mydate date;
    private EditText et_date;

    private boolean countryComp(String a, String b){
        return a.equalsIgnoreCase(b) ||  a.isEmpty();
    }

    private String digitsOnly(String weightStr) {
        String d="";
        for(int i=0;i<weightStr.length();i++)
            if(weightStr.charAt(i)>='0'&&weightStr.charAt(i)<='9') d+=weightStr.charAt(i);
        return d;
    }

    //FIXME bad way
    private ArrayList<ShipmentItem> ShipmentsLocalStorage=MyViewModel.getShipmentLiveData().getValue();
    private ArrayList<TripItem> TripsLocalStorage=MyViewModel.getTripLiveData().getValue();

    private ArrayList<ShipmentItem> getFilteredShipments() {
        ArrayList<ShipmentItem> ShipmentList = ShipmentsLocalStorage;
        ArrayList<ShipmentItem> filteredShipmentList= new ArrayList<ShipmentItem>();

        for(int i=0;i<ShipmentList.size();i++)
        {
            // I have a trip
            ShipmentItem item=ShipmentList.get(i);
            if(countryComp(fromCountry,item.getCountry_from()) &&countryComp(toCountry,item.getCountry_to()) &&
               weight>=item.getItemWeight() && date.isBeforeMyDate(item.getLast_date()))   filteredShipmentList.add(item);
        }

        return filteredShipmentList;
    }

    private ArrayList<TripItem> getFilteredTrips() {
        ArrayList<TripItem> TripList = TripsLocalStorage;
        ArrayList<TripItem> filteredTripList= new ArrayList<TripItem>();
        if(weight==1000000000)weight=0;
        for(int i=0;i<TripList.size();i++)
        {
            // I have a shipment
            TripItem item=TripList.get(i);
            if(countryComp(fromCountry,item.getCountry_from()) &&countryComp(toCountry,item.getCountry_to()) &&
               weight<=item.getAvailable_weight() && date.isBeforeMyDate(item.getMeeting_date()))   filteredTripList.add(item);
        }
        return filteredTripList;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_nav, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        // Create an adapter that knows which fragment should be shown on each page
        final AdapterViewerSearch adapter = new AdapterViewerSearch(getChildFragmentManager(),1);

        // Viewer page hosts the Shipment_Shower_Freg & Trip_Shower_Freg
        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.search_page_viewer);
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.search_page_upper_tab);
        tabLayout.setupWithViewPager(viewPager);

        Button searchButton = (Button)view.findViewById(R.id.search_button);
        final EditText et_to=(EditText)view.findViewById(R.id.to);
        final EditText et_from=(EditText)view.findViewById(R.id.from);
        final EditText et_weight=(EditText)view.findViewById(R.id.weight);
        et_date=(EditText)view.findViewById(R.id.date);
        et_date.setFocusable(false);

        //-------------------------Click listeners------------------------

        et_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker= new DatePickerFragment();
                datePicker.show(getChildFragmentManager(),"date picker");
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                fromCountry=et_from.getText().toString();
                toCountry=et_to.getText().toString();

                if(et_date.getText().toString().isEmpty())date = mydate.convertFromStringToMydate("3000-32-32");
                else date = mydate.convertFromStringToMydate(et_date.getText().toString().substring(7));

                String weightStr=et_weight.getText().toString();
                weightStr=digitsOnly(weightStr);
                if(!weightStr.isEmpty()) weight=Double.parseDouble(weightStr);
                else weight=1000000000;

                MyViewModel.setShipmentLiveData(getFilteredShipments());
                MyViewModel.setTripLiveData(getFilteredTrips());
            }
        });

        //-------------------------Observers------------------------
        // update local storage just for once  FIXME not good way
        MyViewModel.getShipmentLiveData().observe(getViewLifecycleOwner(), new Observer<ArrayList<ShipmentItem>>()
        {
            @Override
            public void onChanged(ArrayList<ShipmentItem> shipmentItems) {
                if(ShipmentsLocalStorage==null)
                     ShipmentsLocalStorage = MyViewModel.getShipmentLiveData().getValue();
            }
        });
        MyViewModel.getTripLiveData().observe(getViewLifecycleOwner(), new Observer<ArrayList<TripItem>>()
        {
            @Override
            public void onChanged(ArrayList<TripItem> tripItems) {
                if(TripsLocalStorage==null)
                    TripsLocalStorage = MyViewModel.getTripLiveData().getValue();
            }
        });
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        month++;
        String CurrentDateString=year+"-"+month+"-"+dayOfMonth;
        date=mydate.convertFromStringToMydate(CurrentDateString);
        et_date.setText("before "+CurrentDateString);
    }
}
