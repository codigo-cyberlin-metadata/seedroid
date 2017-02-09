package id.codigo.seedroid.helper;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadStatusDelegate;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import id.codigo.seedroid.R;
import id.codigo.seedroid.SeedroidApplication;
import id.codigo.seedroid.configs.RestConfigs;
import id.codigo.seedroid.model.json.auth.BaseAuthModel;
import id.codigo.seedroid.service.BaseAuthService;
import id.codigo.seedroid.service.ServiceListener;

/**
 * Created by Lukma on 3/29/2016.
 */
public class HttpHelper {
    private static final String TAG = HttpHelper.class.getSimpleName();

    private static HttpHelper instance;

    private RequestQueue requestQueue;
    private HashMap<String, String> httpHeader = new HashMap<>();
    private RetryPolicy retryPolicy = new RetryPolicy() {
        @Override
        public int getCurrentTimeout() {
            return RestConfigs.requestTimeout;
        }

        @Override
        public int getCurrentRetryCount() {
            return RestConfigs.requestRetryCount;
        }

        @Override
        public void retry(VolleyError error) throws VolleyError {
            Log.e(TAG, error.getMessage() + "");
        }
    };

    private HttpHelper() {
        requestQueue = getRequestQueue();

        if (RestConfigs.isUsingBasicAuth) {
            httpHeader.put("Authorization", RestConfigs.basicAuthValue);
        }
    }

    public static synchronized HttpHelper getInstance() {
        if (instance == null) {
            instance = new HttpHelper();
        }
        return instance;
    }

    /**
     * Make a GET request and return a string
     *
     * @param url      URL of the request to make
     * @param headers  Additional http header of the request to make
     * @param listener Listener of the response from request
     */
    public <T> void get(String url, HashMap<String, String> headers, final ServiceListener<T> listener) {
        httpHeader.putAll(headers);
        get(url, listener);
    }

