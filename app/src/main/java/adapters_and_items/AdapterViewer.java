package adapters_and_items;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import search_classes.Shipment_Shower_Freg;
import search_classes.Trip_Shower_Freg;

public class AdapterViewer extends FragmentStatePagerAdapter
{
    public AdapterViewer(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            Log.i("AdapterViewer", "----------Shipment_Shower_Freg is Created");
            return new Shipment_Shower_Freg();
        } else {
            Log.i("AdapterViewer", "----------Trip_Shower_Freg is created");
            return new Trip_Shower_Freg();
        }
    }

    @Override
    public int getCount() { return 2; }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(position==0) return "Shipments";
        else return "Trips";
    }
}