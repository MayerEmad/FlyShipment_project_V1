package search_classes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import adapters_and_items.AdapterRecyclerShipment;
import com.example.flyshippment_project.R;

import com.example.flyshippment_project.Repository;
import com.example.flyshippment_project.MyViewModel;
import adapters_and_items.ShipmentItem;

import java.util.ArrayList;

public class Shipment_Shower_Frag extends Fragment
{
    public Shipment_Shower_Frag() { }

    private RecyclerView recyclerView;
    private ArrayList<ShipmentItem>userList;
    private AdapterRecyclerShipment mAdapter;
    private View loadingIndicator;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //Its Layout is the Recycler View as shipment_Shower_Freg
        return inflater.inflate(R.layout.recycler_viewer_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull  View view, @Nullable Bundle savedInstanceState)
    {
       // Log.i("onViewCreated: ","1----------->is here");
        loadingIndicator = view.findViewById(R.id.loading_indicator);
        recyclerView = (RecyclerView) view.findViewById(R.id.rc1);
        userList=Repository.getShipmentsFromApi(getActivity().getApplicationContext());

        // For the first time API will take time to get Shipments from doInBackground
        loadingIndicator.setVisibility(View.VISIBLE);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //When the LiveData  Changes due to Loading or Filtering it will be updated here
        MyViewModel.getShipmentLiveData().observe(getViewLifecycleOwner(), new Observer<ArrayList<ShipmentItem>>()
        {
            @Override
            public void onChanged(ArrayList<ShipmentItem> shipmentItems)
            {
                loadingIndicator.setVisibility(View.INVISIBLE);
                userList=MyViewModel.getShipmentLiveData().getValue();
                mAdapter=new AdapterRecyclerShipment(userList,getContext(),"shipment_shower_freg",-1);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(mAdapter);
            }
        });
    }
}

