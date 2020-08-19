package adapters_and_items;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.flyshippment_project.Repository;

import inbox_classes.Deals_Inbox_Frag;
import inbox_classes.Shipments_Inbox_Frag;

public class AdapterViewerInbox extends FragmentStatePagerAdapter
{
    public AdapterViewerInbox(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            Repository.getShipmentRequestsFromApi();
            return new Shipments_Inbox_Frag();
        } else {
            Repository.getShipmentDealsFromApi();
            return new Deals_Inbox_Frag();
        }
    }

    @Override
    public int getCount() { return 2; }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(position==0) return "Ships Requests";
        else return "Deals";
    }
}

