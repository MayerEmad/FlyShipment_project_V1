package adapters_and_items;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface theApiFunctions {
    @GET("shipInfo")
    Call<List<ShipmentItem>> get_api_shipments();

    @GET("travellerInfo")
    Call<List<TripItem>> get_api_trips();

    @GET("1001")
    Call<ProfileItem> get_api_userInfo();

}
