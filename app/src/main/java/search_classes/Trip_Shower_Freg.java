package search_classes;

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

import adapters_and_items.AdapterRecyclerShipment;
import com.example.flyshippment_project.R;

import adapters_and_items.AdapterRecyclerTrip;
import adapters_and_items.Repository;
import adapters_and_items.ShipmentItem;
import adapters_and_items.TripItem;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Trip_Shower_Freg extends Fragment
{

    public Trip_Shower_Freg() {
        // Required empty public constructor
    }

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View rootView= inflater.inflate(R.layout.recycler_viewer_search, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        Log.i("refresh", "---------------ViewCreated---------------------: ");
        final SearchViewModel viewModel = ViewModelProviders.of(getActivity()).get(SearchViewModel.class);
        final View loadingIndicator = view.findViewById(R.id.loading_indicator);
        recyclerView = (RecyclerView) view.findViewById(R.id.rc1);

        //Intialise the Recycler Viewer
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        mAdapter=new AdapterRecyclerTrip(Repository.getTripsFromApi(),getContext());
        recyclerView.setAdapter(mAdapter);

        // For the first time API will take time to get Shipments from doInBackground
        // So after we get Shipments AsyncTask onPostExecute will update LiveData
        loadingIndicator.setVisibility(View.VISIBLE);  /*TODO Problem*/

        //When the LiveData  Changes due to Loading or Filtering it will be updated here
        viewModel.getTripLiveData().observe(getViewLifecycleOwner(), new Observer<ArrayList<TripItem>>() {
            @Override
            public void onChanged(ArrayList<TripItem> tripItems) {
                recyclerView.setAdapter(new AdapterRecyclerTrip(tripItems,getContext()));

                // Remove the Progress par
                loadingIndicator.setVisibility(View.GONE);
            }
        });
    }
}
