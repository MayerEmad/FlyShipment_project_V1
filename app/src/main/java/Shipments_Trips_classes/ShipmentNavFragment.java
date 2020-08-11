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

import android.util.Log;
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

import adapters_and_items.AdapterRecyclerShipment;
import adapters_and_items.ShipmentItem;


public class ShipmentNavFragment extends Fragment
{
    private RecyclerView recyclerView;
    private String parentCaller;
    private int tripId;
    public ShipmentNavFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle arguments = getArguments();
        if(arguments!=null) {
           // Log.i("requestTest", "ShipNavFreg------->data received ");
            parentCaller = getArguments().getString("requestCaller");
            tripId = getArguments().getInt("AdapterTripId");
        }else {
            parentCaller = "shipment_nav_fragment";
            tripId=-1;
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shipment_nav, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        final MyViewModel viewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(MyViewModel.class);
        FloatingActionButton fab =(FloatingActionButton) view.findViewById(R.id.floating_button_shipment_nav);
        final TextView noShipmentText=(TextView) view.findViewById(R.id.no_shipments_text);
        recyclerView = (RecyclerView) view.findViewById(R.id.user_shipments_recycler_view);

        //Intialize the Recycler Viewer
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        ArrayList<ShipmentItem>userList= Repository.getUserShipmentsFromApi();
        if(userList!=null)
        {
            noShipmentText.setVisibility(View.INVISIBLE);
            RecyclerView.Adapter mAdapter = new AdapterRecyclerShipment(userList, getContext(),parentCaller,tripId);
            recyclerView.setAdapter(mAdapter);
        }
        else
        {
            //Toast.makeText(ShipmentNavFragment.this.getContext(), "userlist is full", Toast.LENGTH_SHORT).show();
            noShipmentText.setVisibility(View.VISIBLE);
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                Intent intent =new Intent(getContext(),CreateShipmentItemActivity.class);
                startActivity(intent);
            }
        });
        //When the LiveData  Changes due to Loading or Filtering it will be updated here
        MyViewModel.getUserShipmentLiveData().observe(getViewLifecycleOwner(), new Observer<ArrayList<ShipmentItem>>() {
            @Override
            public void onChanged(ArrayList<ShipmentItem> shipmentItems) {
                recyclerView.setAdapter(new AdapterRecyclerShipment(shipmentItems,getContext(),parentCaller,tripId));
                noShipmentText.setVisibility(View.INVISIBLE);
            }
        });
    }
}
