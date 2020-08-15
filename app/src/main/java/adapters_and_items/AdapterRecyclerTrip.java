package adapters_and_items;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.flyshippment_project.MainActivity;
import com.example.flyshippment_project.R;

import java.util.ArrayList;

import Shipments_Trips_classes.CreateTripItemActivity;
import Shipments_Trips_classes.EditTripItemActivity;
import Shipments_Trips_classes.TripNavFragment;

public class AdapterRecyclerTrip extends RecyclerView.Adapter<AdapterRecyclerTrip.MyViewHolder>
{
    private ArrayList<TripItem> TripList;
    private Context mContext;
    private String parent;


    // Provide a suitable constructor (depends on the kind of dataset)
    public AdapterRecyclerTrip(ArrayList<TripItem> DataList,Context con,String dad )
    {
        TripList = DataList;
        mContext=con;
        parent=dad;

    }

    //ok
    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView country_from, country_to,profile_name,meeting_date,available_weight_text,consumed_weight_text;
        public ImageView profile_image;
        public Button request_btn;
        public RatingBar sender_rate_bar;
        public ConstraintLayout upper_info_part;

        public MyViewHolder(View listItemView)
        {
            super(listItemView);
            country_from =(TextView)listItemView.findViewById(R.id.trip_item_country_from);
            country_to =(TextView)listItemView.findViewById(R.id.trip_item_country_to);
            meeting_date=(TextView)listItemView.findViewById(R.id.trip_item_date);
            available_weight_text=(TextView)listItemView.findViewById(R.id.trip_item_available_weight);
            consumed_weight_text=(TextView)listItemView.findViewById(R.id.trip_item_consumed_weight);
            profile_image=(ImageView)listItemView.findViewById(R.id.trip_item_profile_img);
            profile_name=(TextView)listItemView.findViewById(R.id.trip_item_profile_name);
            sender_rate_bar=(RatingBar)listItemView.findViewById(R.id.trip_item_rating_bar);
            request_btn=(Button)listItemView.findViewById(R.id.trip_item_request_btn);

            upper_info_part=(ConstraintLayout)listItemView.findViewById(R.id.trip_item_upper_part);
        }
    }

    // ok Create new views (invoked by the layout manager)
    @Override
    public AdapterRecyclerTrip.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View listItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.trip_item, parent, false);
        MyViewHolder vh = new MyViewHolder(listItemView);
        return vh;
    }

    // ok Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final TripItem item = TripList.get(position);

        holder.country_from.setText(item.getCountry_from());
        holder.country_to.setText(item.getCountry_to());
        holder.meeting_date.setText(item.getMeeting_date());
        holder.available_weight_text.setText(item.getStrAvailable_weight());
        holder.consumed_weight_text.setText(item.getConsumed_weight());
        Glide.with(mContext).load(item.getProfile_image_url())
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(holder.profile_image);
        holder.profile_name.setText(item.getProfile_name());
        holder.sender_rate_bar.setRating(item.getUser_rate());
        if(!parent.equals("trip_shower_fragment"))holder.request_btn.setVisibility(View.INVISIBLE);


        //Listeners..
        holder.request_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(parent.equals("trip_shower_fragment")) {
                    Intent intent = new Intent(mContext, MainActivity.class);
                    intent.putExtra("openShipmentNav", true);
                    intent.putExtra("AdapterRecyclerTripParent", true);
                    intent.putExtra("AdapterTripId",item.getTrip_id());
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    mContext.startActivity(intent);

                }

            }
        });
        holder.profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { }
        });

        holder.upper_info_part.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(parent.equals("trip_nav_fragment"))
                {
                    //go to Edit the tripItem
                    Intent intent =new Intent(mContext, EditTripItemActivity.class);
                    intent.putExtra("TripItemPosition",position);
                    mContext.startActivity(intent);
                }
                else if(parent.equals("trip_shower_fragment")){
                    //go to show the tripItem
                    Intent intent =new Intent(mContext, CreateTripItemActivity.class);
                    mContext.startActivity(intent);
                }
            }
        });
    }

    // ok Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if(TripList==null) return 0;
        return TripList.size();
    }

}

