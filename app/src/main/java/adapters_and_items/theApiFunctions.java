package adapters_and_items;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface theApiFunctions {
    @GET("shipInfo")
    Call<List<ShipmentItem>> get_api_response_1();

    @GET("travellerInfo")
    Call<List<TripItem>> get_api_response();
}
