package APIs;

import java.util.List;

import adapters_and_items.ProfileItem;
import adapters_and_items.ShipmentItem;
import adapters_and_items.TripItem;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface theApiFunctions {

    //-------------------- profile ----------------------------
    @GET("user_info/{id}")
    Call<ProfileItem> get_api_userInfo(@Path("id") int id) ;

    @Multipart
    @POST("user_update/{user_info_id}")
    Call<ProfileItem> updateUserInfoItem(
            @Path("user_info_id") int itemId,
            @Part MultipartBody.Part image,
            @Part("email") RequestBody email,
            @Part("phone") RequestBody phone,
            @Part("identification") RequestBody identification,
            @Part("fullName") RequestBody fullName,
            @Part("userName") RequestBody Name
    );

    //-------------------- Shipment ----------------------------
    @GET("ship_info")
    Call<List<ShipmentItem>> get_api_shipments();

    @Multipart
    @POST("ship_store")
    Call<ShipmentItem> uploadShipmentItem(
            @Part MultipartBody.Part image,
            @Part("itemName") RequestBody itemName,
            @Part("from_country") RequestBody from_country,
            @Part("to_country") RequestBody to_country,
            @Part("user_info_id") RequestBody user_info_id,
            @Part("deadline") RequestBody deadline,
            @Part("url") RequestBody productUrl,
            @Part("price") RequestBody price,
            @Part("weight") RequestBody weight,
            @Part("count") RequestBody count
    );

    @Multipart
    @POST("ship_update/{ship_info_id}")
    Call<ShipmentItem> updateShipmentItem(
            @Path("ship_info_id") int itemId,
            @Part MultipartBody.Part image,
            @Part("itemName") RequestBody itemName,
            @Part("from_country") RequestBody from_country,
            @Part("to_country") RequestBody to_country,
            @Part("user_info_id") RequestBody user_info_id,
            @Part("deadline") RequestBody deadline,
            @Part("url") RequestBody productUrl,
            @Part("price") RequestBody price,
            @Part("weight") RequestBody weight,
            @Part("count") RequestBody count
    );

    @POST("ship_del/{ship_info_id}")
    Call<ShipmentItem> deleteShipmentItem(@Path("ship_info_id") int itemId);


    //-------------------- Trip ----------------------------

    @GET("traveller_info")
    Call<List<TripItem>> get_api_trips();

    @POST("traveller_store")
    Call<TripItem> uploadTripItem(@Body TripItem tripItem);

    @POST("traveller_update/{traveller_info_id}")
    Call<TripItem> updateTripItem(@Path("traveller_info_id") int itemId,@Body TripItem tripItem);

    @POST("traveller_del/{traveller_info_id}")
    Call<TripItem> deleteTripItem(@Path("traveller_info_id") int itemId);
}
