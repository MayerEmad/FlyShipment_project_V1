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

import com.example.flyshippment_project.R;

import adapters_and_items.AdapterRecyclerTrip;
import com.example.flyshippment_project.Repository;
import com.example.flyshippment_project.MyViewModel;
import adapters_and_items.TripItem;

import java.util.ArrayList;

public class Trip_Shower_Frag extends Fragment
{

    public Trip_Shower_Frag() {
        // Required empty public constructor
    }

    private RecyclerView recyclerView;
    private ArrayList<TripItem>userList;
    private AdapterRecyclerTrip mAdapter;
    private View loadingIndicator;

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

        loadingIndicator = view.findViewById(R.id.loading_indicator);
        recyclerView = (RecyclerView) view.findViewById(R.id.rc1);
        userList=Repository.getTripsFromApi();

        // For the first time API will take time to get Shipments from doInBackground
        loadingIndicator.setVisibility(View.VISIBLE);

        //When the LiveData  Changes due to Loading or Filtering it will be updated here
        MyViewModel.getTripLiveData().observe(getViewLifecycleOwner(), new Observer<ArrayList<TripItem>>() {
            @Override
            public void onChanged(ArrayList<TripItem> tripItems) {
                loadingIndicator.setVisibility(View.INVISIBLE);
                userList=MyViewModel.getTripLiveData().getValue();
                mAdapter=new AdapterRecyclerTrip(userList,getContext(),"trip_shower_fragment");
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setHasFixedSize(true);
                recyclerView.setAdapter(mAdapter);
            }
        });
    }
}
