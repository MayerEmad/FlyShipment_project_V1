package com.example.flyshippment_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import adapters_and_items.ShipmentItem;
import login_rejester_splash.SplashScreen;
import search_classes.SearchNavFragment;
import search_classes.Shipment_Shower_Freg;

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
                        case R.id.profile_bottom_nav:
                            SelectedFragment=new ProfileNavFragment();
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

        //* Bottom Navigation Bar Listener
        BottomNavigationView bottomNav=findViewById(R.id.bottom_nav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //*choose the Fragment to go
        Bundle extras = getIntent().getExtras();
        if(extras!=null && extras.containsKey("openShipmentNav") && extras.getBoolean("openShipmentNav")) {
            bottomNav.getMenu().getItem(1).setChecked(true);
            getSupportFragmentManager().beginTransaction().replace(R.id.container_frame,new ShipmentNavFragment()).commit();
        }
        else {
          getSupportFragmentManager().beginTransaction().replace(R.id.container_frame,new SearchNavFragment()).commit(); //Default
        }
    }

}
