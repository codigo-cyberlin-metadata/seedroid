package id.codigo.seedroid_retrofit.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by papahnakal on 08/01/18.
 */

public class SeedroidServiceGenerator {
    private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    private static OkHttpClient httpClient = new OkHttpClient();
    //private static Builder clientBuilder = new Builder();
    private static Retrofit retrofit;

    public static <S> S create(String HOST, Class<S> serviceClass) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .registerTypeAdapterFactory(new ItemTypeAdapterFactory())
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

    public static <S> S create(final List<Header> headers, String HOST, Class<S> serviceClass) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .registerTypeAdapterFactory(new ItemTypeAdapterFactory())
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
                        Request.Builder requestBuilder = original.newBuilder();
                        if(headers.size()>0){
                            for (int i = 0; i < headers.size(); i++) {
                                requestBuilder.header(headers.get(i).getKey(),headers.get(i).getValue());
                            }
                        }
                        requestBuilder.method(original.method(), original.body());
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
}
