package Shipments_Trips_classes;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.flyshippment_project.MyViewModel;
import com.example.flyshippment_project.R;
import com.example.flyshippment_project.Repository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;

import adapters_and_items.AdapterRecyclerTrip;
import adapters_and_items.TripItem;

public class TripNavFragment extends Fragment {

    private String parentCaller="trip_nav_fragment";
    private RecyclerView recyclerView;
    public TripNavFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_trip_nav, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        FloatingActionButton fab =(FloatingActionButton) view.findViewById(R.id.floating_button_trip_nav);
        final TextView noTripText=(TextView) view.findViewById(R.id.no_trips_text);
        recyclerView = (RecyclerView) view.findViewById(R.id.user_trips_recycler_view);

        //initialize the Recycler Viewer
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        ArrayList<TripItem> userList= Repository.getUserTripsFromApi();
        if(userList!=null)
        {
            if(!userList.isEmpty())noTripText.setVisibility(View.INVISIBLE);
            RecyclerView.Adapter mAdapter = new AdapterRecyclerTrip(userList, getContext(),parentCaller);
            recyclerView.setAdapter(mAdapter);
        }
        else
        {
            //Toast.makeText(TripNavFragment.this.getContext(), "userlist is full", Toast.LENGTH_SHORT).show();
            noTripText.setVisibility(View.VISIBLE);
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                Intent intent =new Intent(getContext(),CreateTripItemActivity.class);
                startActivity(intent);
            }
        });
        //When the LiveData  Changes due to Loading or Filtering it will be updated here
        MyViewModel.getUserTripLiveData().observe(getViewLifecycleOwner(), new Observer<ArrayList<TripItem>>() {
            @Override
            public void onChanged(ArrayList<TripItem> tripItems) {
                recyclerView.setAdapter(new AdapterRecyclerTrip(tripItems,getContext(),parentCaller));
               if(!tripItems.isEmpty()) noTripText.setVisibility(View.INVISIBLE);
            }
        });
    }
}
