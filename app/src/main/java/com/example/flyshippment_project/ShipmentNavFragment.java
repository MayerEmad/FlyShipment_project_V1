package com.example.flyshippment_project;

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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Objects;

import adapters_and_items.AdapterRecyclerShipment;
import adapters_and_items.Repository;
import adapters_and_items.ShipmentItem;
import adapters_and_items.SearchViewModel;


public class ShipmentNavFragment extends Fragment {

    private RecyclerView recyclerView;

    public ShipmentNavFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shipment_nav, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        final SearchViewModel viewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(SearchViewModel.class);
        FloatingActionButton fab =(FloatingActionButton) view.findViewById(R.id.floating_button_shipment_nav);
        recyclerView = (RecyclerView) view.findViewById(R.id.user_shipments_recycler_view);

        //Intialise the Recycler Viewer
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        RecyclerView.Adapter mAdapter = new AdapterRecyclerShipment(Repository.getShipmentsFromApi(), getContext());
        recyclerView.setAdapter(mAdapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
        //When the LiveData  Changes due to Loading or Filtering it will be updated here
        viewModel.getShipmentLiveData().observe(getViewLifecycleOwner(), new Observer<ArrayList<ShipmentItem>>() {
            @Override
            public void onChanged(ArrayList<ShipmentItem> shipmentItems) {
                recyclerView.setAdapter(new AdapterRecyclerShipment( shipmentItems,getContext()));
            }
        });
    }
}
