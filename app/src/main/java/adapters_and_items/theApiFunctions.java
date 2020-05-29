package adapters_and_items;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
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
    @POST("/api/Pharmarcy/UploadImage")
    Call<ResponseBody> uploadShipmentItem(
            @Part("itemName") RequestBody itemName,
            @Part("from_country") RequestBody from_country,
            @Part("to_country") RequestBody to_country,
            @Part("user_name") RequestBody user_name,
            @Part("deadline") RequestBody deadline,
            @Part("user_rate") RequestBody user_rate,
            @Part("price") RequestBody price,
            @Part("weight") RequestBody weight,
            @Part("count") RequestBody count,

            @Part MultipartBody.Part prod_image,
            @Part MultipartBody.Part user_image
    );
}
