package login_rejester_splash;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIFunctions {
    @POST("register?")
    public Call<RespnseModel> register(@Query("email") String email, @Query("userName") String name,
                                                @Query("password") String pass, @Query("fullName") String fullname


    ) ;

    @POST("login?")
    public Call<RespnseModel> login(@Query("email") String email,
                                          @Query("password") String pass
    ) ;
    @POST("request?")
    public Call<RespnseModel> request(@Query("traveller_id") int traveller_id, @Query("ship_id") int ship_id



    ) ;
}
