package adapters_and_items;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import search_classes.Shipment_Shower_Frag;
import search_classes.Trip_Shower_Frag;

public class AdapterViewerSearch extends FragmentStatePagerAdapter
{
    public AdapterViewerSearch(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        if (position == 0) {
           // Log.i("AdapterViewer", "----------Shipment_Shower_Freg is Created");
            return new Trip_Shower_Frag();
        } else {
            //Log.i("AdapterViewer", "----------Trip_Shower_Freg is created");
            return new Shipment_Shower_Frag();
        }
    }

    @Override
    public int getCount() { return 2; }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(position==0) return "Trips";
        else return "Shipments";
    }
}