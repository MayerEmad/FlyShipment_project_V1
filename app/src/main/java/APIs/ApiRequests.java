package APIs;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.flyshippment_project.Repository;

import java.util.List;

import adapters_and_items.RequestItem;
import adapters_and_items.ShipmentItem;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiRequests extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    public void ShipmentAskForTrip(int shipment_id, int tripId)
    {
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("https://originaliereny.com/shipping/public/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        theApiFunctions client=retrofit.create(theApiFunctions.class);
        Call<RequestItem> call = client.sendRequestFormShipmentToTrip(new RequestItem(shipment_id,tripId));
        call.enqueue(new Callback<RequestItem>() {
            @Override
            public void onResponse(Call<RequestItem> call, Response<RequestItem> response) {
                if (!response.isSuccessful())
                    Log.i("ApiRequest", "Response has error = "+response.message()+" code = "+response.code());
                else
                    Log.i("ApiRequest", "request to trip is sent successfully = "+response.message());
            }

            @Override
            public void onFailure(Call<RequestItem> call, Throwable t) {
                Log.i("ApiRequest", "failed to send request to trip\n"+
                        t.getLocalizedMessage()+"\n"+
                        t.getCause()+"\n"+
                        t.getCause());
            }
        });
    }
}
