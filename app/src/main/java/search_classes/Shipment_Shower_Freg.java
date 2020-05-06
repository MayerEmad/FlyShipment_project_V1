package search_classes;

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
import android.widget.Toast;

import adapters_and_items.AdapterRecyclerShipment;
import com.example.flyshippment_project.R;

import adapters_and_items.Repository;
import adapters_and_items.ShipmentItem;

import java.util.ArrayList;

public class Shipment_Shower_Freg extends Fragment
{
    public Shipment_Shower_Freg() { }

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //Its Layout is the Recycler View as Trip_Shower_Freg
        return inflater.inflate(R.layout.recycler_viewer_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull  View view, @Nullable Bundle savedInstanceState)
    {
        final SearchViewModel viewModel = ViewModelProviders.of(getActivity()).get(SearchViewModel.class);
        final View loadingIndicator = view.findViewById(R.id.loading_indicator);
        recyclerView = (RecyclerView) view.findViewById(R.id.rc1);

        //Intialise the Recycler Viewer
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        mAdapter=new AdapterRecyclerShipment(Repository.getShipmentsFromApi());
        recyclerView.setAdapter(mAdapter);

        // For the first time API will take time to get Shipments from doInBackground
        // So after we get Shipments AsyncTask onPostExecute will update LiveData
        loadingIndicator.setVisibility(View.VISIBLE);  /*TODO Problem*/

        //When the LiveData  Changes due to Loading or Filtering it will be updated here
        viewModel.getShipmentLiveData().observe(getViewLifecycleOwner(), new Observer<ArrayList<ShipmentItem>>() {
            @Override
            public void onChanged(ArrayList<ShipmentItem> shipmentItems) {
                recyclerView.setAdapter(new AdapterRecyclerShipment( shipmentItems));

                // Remove the Progress par
                loadingIndicator.setVisibility(View.GONE);
            }
        });
    }

}
