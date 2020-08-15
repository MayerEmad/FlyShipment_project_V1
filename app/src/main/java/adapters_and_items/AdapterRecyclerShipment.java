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
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.flyshippment_project.R;
import com.example.flyshippment_project.Repository;

import java.util.ArrayList;

import Shipments_Trips_classes.CreateShipmentItemActivity;
import Shipments_Trips_classes.EditShipmentItemActivity;


public class AdapterRecyclerShipment extends RecyclerView.Adapter<AdapterRecyclerShipment.MyViewHolder>
{
    private ArrayList<ShipmentItem> ShipmentsList;
    private Context mContext;
    private String parent;
    private int tripId;
    // Provide a suitable constructor (depends on the kind of dataset)
    public AdapterRecyclerShipment(ArrayList<ShipmentItem> DataList,Context con,String dad,int trip_id)
    {
        ShipmentsList = DataList;
        mContext=con;
        parent=dad;
        tripId=trip_id;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView product_name, country_from, country_to,profile_name,last_date,reward_text,weight_text;
        public ImageView product_image,profile_image;
        public Button request_btn;
        public RatingBar sender_rate_bar;
        public ConstraintLayout upper_info_part;
        public MyViewHolder(View listItemView)
        {
            super(listItemView);
            product_image=(ImageView)listItemView.findViewById(R.id.shipment_item_product_image);
            weight_text=(TextView)listItemView.findViewById(R.id.shipment_item_product_weight);
            product_name=(TextView)listItemView.findViewById(R.id.shipment_item_name);
            country_from =(TextView)listItemView.findViewById(R.id.shipment_item_countery_from);
            country_to =(TextView)listItemView.findViewById(R.id.shipment_item_countery_to);
            last_date=(TextView)listItemView.findViewById(R.id.shipment_item_date);

            reward_text=(TextView)listItemView.findViewById(R.id.shipment_item_reward);
            profile_image=(ImageView)listItemView.findViewById(R.id.shipment_item_profile_img);
            profile_name=(TextView)listItemView.findViewById(R.id.shipment_item_profile_name);
            sender_rate_bar=(RatingBar)listItemView.findViewById(R.id.shipment_item_rating_bar);
            request_btn=(Button)listItemView.findViewById(R.id.shipment_item_request_btn);
            upper_info_part=(ConstraintLayout) listItemView.findViewById(R.id.shipment_item_upper_part);
        }
    }
    // ok Create new views (invoked by the layout manager)
    @Override
    public AdapterRecyclerShipment.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View listItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.shipment_item, parent, false);
        MyViewHolder vh = new MyViewHolder(listItemView);
        return vh;
    }

    // ok Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position)
    {
        final ShipmentItem item = ShipmentsList.get(position);

        Glide.with(mContext).load(item.getProduct_image())
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(holder.product_image);
        holder.weight_text.setText(item.getStrTotalWeight());
        holder.product_name.setText(item.getProduct_name());
        holder.country_from.setText(item.getCountry_from());
        holder.country_to.setText(item.getCountry_to());
        holder.last_date.setText(item.getLast_date());
        holder.reward_text.setText(String.valueOf(item.getItemPrice()));
        Glide.with(mContext).load(item.getProfile_image()).into(holder.profile_image);
        holder.profile_name.setText(item.getProfile_name());
        holder.sender_rate_bar.setRating(item.getUserRate());

        if(parent.equals("AdapterRecyclerTripParent"))
        {
            holder.request_btn.setText(R.string.add_to_trip_text);
            if(item.getIsEditable()==-1)
            {
                holder.request_btn.setText(R.string.request_sent_text);
                holder.request_btn.setEnabled(false);
            }
            if(item.getIsEditable()==1)
            {
                holder.request_btn.setText(R.string.not_uoloaded_text);
                holder.request_btn.setEnabled(false);
            }
        }
        else if(parent.equals("shipment_shower_freg")){
            holder.request_btn.setText("next version");
            holder.request_btn.setEnabled(false);
        }
        else holder.request_btn.setVisibility(View.INVISIBLE);

        //Listeners..
        holder.request_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(parent.equals("AdapterRecyclerTripParent")){
                    //FIXME validate shipment size and trip available weight
                    Repository.sendRequestForTrip(item.getShipment_id(),tripId);
                    item.setIsEditable(-1);
                    Repository.getUserShipmentsFromApi();
                    holder.request_btn.setText(R.string.request_sent_text);
                    holder.request_btn.setEnabled(false);
                    // Log.i("requestTest", "onRequestButton-->AddToTrip----------> ship_id= " +item.getShipment_id()+"trip_id= "+tripId);
                }
            }
        });
        holder.profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go to profile
            }
        });
        holder.upper_info_part.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(parent.equals("shipment_nav_fragment"))
                {
                    //go to Edit the shipmentItem
                    Intent intent =new Intent(mContext, EditShipmentItemActivity.class);
                    intent.putExtra("ShipmentItemPosition",position);
                    mContext.startActivity(intent);
                }
                else if(parent.equals("shipment_shower_freg") ||parent.equals("AdapterRecyclerTripParent")){
                    //go to show the ShipmentItem
                    Intent intent =new Intent(mContext, CreateShipmentItemActivity.class);
                    mContext.startActivity(intent);
                }
            }
        });
    }

    // ok Return the size of your dataSet (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if(ShipmentsList==null) return 0;
        return ShipmentsList.size();
    }

}

