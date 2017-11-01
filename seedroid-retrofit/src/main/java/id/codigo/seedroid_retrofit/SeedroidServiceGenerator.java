package id.codigo.seedroid_retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by papahnakal on 31/10/17.
 */

public class SeedroidServiceGenerator {
    private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    private static OkHttpClient httpClient = new OkHttpClient();
    //private static Builder clientBuilder = new Builder();
    private static Retrofit retrofit;

/**
 * Created service without header and authorization
 */
    public static <S> S createService(String HOST,Class<S> serviceClass) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(HOST)
                .addConverterFactory(GsonConverterFactory.create());
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();
        retrofit = builder.client(httpClient).build();
        return retrofit.create(serviceClass);
    }
/**
 * Created service with header and authorization
 */
    public static <S> S createService(String HOST,Class<S> serviceClass, final SeedroidSessionManager sessionManager) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(HOST)
                .addConverterFactory(GsonConverterFactory.create(gson));
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();

                        Request.Builder requestBuilder = original.newBuilder()
                                .header("Authorization", sessionManager.getAutorization())
                                .header("KLASIO-API-KEY", "12345")
                                .header("Content-Type", "application/x-www-form-urlencoded")
                                .method(original.method(), original.body());

                        Request request = requestBuilder.build();

                        return chain.proceed(request);
                    }
                })
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();

        retrofit = builder.client(httpClient).build();
        return retrofit.create(serviceClass);
    }

    public static <S> S createServiceArray(String HOST,Class<S> serviceClass, final SeedroidSessionManager sessionManager) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(HOST)
                .addConverterFactory(GsonConverterFactory.create(gson));
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        Request.Builder requestBuilder = original.newBuilder()
                                .header("Authorization", sessionManager.getAutorization())
                                .header("KLASIO-API-KEY", "12345")
                                .header("Content-Type", "application/x-www-form-urlencoded")
                                .method(original.method(), original.body());

                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                    }
                })
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();

        retrofit = builder.client(httpClient).build();
        return retrofit.create(serviceClass);
    }

    /*public static void setBuilder(String HOST) {
        builder = new Retrofit.Builder()
                .baseUrl(HOST)
                .addConverterFactory(GsonConverterFactory.create());
    }*/
}
