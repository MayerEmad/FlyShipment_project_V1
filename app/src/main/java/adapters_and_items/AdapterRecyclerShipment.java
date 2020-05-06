package adapters_and_items;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.flyshippment_project.R;

import java.util.ArrayList;

public class AdapterRecyclerShipment extends RecyclerView.Adapter<AdapterRecyclerShipment.MyViewHolder>
{
    private ArrayList<ShipmentItem> ShipmentsList;

    // Provide a suitable constructor (depends on the kind of dataset)
    public AdapterRecyclerShipment(ArrayList<ShipmentItem> DataList)
    {
        ShipmentsList = DataList;
    }

    //ok
    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView product_name, country_from, country_to,profile_name,last_date,reward_text,weight_text;
        public ImageView product_image,profile_image;
        public Button request_btn;
        public RatingBar sender_rate_bar;
        public LinearLayout upper_info_part;

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
            upper_info_part=(LinearLayout)listItemView.findViewById(R.id.shipment_item_upper_part);
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
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ShipmentItem item = ShipmentsList.get(position);

        holder.product_image.setImageBitmap(item.getProduct_image());
        holder.weight_text.setText(item.getStrWeight());
        holder.product_name.setText(item.getProduct_name());
        holder.country_from.setText(item.getCountry_from());
        holder.country_to.setText(item.getCountry_to());
        holder.last_date.setText(item.getLast_date());
        holder.reward_text.setText(item.getStrReward());
        holder.profile_image.setImageBitmap(item.getProfile_image());
        holder.profile_name.setText(item.getProfile_name());
        holder.sender_rate_bar.setRating(item.getUserRate());

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
        if(ShipmentsList==null) return 0;
        return ShipmentsList.size();
    }

}

