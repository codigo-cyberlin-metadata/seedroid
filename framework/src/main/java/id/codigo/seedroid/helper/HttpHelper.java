package id.codigo.seedroid.helper;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadStatusDelegate;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import id.codigo.seedroid.R;
import id.codigo.seedroid.SeedroidApplication;
import id.codigo.seedroid.configs.RestConfigs;
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
            LogHelper.e(TAG, error.getMessage() + "");
        }
    };

    private HttpHelper() {
        requestQueue = getRequestQueue();

        if (RestConfigs.isUsingBasicAuth) {
            httpHeader.put("Authorization", RestConfigs.basicAuthValue);
        }

        if (RestConfigs.defaultHeader.size() > 0) {
            for (String key : RestConfigs.defaultHeader.keySet()) {
                httpHeader.put(key, RestConfigs.defaultHeader.get(key));
            }
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
        LogHelper.d(TAG, "request:" + url);
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
                            listener.onFailed(error.getLocalizedMessage());
                            try {
                                listener.onFailed(error.getLocalizedMessage(), error.networkResponse.statusCode, new String(error.networkResponse.data, "UTF-8"));
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                        LogHelper.e(TAG, error.getMessage() + "");
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
        if (RestConfigs.defaultFormBody.size() > 0) {
            for (String key : RestConfigs.defaultFormBody.keySet()) {
                parameters.put(key, RestConfigs.defaultFormBody.get(key));
            }
        }

        String requestHttpPost = "request:" + url;
        if (parameters.keySet().size() > 0) {
            requestHttpPost += "\nparameter:";

            for (String key : parameters.keySet()) {
                requestHttpPost += "\n- " + key + ":" + parameters.get(key) + ",";
            }
        }
        LogHelper.d(TAG, requestHttpPost);

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
                            listener.onFailed(error.getLocalizedMessage());
                            try {
                                listener.onFailed(error.getLocalizedMessage(), error.networkResponse.statusCode, new String(error.networkResponse.data, "UTF-8"));
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                        LogHelper.e(TAG, error.getMessage() + "");
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

    /**
     * Make a POST request and return a string
     *
     * @param url        URL of the request to make
     * @param headers    Additional http header of the request to make
     * @param parameters Parameters of the request to make
     * @param payload    Payload of the request to make
     * @param listener   Listener of the response from request
     */
    public <T> void post(final String url, HashMap<String, String> headers, final Map<String, String> parameters, JSONObject payload, final ServiceListener<T> listener) {
        httpHeader.putAll(headers);
        post(url, parameters, payload, listener);
    }

    /**
     * Make a POST request and return a string
     *
     * @param url        URL of the request to make
     * @param parameters Parameters of the request to make
     * @param payload    Payload of the request to make
     * @param listener   Listener of the response from request
     */
    public <T> void post(final String url, final Map<String, String> parameters, JSONObject payload, final ServiceListener<T> listener) {
        if (RestConfigs.defaultFormBody.size() > 0) {
            for (String key : RestConfigs.defaultFormBody.keySet()) {
                parameters.put(key, RestConfigs.defaultFormBody.get(key));
            }
        }

        String requestHttpPost = "request:" + url;
        if (parameters.keySet().size() > 0) {
            requestHttpPost += "\nparameter:";

            for (String key : parameters.keySet()) {
                requestHttpPost += "\n- " + key + ":" + parameters.get(key) + ",";
            }
        }
        LogHelper.d(TAG, requestHttpPost);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, payload,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        handleResponse(response.toString(), listener);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof NoConnectionError) {
                            listener.onFailed(SeedroidApplication.getInstance().getString(R.string.status_no_connection));
                        } else {
                            listener.onFailed(error.getLocalizedMessage());
                            try {
                                listener.onFailed(error.getLocalizedMessage(), error.networkResponse.statusCode, new String(error.networkResponse.data, "UTF-8"));
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                        LogHelper.e(TAG, error.getMessage() + "");
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

    /**
     * Make a POST multipart request
     *
     * @param url        URL of the request to make
     * @param headers    Additional http header of the request to make
     * @param parameters Parameters of the request to make
     * @param delegate   Listener of the response from request
     */
    public void postMultipart(String url, HashMap<String, String> headers, Map<String, String> parameters, UploadStatusDelegate delegate) {
        httpHeader.putAll(headers);
        postMultipart(url, parameters, delegate);
    }

    /**
     * Make a POST multipart request
     *
     * @param url        URL of the request to make
     * @param parameters Parameters of the request to make
     * @param delegate   Listener of the response from request
     */
    public void postMultipart(String url, Map<String, String> parameters, UploadStatusDelegate delegate) {
        if (RestConfigs.defaultFormBody.size() > 0) {
            for (String key : RestConfigs.defaultFormBody.keySet()) {
                parameters.put(key, RestConfigs.defaultFormBody.get(key));
            }
        }

        String requestHttpPost = "request:" + url;
        if (parameters.keySet().size() > 0) {
            requestHttpPost += "\nparameter:";

            for (String key : parameters.keySet()) {
                requestHttpPost += "\n-" + key.replace("file-", "") + ":" + parameters.get(key) + ",";
            }
        }
        LogHelper.d(TAG, requestHttpPost);

        try {
            MultipartUploadRequest request = new MultipartUploadRequest(SeedroidApplication.getInstance(), UUID.randomUUID().toString(), url);

            for (String key : httpHeader.keySet()) {
                request.addHeader(key, httpHeader.get(key));
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
            LogHelper.e(TAG, e.getMessage() + "");
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
            Class<T> valueType = listener.getType();
            listener.onSuccess(JsonHelper.getInstance().toObject(response, valueType));
        } catch (Exception e) {
            listener.onFailed(SeedroidApplication.getInstance().getString(R.string.status_failed));
            LogHelper.e(TAG, e.getMessage() + "");
        }
    }
}
