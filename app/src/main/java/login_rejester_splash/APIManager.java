package login_rejester_splash;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIManager {
    private static APIManager mInstance;
    private static final String BASE_URL = "https://originaliereny.com/shipping/public/api/";
    private Retrofit mRetrofit;

    private APIManager(){

        // This interceptor for getting all the requests and responses in LOG
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        Interceptor headerAuth = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                okhttp3.Request request = chain.request();

                Headers headers = request.headers().newBuilder().build();
                request = request.newBuilder().headers(headers).build();

                return chain.proceed(request);
            }
        };

        // This interceptor for adding headers to all requests
        // remove this if you don't have headers


        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.readTimeout(60, TimeUnit.SECONDS);
        client.connectTimeout(60, TimeUnit.SECONDS);
        client.addInterceptor(headerAuth);
        client.addInterceptor(loggingInterceptor);

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client.build())
                .build();
    }

    public static APIManager getInstance(){
        if (mInstance == null) {
            mInstance = new APIManager();
        }
        return mInstance;
    }

    public APIFunctions getAPI(){
        return mRetrofit.create(APIFunctions.class);
    }
}
