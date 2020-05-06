package adapters_and_items;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flyshippment_project.R;

import java.util.ArrayList;

public class AdapterRecyclerTrip extends RecyclerView.Adapter<AdapterRecyclerTrip.MyViewHolder>
{
    private ArrayList<TripItem> TripList;
    private Context mContext;

    // Provide a suitable constructor (depends on the kind of dataset)
    public AdapterRecyclerTrip(ArrayList<TripItem> DataList,Context con)
    {
        TripList = DataList;
        mContext=con;
    }

    //ok
    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView country_from, country_to,profile_name,meeting_date,available_weight_text,consumed_weight_text;
        public ImageView profile_image;
        public Button request_btn;
        public RatingBar sender_rate_bar;
        public LinearLayout upper_info_part;

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
            upper_info_part=(LinearLayout)listItemView.findViewById(R.id.trip_item_upper_part);
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
    public void onBindViewHolder(MyViewHolder holder, int position) {
        TripItem item = TripList.get(position);

        holder.country_from.setText(item.getCountry_from());
        holder.country_to.setText(item.getCountry_to());
        holder.meeting_date.setText(item.getMeeting_date());
        holder.available_weight_text.setText(item.getAvailable_weight());
        holder.consumed_weight_text.setText(item.getConsumed_weight());
       // holder.profile_image.setImageBitmap(null);
        Glide.with(mContext).load(item.getProfile_image_url()).into(holder.profile_image);
        holder.profile_name.setText(item.getProfile_name());
        holder.sender_rate_bar.setRating(item.getUser_rate());

        //Listeners..
        holder.request_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        holder.profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        holder.upper_info_part.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

