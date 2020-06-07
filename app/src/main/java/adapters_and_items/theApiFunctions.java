package adapters_and_items;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface theApiFunctions {
    @GET("shipInfo")
    Call<List<ShipmentItem>> get_api_shipments();

    @GET("travellerInfo")
    Call<List<TripItem>> get_api_trips();

    @GET("userInfo/{id}")
    public Call<ProfileItem> get_api_userInfo(@Path("id") int id) ;

    @Multipart
    @POST("storeShip")
    Call<ResponseBody> uploadShipmentItem(
            @Part MultipartBody.Part image,
            @Part("itemName") RequestBody itemName,
            @Part("from_country") RequestBody from_country,
            @Part("to_country") RequestBody to_country,
            @Part("user_info_id") RequestBody user_info_id,
            @Part("deadline") RequestBody deadline,
            @Part("price") RequestBody price,
            @Part("weight") RequestBody weight,
            @Part("count") RequestBody count
    );

    @POST("storeTraveller")
    @FormUrlEncoded
    Call<TripItem> uploadTripItem(
            @Field("user_info_id")  String user_info_id,
            @Field("from_country")  String from_country,
            @Field("to_country")    String to_country,
            @Field("available_weight") String available_weight,
            @Field("date")     String date
    );
}
