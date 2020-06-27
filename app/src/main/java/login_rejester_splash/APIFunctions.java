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
    public Call<LoginResponsemodel> login(@Query("email") String email,
                                          @Query("password") String pass
    ) ;
}
