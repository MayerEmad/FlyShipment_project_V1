package adapters_and_items;

import android.annotation.SuppressLint;
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

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.flyshippment_project.MainActivity;
import com.example.flyshippment_project.R;
import com.example.flyshippment_project.Repository;

import java.util.ArrayList;

import Shipments_Trips_classes.CreateTripItemActivity;
import inbox_classes.Deals_Inbox_Frag;

public class AdapterRecyclerShipmentRequests extends RecyclerView.Adapter<AdapterRecyclerShipmentRequests.MyViewHolder>
{
    private ArrayList<ShipmentRequestItem> ShipmentsRequestList;
    private Context mContext;
    private String parent;
    // Provide a suitable constructor (depends on the kind of dataset)
    public AdapterRecyclerShipmentRequests(ArrayList<ShipmentRequestItem> DataList, Context con, String dad)
    {
        ShipmentsRequestList = DataList;
        mContext=con;
        parent=dad;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView product_name, country_from, country_to,profile_name,last_date,reward_text,weight_text;
        public ImageView product_image,profile_image;
        public Button status_btn,approve_btn,reject_btn;
        public RatingBar sender_rate_bar;
        public ConstraintLayout upper_info_part;

        public MyViewHolder(View listItemView)
        {
            super(listItemView);
            product_image=(ImageView)listItemView.findViewById(R.id.shipment_request_item_product_image);
            weight_text=(TextView)listItemView.findViewById(R.id.shipment_request_item_product_weight);
            product_name=(TextView)listItemView.findViewById(R.id.shipment_request_item_name);
            country_from =(TextView)listItemView.findViewById(R.id.shipment_request_item_countery_from);
            country_to =(TextView)listItemView.findViewById(R.id.shipment_request_item_countery_to);
            last_date=(TextView)listItemView.findViewById(R.id.shipment_request_item_date);

            reward_text=(TextView)listItemView.findViewById(R.id.shipment_request_item_reward);
            profile_image=(ImageView)listItemView.findViewById(R.id.shipment_request_item_profile_img);
            profile_name=(TextView)listItemView.findViewById(R.id.shipment_request_item_profile_name);
            sender_rate_bar=(RatingBar)listItemView.findViewById(R.id.shipment_request_item_rating_bar);
            status_btn=(Button)listItemView.findViewById(R.id.shipment_request_item_status_btn);
            approve_btn=(Button)listItemView.findViewById(R.id.shipment_request_item_approve_btn);
            reject_btn=(Button)listItemView.findViewById(R.id.shipment_request_item_reject_btn);
            upper_info_part=(ConstraintLayout) listItemView.findViewById(R.id.shipment_request_item_upper_part);
        }
    }

    @NonNull
    @Override
    public AdapterRecyclerShipmentRequests.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.shipment_request_item, parent, false);
        MyViewHolder vh = new MyViewHolder(listItemView);
        return vh;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull final AdapterRecyclerShipmentRequests.MyViewHolder holder, int position) {
        final ShipmentRequestItem item = ShipmentsRequestList.get(position);

        Glide.with(mContext).load(item.getProduct_image())
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(holder.product_image);
        holder.weight_text.setText(item.getStrTotalWeight());
        holder.product_name.setText(item.getProduct_name());
        holder.country_from.setText(item.getCountry_from());
        holder.country_to.setText(item.getCountry_to());
        holder.last_date.setText(item.getDate());
        holder.reward_text.setText(String.valueOf(item.getItem_price()));

        if(item.getSender_user_id()!=Repository.TheProfileItem.getUser_id())
        {    //not me
            Glide.with(mContext).load(item.getSender_user_image()).into(holder.profile_image);
            holder.profile_name.setText(item.getSender_user_name());
            holder.sender_rate_bar.setRating(item.getSender_user_rate());
            holder.approve_btn.setVisibility(View.VISIBLE);
            holder.reject_btn.setVisibility(View.VISIBLE);
            holder.status_btn.setVisibility(View.INVISIBLE);
        }
        else
        {   // me
            Glide.with(mContext).load(item.getReceiver_user_image()).into(holder.profile_image);
            holder.profile_name.setText(item.getReceiver_user_name());
            holder.sender_rate_bar.setRating(item.getReceiver_user_rate());
            holder.status_btn.setVisibility(View.VISIBLE);
            holder.approve_btn.setVisibility(View.INVISIBLE);
            holder.reject_btn.setVisibility(View.INVISIBLE);
        }

        if(parent.equals("shipment_inbox_fragment")){
            if(item.getApproving_state()==1){
                holder.status_btn.setText(R.string.go_to_deals_text);
                holder.status_btn.setBackgroundColor(R.drawable.bushape);
            }
            else if(item.getApproving_state()==-1){
                holder.status_btn.setText(R.string.remove_from_request_text);
                holder.status_btn.setBackgroundColor(R.drawable.bushape6);
            }
        }

        //Listeners
        holder.profile_name.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) { showProfile(); }});
        holder.sender_rate_bar.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) { showProfile(); }});
        holder.profile_image.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) { showProfile(); }});

        holder.approve_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Repository.approveShipmentRequest(item.getRequest_id());
                Repository.getShipmentRequestsFromApi();
            }});
        holder.reject_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Repository.rejectShipmentRequest(item.getRequest_id());
                Repository.getShipmentRequestsFromApi();
            }});
        holder.status_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if(item.getApproving_state()==0){
                  Log.i("testApprove", "onClick: ---------------->do no thing");
                  /*Fragment fragment = new Deals_Inbox_Frag();
                  FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                  FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                  fragmentTransaction.replace(R.id.content_frame, fragment);
                  fragmentTransaction.addToBackStack(null);
                  fragmentTransaction.commit();*/
              }
              else if(item.getApproving_state()==1){
                 //FIXME got to Deals
              }
              else if(item.getApproving_state()==-1){

                  Repository.afterRejectShipmentRequest(item.getRequest_id());
                  Log.i("Api", "onClick:------> "+item.getRequest_id());
              }
            }});
    }

    private void showProfile() {

    }

    @Override
    public int getItemCount() {
        if(ShipmentsRequestList==null) return 0;
        return ShipmentsRequestList.size();
    }
}
