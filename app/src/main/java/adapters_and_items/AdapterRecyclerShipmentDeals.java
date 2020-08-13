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

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.flyshippment_project.R;
import com.example.flyshippment_project.Repository;

import java.util.ArrayList;

import inbox_classes.ShipmentDealPathActivity;

public class AdapterRecyclerShipmentDeals extends RecyclerView.Adapter<AdapterRecyclerShipmentDeals.MyViewHolder> {
    private ArrayList<ShipmentDealItem> ShipmentsDealList;
    private Context mContext;
    private String parent;

    private void showProfile() {
    }

    public AdapterRecyclerShipmentDeals(ArrayList<ShipmentDealItem> list, Context mContext, String parent) {
        ShipmentsDealList = list;
        this.mContext = mContext;
        this.parent = parent;
    }

    @NonNull
    @Override
    public AdapterRecyclerShipmentDeals.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.shipment_deal_item, parent, false);
        MyViewHolder vh = new MyViewHolder(listItemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final ShipmentDealItem item = ShipmentsDealList.get(position);

        Glide.with(mContext).load(item.getProduct_image())
               // .skipMemoryCache(true)
                //.diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(holder.product_image);
        holder.weight_text.setText(item.getStrTotalWeight());
        holder.product_name.setText(item.getProduct_name());
        holder.country_from.setText(item.getCountry_from());
        holder.country_to.setText(item.getCountry_to());
        holder.last_date.setText(item.getDate());
        holder.reward_text.setText(String.valueOf(item.getItem_price()));

        if(item.getSender_user_id()!= Repository.TheProfileItem.getUser_id())
        {    //not me
            Glide.with(mContext).load(item.getSender_user_image()).into(holder.profile_image);
            holder.profile_name.setText(item.getSender_user_name());
            holder.sender_rate_bar.setRating(item.getSender_user_rate());
        }
        else
        {   // me
            Glide.with(mContext).load(item.getReceiver_user_image()).into(holder.profile_image);
            holder.profile_name.setText(item.getReceiver_user_name());
            holder.sender_rate_bar.setRating(item.getReceiver_user_rate());
        }

        //Listeners
        holder.profile_name.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) { showProfile(); }});
        holder.sender_rate_bar.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) { showProfile(); }});
        holder.profile_image.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) { showProfile(); }});

        holder.status_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(item.getStatus_state()==0){
                    Log.i("ShowDeal", "onClick: ---------------->Show Deal state");
                    Intent intent = new Intent(mContext, ShipmentDealPathActivity.class);
                    mContext.startActivity(intent);
                }
            }});
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView product_name, country_from, country_to,profile_name,last_date,reward_text,weight_text;
        public ImageView product_image,profile_image;
        public Button status_btn;
        public RatingBar sender_rate_bar;
        public ConstraintLayout upper_info_part;

        public MyViewHolder(View listItemView)
        {
            super(listItemView);
            product_image=(ImageView)listItemView.findViewById(R.id.shipment_deal_item_product_image);
            weight_text=(TextView)listItemView.findViewById(R.id.shipment_deal_item_product_weight);
            product_name=(TextView)listItemView.findViewById(R.id.shipment_deal_item_name);
            country_from =(TextView)listItemView.findViewById(R.id.shipment_deal_item_countery_from);
            country_to =(TextView)listItemView.findViewById(R.id.shipment_deal_item_countery_to);
            last_date=(TextView)listItemView.findViewById(R.id.shipment_deal_item_date);
            reward_text=(TextView)listItemView.findViewById(R.id.shipment_deal_item_reward);
            profile_image=(ImageView)listItemView.findViewById(R.id.shipment_deal_item_profile_img);
            profile_name=(TextView)listItemView.findViewById(R.id.shipment_deal_item_profile_name);
            sender_rate_bar=(RatingBar)listItemView.findViewById(R.id.shipment_deal_item_rating_bar);
            status_btn=(Button)listItemView.findViewById(R.id.shipment_deal_item_status_btn);
            upper_info_part=(ConstraintLayout) listItemView.findViewById(R.id.shipment_deal_item_upper_part);
        }
    }

    @Override
    public int getItemCount() {
        if(ShipmentsDealList==null) return 0;
        return ShipmentsDealList.size();
    }

}
