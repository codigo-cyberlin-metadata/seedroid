package id.codigo.seedroid_retrofit.service;

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

public class SeedroidServiceGenerator1 {
    private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    private static OkHttpClient httpClient = new OkHttpClient();
    //private static Builder clientBuilder = new Builder();
    private static Retrofit retrofit;

    /**
     * make call service with auth based on content-type and key header if needed
     * keyHeader : Authorization, token, x-access-token, x Authorization,
     * contenttype : application/json or application/x-www-form-urlencoded
     */
    public static <S> S create(final String contentType, String HOST, Class<S> serviceClass) {
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
                        Request.Builder requestBuilder = original.newBuilder();
                        if (contentType.equalsIgnoreCase("")){
                            requestBuilder.header("Content-Type", contentType);
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

    /**
     * make call service with auth based on content-type and key header if needed
     * keyHeader : Authorization, token, x-access-token, x Authorization,
     * contenttype : application/json or application/x-www-form-urlencoded
     */
    public static <S> S create(final String keyAuth, final String contentType, String HOST, Class<S> serviceClass, final SeedroidSessionManager sessionManager) {
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
                        Request.Builder requestBuilder = original.newBuilder();
                        if (!keyAuth.equalsIgnoreCase("")) {
                            requestBuilder.header(keyAuth, sessionManager.getAutorization());
                        }
                        if (!contentType.equalsIgnoreCase("")){
                            requestBuilder.header("Content-Type", contentType);
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

/**
 * make call service without authorization
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
 * Created service with header form json
 * Content-type : application/json
 */
    public static <S> S createServiceJson(String HOST,Class<S> serviceClass) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(HOST)
                .addConverterFactory(GsonConverterFactory.create());
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();

                        Request.Builder requestBuilder = original.newBuilder()
                                .header("Content-Type", "application/json")
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
/**
 * Created service with header form urlencode
 * Content-type : application/x-www-form-urlencoded
 */
    public static <S> S createServiceForm(String HOST,Class<S> serviceClass) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(HOST)
                .addConverterFactory(GsonConverterFactory.create());
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();

                        Request.Builder requestBuilder = original.newBuilder()
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
/**
 * Created service with header form urlencode and authorization
 * Content-type : application/x-www-form-urlencoded
 * Authorization : auth
 */
    public static <S> S createServiceAuthForm(String HOST,Class<S> serviceClass, final SeedroidSessionManager sessionManager) {
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
/**
 * Created service with header form json and authorization
 * Content-type : application/json
 * Authorization : auth
 */
    public static <S> S createServiceAuthJson(String HOST,Class<S> serviceClass, final SeedroidSessionManager sessionManager) {
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
                                .header("Content-Type", "application/json")
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
/**
 * Created service with header form urlencode and token
 * Content-type : application/x-www-form-urlencoded
 * token : auth
 */
    public static <S> S createServiceTokenForm(String HOST,Class<S> serviceClass, final SeedroidSessionManager sessionManager) {
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
                                .header("token", sessionManager.getAutorization())
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
/**
 * Created service with header form json and token
 * Content-type : application/json
 * token : auth
 */
    public static <S> S createServiceTokenJson(String HOST,Class<S> serviceClass, final SeedroidSessionManager sessionManager) {
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
                                .header("token", sessionManager.getAutorization())
                                .header("Content-Type", "application/json")
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
