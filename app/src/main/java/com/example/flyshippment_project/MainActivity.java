package com.example.flyshippment_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

import Shipments_Trips_classes.ShipmentNavFragment;
import Shipments_Trips_classes.TripNavFragment;
import adapters_and_items.ApiUserInfo;
import more_classes.MoreNavFragment;
import search_classes.SearchNavFragment;

public class MainActivity extends AppCompatActivity
{
    private static final String TAG = "MainActivity";
    private static boolean goToSplash=true;


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

        // FIXME Temporary------------
        ApiUserInfo task=new ApiUserInfo(); task.DoTaskInBack();

        //* Bottom Navigation Bar Listener
        BottomNavigationView bottomNav=findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //*choose the Fragment to go
        Bundle extras = getIntent().getExtras();
        if(extras!=null && extras.containsKey("openShipmentNav") && extras.getBoolean("openShipmentNav")) {
            bottomNav.getMenu().getItem(1).setChecked(true);
            getSupportFragmentManager().beginTransaction().replace(R.id.container_frame,new ShipmentNavFragment()).commit();
        }
        else if(extras!=null && extras.containsKey("openTripNav") && extras.getBoolean("openTripNav")) {
            bottomNav.getMenu().getItem(4).setChecked(true);
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

}