    /**
     * Make a GET request and return a string
     *
     * @param url      URL of the request to make
     * @param listener Listener of the response from request
     */
    public <T> void get(String url, final ServiceListener<T> listener) {
        Log.d(TAG, "request:" + url);
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        handleResponse(response, listener);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof NoConnectionError) {
                            listener.onFailed(SeedroidApplication.getInstance().getString(R.string.status_no_connection));
                        } else {
                            listener.onFailed(SeedroidApplication.getInstance().getString(R.string.status_failed));
                        }
                        Log.e(TAG, error.getMessage() + "");
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return httpHeader;
            }
        };
        request.setRetryPolicy(retryPolicy);
        addToRequestQueue(request);
    }

    /**
     * Make a POST request and return a string
     *
     * @param url        URL of the request to make
     * @param headers    Additional http header of the request to make
     * @param parameters Parameters of the request to make
     * @param listener   Listener of the response from request
     */
    public <T> void post(final String url, HashMap<String, String> headers, final Map<String, String> parameters, final ServiceListener<T> listener) {
        httpHeader.putAll(headers);
        post(url, parameters, listener);
    }

    /**
     * Make a POST request and return a string
     *
     * @param url        URL of the request to make
     * @param parameters Parameters of the request to make
     * @param listener   Listener of the response from request
     */
    public <T> void post(final String url, final Map<String, String> parameters, final ServiceListener<T> listener) {
        if (RestConfigs.isUsingCodigoAuth) {
            parameters.put(RestConfigs.apiIdParameter, RestConfigs.apiIdValue);
            parameters.put(RestConfigs.apiKeyParameter, RestConfigs.apiKeyValue);
            parameters.put(RestConfigs.apiSecretParameter, RestConfigs.apiSecretValue);

            if (AuthHelper.isAuthenticated()) {
                parameters.put(RestConfigs.userIdParameter, AuthHelper.getUserId());
                parameters.put(RestConfigs.userAccessTokenParameter, AuthHelper.getUserAccessToken());
            }
        }

        String requestHttpPost = "request:" + url;
        if (parameters.keySet().size() > 0) {
            requestHttpPost += "\nparameter:";

            for (String key : parameters.keySet()) {
                requestHttpPost += "\n- " + key + ":" + parameters.get(key) + ",";
            }
        }
        Log.d(TAG, requestHttpPost);

        if (RestConfigs.isUsingCodigoAuth && AuthHelper.isAuthenticated() && AuthHelper.isUserAccessTokenExpired()) {
            BaseAuthService.refreshToken(new ServiceListener<BaseAuthModel>() {
                @Override
                public void onSuccess(BaseAuthModel data) {
                    AuthHelper.saveUserAccessToken(data.userAccessToken);
                    AuthHelper.saveUserAccessTokenExpired(data.userAccessTokenExpired);
                    post(url, parameters, listener);
                }

                @Override
                public void onFailed(String message) {
                    AuthHelper.logout();
                    listener.onFailed(message);
                }
            });
        } else {
            StringRequest request = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            handleResponse(response, listener);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (error instanceof NoConnectionError) {
                                listener.onFailed(SeedroidApplication.getInstance().getString(R.string.status_no_connection));
                            } else {
                                listener.onFailed(SeedroidApplication.getInstance().getString(R.string.status_failed));
                            }
                            Log.e(TAG, error.getMessage() + "");
                        }
                    }
            ) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    return httpHeader;
                }

                @Override
                protected Map<String, String> getParams() {
                    return parameters;
                }
            };
            request.setRetryPolicy(retryPolicy);
            request.setTag(url);
            addToRequestQueue(request);
        }
    }

    /**
     * Make a POST multipart request
     *
     * @param url        URL of the request to make
     * @param parameters Parameters of the request to make
     * @param delegate   Listener of the response from request
     */
    public void postMultipart(String url, Map<String, String> parameters, UploadStatusDelegate delegate) {
        if (RestConfigs.isUsingCodigoAuth) {
            parameters.put(RestConfigs.apiIdParameter, RestConfigs.apiIdValue);
            parameters.put(RestConfigs.apiKeyParameter, RestConfigs.apiKeyValue);
            parameters.put(RestConfigs.apiSecretParameter, RestConfigs.apiSecretValue);

            if (AuthHelper.isAuthenticated()) {
                parameters.put(RestConfigs.userIdParameter, AuthHelper.getUserId());
                parameters.put(RestConfigs.userAccessTokenParameter, AuthHelper.getUserAccessToken());
            }
        }

        String requestHttpPost = "request:" + url;
        if (parameters.keySet().size() > 0) {
            requestHttpPost += "\nparameter:";

            for (String key : parameters.keySet()) {
                requestHttpPost += "\n-" + key.replace("file-", "") + ":" + parameters.get(key) + ",";
            }
        }
        Log.d(TAG, requestHttpPost);

        try {
            MultipartUploadRequest request = new MultipartUploadRequest(SeedroidApplication.getInstance(), UUID.randomUUID().toString(), url);

            if (RestConfigs.isUsingBasicAuth) {
                request.addHeader("Authorization", RestConfigs.basicAuthValue);
            }

            request.setDelegate(delegate);

            for (String key : parameters.keySet()) {
                if (key.startsWith("file-")) {
                    request.addFileToUpload(parameters.get(key), key.replace("file-", ""));
                } else {
                    request.addParameter(key, parameters.get(key));
                }
            }

            request.startUpload();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage() + "");
        }
    }

    private <T> void addToRequestQueue(Request<T> request) {
        getRequestQueue().add(request);
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(SeedroidApplication.getInstance());
        }
        return requestQueue;
    }

    private <T> void handleResponse(String response, ServiceListener<T> listener) {
        try {
            Class<T> valueType = null;

            Type[] genericInterfaces = listener.getClass().getGenericInterfaces();
            for (Type genericInterface : genericInterfaces) {
                if (genericInterface instanceof ParameterizedType) {
                    valueType = (Class<T>) ((ParameterizedType) genericInterface).getActualTypeArguments()[0];
                }
            }

            listener.onSuccess(JsonHelper.getInstance().toObject(response, valueType));
        } catch (Exception e) {
            listener.onFailed(SeedroidApplication.getInstance().getString(R.string.status_failed));
            Log.e(TAG, e.getMessage() + "");
        }
    }
}
