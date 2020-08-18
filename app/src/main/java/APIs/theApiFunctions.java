package APIs;

import java.util.List;

import adapters_and_items.ProfileItem;
import adapters_and_items.RequestItem;
import adapters_and_items.ShipmentDealItem;
import adapters_and_items.ShipmentItem;
import adapters_and_items.ShipmentRequestItem;
import adapters_and_items.TripItem;
import login_rejester_splash.RespnseModel;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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
            @Path("user_info_id") int id,
            @Part MultipartBody.Part image,
            @Part("phone") RequestBody phone,
            @Part("identification") RequestBody identification,
            @Part("fullName") RequestBody fullName
    );

    @POST("user_update/{user_info_id}")
    Call<ProfileItem> updateUserInfoNoImage(@Path("user_info_id") int itemId,@Body ProfileItem profileItem);

    //-------------------- Shipment ----------------------------
    @GET("ship_info")
    Call<List<ShipmentItem>> get_api_shipments();

    // userShipment
    @GET("ship_user/{user_info_id}")
    Call<List<ShipmentItem>> get_user_shipments(@Path("user_info_id") int id);

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
            @Part("count") RequestBody count,
            @Part("editable") RequestBody isEditable
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
            @Part("count") RequestBody count,
            @Part("editable") RequestBody isEditable

    );
    @POST("ship_update/{ship_info_id}")
    Call<ShipmentItem> updateShipmentItemNoImage(@Path("ship_info_id") int itemId,@Body ShipmentItem shipmentItem);

    @POST("ship_del/{ship_info_id}")
    Call<ShipmentItem> deleteShipmentItem(@Path("ship_info_id") int itemId);

    //-------------------- Trip ----------------------------

    @GET("traveller_info")
    Call<List<TripItem>> get_api_trips();

    // user trips
    @GET("traveller_user/{user_id}")
    Call<List<TripItem>> get_user_trips(@Path("user_id") int user_id);

    @POST("traveller_store")
    Call<TripItem> uploadTripItem(@Body TripItem tripItem);

    @POST("traveller_update/{traveller_info_id}")
    Call<TripItem> updateTripItem(@Path("traveller_info_id") int itemId,@Body TripItem tripItem);

    @POST("traveller_del/{traveller_info_id}")
    Call<TripItem> deleteTripItem(@Path("traveller_info_id") int itemId);

    //-------------------requests--------------------
    @POST("request")
    Call<RequestItem> sendRequestFormShipmentToTrip(@Body RequestItem r);

    @GET("request/{user_id}")
    Call<List<ShipmentRequestItem>> get_shipments_requests(@Path("user_id") int user_id);

    @POST("request/approved/{request_id}")
    Call<ShipmentRequestItem> approve_shipments_request(@Path("request_id") int request_id);

    @POST("request/not_approved/{request_id}")
    Call<ShipmentRequestItem> reject_shipments_request(@Path("request_id") int request_id);

    @GET("request/del/{request_id}")
    Call<ShipmentRequestItem> remove_rejected_shipment_from_request(@Path("request_id") int request_id);

    //-------------------deals--------------------
    @GET("deal/{user_id}")
    Call<List<ShipmentDealItem>> get_shipments_deals(@Path("user_id")  int user_id);

    @POST("status/{deal_id}")
    Call<ShipmentDealItem> move_step_shipment_deal_path(@Path("deal_id")  int deal_id);

    @POST("rate/{user_id}")
    @FormUrlEncoded
    Call<ShipmentDealItem> send_shipment_deal_rate(@Path("user_id") int user_id , @Field("rate") float rate);

    //---------------Rejester-----------------
    @POST("register")
    @FormUrlEncoded
    public Call<RespnseModel> register(@Field("email") String email, @Field("userName") String name,
                                       @Field("password") String pass, @Field("fullName") String fullname
    ) ;
}
