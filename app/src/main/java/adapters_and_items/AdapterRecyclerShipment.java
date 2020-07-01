package adapters_and_items;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flyshippment_project.MainActivity;
import com.example.flyshippment_project.R;

import java.util.ArrayList;

import Shipments_Trips_classes.CreateShipmentItemActivity;
import Shipments_Trips_classes.CreateTripItemActivity;
import Shipments_Trips_classes.EditShipmentItemActivity;
import Shipments_Trips_classes.ShipmentNavFragment;
import Shipments_Trips_classes.TripNavFragment;
import login_rejester_splash.APIManager;
import login_rejester_splash.RespnseModel;
import more_classes.EditProfilePageActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import search_classes.Shipment_Shower_Freg;

public class AdapterRecyclerShipment extends RecyclerView.Adapter<AdapterRecyclerShipment.MyViewHolder>
{
    private ArrayList<ShipmentItem> ShipmentsList;
    private Context mContext;
    private String parent;
    // Provide a suitable constructor (depends on the kind of dataset)
    public AdapterRecyclerShipment(ArrayList<ShipmentItem> DataList,Context con,String dad)
    {
        ShipmentsList = DataList;
        mContext=con;
        parent=dad;
    }

    //ok
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
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final ShipmentItem item = ShipmentsList.get(position);

        Glide.with(mContext).load(item.getProduct_image()).into(holder.product_image);
        holder.weight_text.setText(item.getStrWeight());
        holder.product_name.setText(item.getProduct_name());
        holder.country_from.setText(item.getCountry_from());
        holder.country_to.setText(item.getCountry_to());
        holder.last_date.setText(item.getLast_date());
        holder.reward_text.setText(String.valueOf(item.getReward()));
        Glide.with(mContext).load(item.getProfile_image()).into(holder.profile_image);
        holder.profile_name.setText(item.getProfile_name());
        holder.sender_rate_bar.setRating(item.getUserRate());

        //Listeners..
        holder.request_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("this", "onClick: clicked"+ item.getCountry_from());

                TripNavFragment fragobj = new TripNavFragment();
                int i = fragobj.getdata();
                Log.i("TAG", "onClick: id" + fragobj.getdata());

                APIManager.getInstance().getAPI().request(i,8 )
                        .enqueue(new Callback<RespnseModel>() {
                            @Override
                            public void onResponse(Call<RespnseModel> call, Response<RespnseModel> response) {


                                if (response.isSuccessful()) {



                                    RespnseModel msg = response.body();

                                    Toast.makeText(mContext,"Done", Toast.LENGTH_LONG).show();


                                }
                            }

                            @Override
                            public void onFailure(Call<RespnseModel> call, Throwable t) {

                                Toast.makeText(mContext, "Failed",
                                        Toast.LENGTH_LONG).show();


                            }
                        });
                /*Intent intent = new Intent(mContext, MainActivity.class);
                intent .putExtra("openTripNav",true);

                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
               // intent.putExtra("shipid",item.getShipment_id());
                mContext.startActivity(intent);
*/

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

                if(parent.equals("shipment_freg"))
                {
                    //go to show the ShipmentItem
                    Intent intent =new Intent(mContext, CreateShipmentItemActivity.class);
                    mContext.startActivity(intent);
                }
                else if(parent.equals("shipment_shower_freg")){
                    //go to Edit the shipmentItem
                    Intent intent =new Intent(mContext, EditShipmentItemActivity.class);
                    intent.putExtra("ShipmentItemPosition",position);
                    mContext.startActivity(intent);
                }
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

