package inbox_classes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.flyshippment_project.R;
import com.google.android.material.tabs.TabLayout;

import adapters_and_items.AdapterViewerInbox;


public class InboxNavFragment extends Fragment {

    public InboxNavFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inbox_nav, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        final AdapterViewerInbox adapter = new AdapterViewerInbox(getChildFragmentManager(),1);

        // Viewer page hosts the Shipment_Shower_Freg & Trip_Shower_Freg
        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.inbox_page_viewer);
        viewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.inbox_page_upper_tab);
        tabLayout.setupWithViewPager(viewPager);

    }
}
