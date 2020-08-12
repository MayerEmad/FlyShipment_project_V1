package inbox_classes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flyshippment_project.MyViewModel;
import com.example.flyshippment_project.R;
import com.example.flyshippment_project.Repository;

import java.util.ArrayList;

import adapters_and_items.AdapterRecyclerShipmentRequests;
import adapters_and_items.ShipmentRequestItem;

public class Shipments_Inbox_Frag extends Fragment {
    public Shipments_Inbox_Frag() { }

    private RecyclerView recyclerView;
    private ArrayList<ShipmentRequestItem> shipRequestsList;
    private AdapterRecyclerShipmentRequests mAdapter;
    private View loadingIndicator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //bad name i could not change it but its for all part Fragments
        return inflater.inflate(R.layout.recycler_viewer_search, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        // Log.i("onViewCreated: ","1----------->is here");
        loadingIndicator = view.findViewById(R.id.loading_indicator);
        recyclerView = (RecyclerView) view.findViewById(R.id.rc1);
        shipRequestsList= Repository.getShipmentRequestsFromApi();

        // For the first time API will take time to get Shipments from doInBackground
        loadingIndicator.setVisibility(View.VISIBLE);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //When the LiveData  Changes due to Loading or Filtering it will be updated here
        MyViewModel.getShipmentRequestsLiveData().observe(getViewLifecycleOwner(), new Observer<ArrayList<ShipmentRequestItem>>()
        {
            @Override
            public void onChanged(ArrayList<ShipmentRequestItem> shipmentItems)
            {
                loadingIndicator.setVisibility(View.INVISIBLE);
                shipRequestsList=MyViewModel.getShipmentRequestsLiveData().getValue();
                mAdapter=new AdapterRecyclerShipmentRequests(shipRequestsList,getContext(),"shipment_inbox_fragment");
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(mAdapter);
            }
        });
    }
}
