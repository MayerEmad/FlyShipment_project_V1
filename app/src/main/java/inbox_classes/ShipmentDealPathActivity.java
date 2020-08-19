package inbox_classes;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RatingBar;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.flyshippment_project.MyViewModel;
import com.example.flyshippment_project.R;
import com.example.flyshippment_project.Repository;

import java.util.ArrayList;
import java.util.List;

import adapters_and_items.ShipmentDealItem;

public class ShipmentDealPathActivity extends AppCompatActivity
{
    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipment_deal_path);

        Bundle extras = getIntent().getExtras();
        ShipmentDealItem dealItem = null;
        if(extras!=null && extras.containsKey("DEAL_ID")) {
            int  dealId=extras.getInt("DEAL_ID");
            ArrayList<ShipmentDealItem> deals = MyViewModel.getShipmentDealsLiveData().getValue();
            for(ShipmentDealItem deal : deals){
                if(deal.getDeal_id()==dealId){dealItem=deal; break;}
            }

        }

        final ArrayList<CheckBox> CheckBoxArray=new ArrayList<CheckBox>();

        CheckBox chb1 = (CheckBox) findViewById(R.id.shipment_deal_path_step_1);
        CheckBox chb2 = (CheckBox) findViewById(R.id.shipment_deal_path_step_2);
        CheckBox chb3 = (CheckBox) findViewById(R.id.shipment_deal_path_step_3);
        CheckBox chb4 = (CheckBox) findViewById(R.id.shipment_deal_path_step_4);
        CheckBox chb5 = (CheckBox) findViewById(R.id.shipment_deal_path_step_5);
        Button progressButton = (Button) findViewById(R.id.shipment_deal_path_progress_button);
        final Button rateButton=(Button)findViewById(R.id.shipment_deal_path_rate_button);
        final RatingBar rateBar=(RatingBar)findViewById(R.id.shipment_deal_path_rating_bar);
        CheckBoxArray.add(chb1);
        CheckBoxArray.add(chb2);
        CheckBoxArray.add(chb3);
        CheckBoxArray.add(chb4);
        CheckBoxArray.add(chb5);

        for(int i=0;i<5;i++)
            CheckBoxArray.get(i).setClickable(false);

        for(int i = 0; i< dealItem.getStatus_state(); i++){
            CheckBox chb=CheckBoxArray.get(i);
            chb.setChecked(true);
        }

        if(Repository.TheProfileItem.getUser_id()==dealItem.getSender_user_id()){
            progressButton.setText(R.string.confirm_word);
            if(dealItem.getStatus_state()!=4) progressButton.setEnabled(false);
        }
        else{
            progressButton.setText(R.string.next_step_word);
            if(dealItem.getStatus_state()>=4) progressButton.setEnabled(false);
        }

        if(dealItem.getStatus_state()==5)
        {
            rateBar.setVisibility(View.VISIBLE);
            rateBar.setRating((float) 2.5);
            rateButton.setVisibility(View.VISIBLE);
        }

        final ShipmentDealItem finalDealItem = dealItem;
        progressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Repository.moveStepShipmentDealPath(finalDealItem.getDeal_id(),getApplicationContext());
            }
        });

        rateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
               float rate=rateBar.getRating();
               int user_id;
               //sender means he send the request --> he will confirm receiving his own shipment so he rates the traveller
               if(Repository.TheProfileItem.getUser_id()==finalDealItem.getSender_user_id())
                    user_id=finalDealItem.getReceiver_user_id();
               else
                    user_id=finalDealItem.getSender_user_id();
               rateButton.setVisibility(View.INVISIBLE);
               Repository.sendShipmentDealRate(rate,user_id);
            }
        });

    }
}
