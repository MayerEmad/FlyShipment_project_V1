package search_classes;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import adapters_and_items.AdapterViewer;
import adapters_and_items.Repository;
import adapters_and_items.ShipmentItem;
import adapters_and_items.TripItem;

import com.example.flyshippment_project.R;
import com.google.android.material.tabs.TabLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SearchNavFragment extends Fragment
{

    public SearchNavFragment() {
        // Required empty public constructor
    }

    private SearchViewModel viewModel;
    private String fromCountery="";
    private String toCountery="";
    private double weight=0;
    private Date date;

    private Date StringToDate(String dob) throws ParseException
    {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = formatter.parse(dob);
        //System.out.println("Date object value: "+date);
        return date;
    }

    private ArrayList<ShipmentItem> getFilteredShipments()
    {
        ArrayList<ShipmentItem> ShipmentList =Repository.getShipmentsFromApi();
        ArrayList<ShipmentItem> filteredShipmentList= new ArrayList<ShipmentItem>();

        for(int i=0;i<ShipmentList.size();i++)
           {
               ShipmentItem item=ShipmentList.get(i);
               //Log.i("SEARCH--->", item.getCountry_from()+" "+item.getCountry_to()+" "+item.getWeight());
               //Log.i("copare--->", fromCountery+" "+toCountery+" "+weight);
               if(fromCountery.equals(item.getCountry_from()) )//&& toCountery==item.getCountry_to() && weight>=item.getWeight())
               {
                   filteredShipmentList.add(item);
               }
           }
        return filteredShipmentList;
    }

    private ArrayList<TripItem> getFilteredTrips()
    {
        ArrayList<TripItem> TripList = Repository.getTripsFromApi();
        ArrayList<TripItem> filteredtripList= new ArrayList<TripItem>();
        for(int i=0;i<TripList.size();i++)
        {
            TripItem item=TripList.get(i);
            if(fromCountery.equals(item.getCountry_from()) ||toCountery.equals(item.getCountry_to())  )  // && weight>=item.getWeight())
             {
                filteredtripList.add(item);
             }
        }
        return filteredtripList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search_nav, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        // Create an adapter that knows which fragment should be shown on each page
        final AdapterViewer adapter = new AdapterViewer(getChildFragmentManager(),1);

        // Viewer page hosts the Shipment_Shower_Freg & Trip_Shower_Freg
        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.search_page_viewer);
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.search_page_upper_tab);
        tabLayout.setupWithViewPager(viewPager);

        Button searchButton = (Button)view.findViewById(R.id.search_button);
        final EditText et_to=(EditText)view.findViewById(R.id.to);
        final EditText et_from=(EditText)view.findViewById(R.id.from);
        final EditText et_weight=(EditText)view.findViewById(R.id.weight);
        final EditText et_date=(EditText)view.findViewById(R.id.date);


        viewModel = ViewModelProviders.of(getActivity()).get(SearchViewModel.class);
        // on Searching..
        searchButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                fromCountery=et_from.getText().toString();
                toCountery=et_to.getText().toString();

                String strwe=et_weight.getText().toString();
                if(!strwe.isEmpty()) weight=Double.parseDouble(strwe);
                try
                {
                    date = StringToDate(et_date.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                //TODO apply filtering and store them in list

                viewModel.setShipmentLiveData(getFilteredShipments());
                viewModel.setTripLiveData(getFilteredTrips());
            }
        });
    }
}
