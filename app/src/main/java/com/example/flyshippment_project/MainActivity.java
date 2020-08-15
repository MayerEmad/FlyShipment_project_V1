package com.example.flyshippment_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

import Shipments_Trips_classes.ShipmentNavFragment;
import Shipments_Trips_classes.TripNavFragment;
import inbox_classes.InboxNavFragment;
import more_classes.MoreNavFragment;
import search_classes.SearchNavFragment;

public class MainActivity extends AppCompatActivity
{
    private static final String TAG = "MainActivity";

    private BottomNavigationView.OnNavigationItemSelectedListener navListener=
            new BottomNavigationView.OnNavigationItemSelectedListener()
            {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment SelectedFragment=null;

                    switch (menuItem.getItemId()) {
                        case R.id.search_bottom_nav:
                            SelectedFragment=new SearchNavFragment();
                            break;
                        case R.id.shipment_bottom_nav:
                            SelectedFragment=new ShipmentNavFragment();
                            break;
                        case R.id.trib_bottom_nav:
                            SelectedFragment=new TripNavFragment();
                            break;
                        case R.id.inbox_bottom_nav:
                            SelectedFragment=new InboxNavFragment();
                            break;
                        case R.id.more_bottom_nav:
                            SelectedFragment=new MoreNavFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.container_frame,SelectedFragment).commit();
                    return true;
                }
            };

    // We can move to SearchNavFragment , ShipmentNavFragment , TripNavFragment , InboxNavFragment ,ProfileNavFragment
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //* Check if the device is connected to the internet
        ConnectivityManager cm = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = Objects.requireNonNull(cm).getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if(!isConnected) {
            Toast.makeText(this, "there is no internet access :(", Toast.LENGTH_SHORT).show();
        }

        // Starting point ------------
        if(Repository.TheProfileItem==null)getProfileItemForTheFirstTime();


        //* Bottom Navigation Bar Listener
        BottomNavigationView bottomNav=findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //choose the Fragment to go
        Bundle extras = getIntent().getExtras();
        if(extras!=null && extras.containsKey("openShipmentNav") && extras.getBoolean("openShipmentNav")) {
            bottomNav.getMenu().getItem(1).setChecked(true);
            ShipmentNavFragment shipmentNavFragment = new ShipmentNavFragment();
            if(extras.containsKey("AdapterRecyclerTripParent") && extras.getBoolean("AdapterRecyclerTripParent")){
                Bundle bundle = new Bundle();
                bundle.putString("requestCaller", "AdapterRecyclerTripParent");
                bundle.putInt("AdapterTripId", extras.getInt("AdapterTripId"));
                shipmentNavFragment.setArguments(bundle);
               // Log.i("requestTest", "MainActivity------->send data to ShipNavFreg ");
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.container_frame,shipmentNavFragment).commit();
        }
        else if(extras!=null && extras.containsKey("openTripNav") && extras.getBoolean("openTripNav")) {
            bottomNav.getMenu().getItem(2).setChecked(true);
            getSupportFragmentManager().beginTransaction().replace(R.id.container_frame,new TripNavFragment()).commit();
        }
        else if(extras!=null && extras.containsKey("openMoreNav") && extras.getBoolean("openMoreNav")) {
            bottomNav.getMenu().getItem(4).setChecked(true);
            getSupportFragmentManager().beginTransaction().replace(R.id.container_frame,new MoreNavFragment()).commit();
        }
        else {
          getSupportFragmentManager().beginTransaction().replace(R.id.container_frame,new SearchNavFragment()).commit(); //Default
        }
    }

    public void getProfileItemForTheFirstTime() {
        SharedPreferences PREF_USER_ID = getSharedPreferences("userid" , MODE_PRIVATE);
        int id=PREF_USER_ID.getInt("userid",2);
        Repository.getUserInfo(id,getApplicationContext());
    }

}
