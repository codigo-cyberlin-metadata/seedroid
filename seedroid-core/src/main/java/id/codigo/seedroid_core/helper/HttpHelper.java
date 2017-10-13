package id.codigo.seedroid_core.helper;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
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
import net.gotev.uploadservice.ServerResponse;
import net.gotev.uploadservice.UploadInfo;
import net.gotev.uploadservice.UploadStatusDelegate;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import id.codigo.seedroid_core.R;
import id.codigo.seedroid_core.SeedroidCoreApplication;
import id.codigo.seedroid_core.configs.RestConfigs;
import id.codigo.seedroid_core.service.ServiceListener;
import id.codigo.seedroid_core.service.ServiceMultipartListener;

/**
 * Created by Lukma on 3/29/2016.
 */
public class HttpHelper {
    private static final String TAG = HttpHelper.class.getSimpleName();

    private static HttpHelper instance;

    private Context context;
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
            LogHelper.e(TAG, error.getLocalizedMessage() + "");
        }
    };

    private HttpHelper() {
        context = SeedroidCoreApplication.getInstance();
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
     * Create Http GET request with additional headers
     *
     * @param url      URL of the request to make
     * @param headers  Additional http header of the request to make
     * @param callback Callback of the response from request
     */
    public <T> void get(String url, HashMap<String, String> headers, ServiceListener<T> callback) {
        httpHeader.putAll(headers);
        get(url, callback);
    }

    /**
     * Create Http GET request
     *
     * @param url      URL of the request to make
     * @param callback Callback of the response from request
     */
    public <T> void get(String url, final ServiceListener<T> callback) {
        LogHelper.i(TAG, "request : " + url);
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        handleResponse(response, callback);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof NoConnectionError) {
                            callback.onFailed(context.getString(R.string.status_no_connection));
                            LogHelper.e(TAG, error.getLocalizedMessage() + "");
                        } else {
                            try {
                                callback.onFailed(error.getLocalizedMessage());
                                callback.onFailed(error.getLocalizedMessage(), error.networkResponse.statusCode, new String(error.networkResponse.data, "UTF-8"));
                                LogHelper.e(TAG, error.getLocalizedMessage() + "");
                            } catch (UnsupportedEncodingException ex) {
                                LogHelper.e(TAG, ex.getLocalizedMessage() + "");
                            } catch (Exception e){
                                LogHelper.e(TAG, e.getLocalizedMessage() + "");
                            }
                        }
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return httpHeader;
            }
        };
        //request.setRetryPolicy(retryPolicy);
        request.setRetryPolicy(new DefaultRetryPolicy(this.retryPolicy.getCurrentTimeout() * 1000, this.retryPolicy.getCurrentRetryCount(), DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        addToRequestQueue(request);
    }

    /**
     * Create Http POST request with additional headers
     *
     * @param url        URL of the request to make
     * @param headers    Additional http header of the request to make
     * @param parameters Parameters of the request to make
     * @param callback   Callback of the response from request
     */
    public <T> void post(String url, HashMap<String, String> headers, Map<String, String> parameters, final ServiceListener<T> callback) {
        httpHeader.putAll(headers);
        post(url, parameters, callback);
    }

    /**
     * Create Http POST request
     *
     * @param url        URL of the request to make
     * @param parameters Parameters of the request to make
     * @param Callback   Callback of the response from request
     */
    public <T> void post(String url, Map<String, String> parameters, final ServiceListener<T> Callback) {
        final Map<String, String> httpParameter = generateHttpParameter(parameters);
        printHttpLog(url, httpHeader, parameters);

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        handleResponse(response, Callback);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof NoConnectionError) {
                            Callback.onFailed(context.getString(R.string.status_no_connection));
                            LogHelper.e(TAG, error.getLocalizedMessage() + "");
                        } else {
                            try {
                                Callback.onFailed(error.getLocalizedMessage());
                                Callback.onFailed(error.getLocalizedMessage(), error.networkResponse.statusCode, new String(error.networkResponse.data, "UTF-8"));
                                LogHelper.e(TAG, error.getLocalizedMessage() + "");
                            } catch (UnsupportedEncodingException ex) {
                                LogHelper.e(TAG, ex.getLocalizedMessage() + "");
                            } catch (Exception e){
                                LogHelper.e(TAG, e.getLocalizedMessage() + "");
                            }
                        }
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return httpHeader;
            }

            @Override
            protected Map<String, String> getParams() {
                return httpParameter;
            }
        };
        //request.setRetryPolicy(retryPolicy);
        request.setRetryPolicy(new DefaultRetryPolicy(this.retryPolicy.getCurrentTimeout() * 1000, this.retryPolicy.getCurrentRetryCount(), DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setTag(url);
        addToRequestQueue(request);
    }

    /**
     * Create Http POST request with additional headers
     *
     * @param url        URL of the request to make
     * @param headers    Additional http header of the request to make
     * @param parameters Parameters of the request to make
     * @param payload    Payload of the request to make
     * @param callback   Callback of the response from request
     */
    public <T> void post(String url, HashMap<String, String> headers, Map<String, String> parameters, JSONObject payload, ServiceListener<T> callback) {
        httpHeader.putAll(headers);
        post(url, parameters, payload, callback);
    }

    /**
     * Create Http POST request
     *
     * @param url        URL of the request to make
     * @param parameters Parameters of the request to make
     * @param payload    Payload of the request to make
     * @param callback   Callback of the response from request
     */
    public <T> void post(String url, Map<String, String> parameters, JSONObject payload, final ServiceListener<T> callback) {
        final Map<String, String> httpParameter = generateHttpParameter(parameters);
        printHttpLog(url, httpHeader, parameters);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, payload,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        handleResponse(response.toString(), callback);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (error instanceof NoConnectionError) {
                            callback.onFailed(context.getString(R.string.status_no_connection));
                            LogHelper.e(TAG, error.getLocalizedMessage() + "");
                        } else {
                            try {
                                callback.onFailed(error.getLocalizedMessage());
                                callback.onFailed(error.getLocalizedMessage(), error.networkResponse.statusCode, new String(error.networkResponse.data, "UTF-8"));
                                LogHelper.e(TAG, error.getLocalizedMessage() + "");
                            } catch (UnsupportedEncodingException ex) {
                                LogHelper.e(TAG, ex.getLocalizedMessage() + "");
                            } catch (Exception e){
                                LogHelper.e(TAG, e.getLocalizedMessage() + "");
                            }
                        }
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return httpHeader;
            }

            @Override
            protected Map<String, String> getParams() {
                return httpParameter;
            }
        };
        //request.setRetryPolicy(retryPolicy);
        request.setRetryPolicy(new DefaultRetryPolicy(this.retryPolicy.getCurrentTimeout() * 1000, this.retryPolicy.getCurrentRetryCount(), DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.setTag(url);
        addToRequestQueue(request);
    }

    /**
     * Create Http POST multipart request with additional headers
     *
     * @param url        URL of the request to make
     * @param headers    Additional http header of the request to make
     * @param parameters Parameters of the request to make
     * @param callback   Callback of the response from request
     */
    public void postMultipart(String url, HashMap<String, String> headers, Map<String, String> parameters, ServiceMultipartListener callback) {
        httpHeader.putAll(headers);
        postMultipart(url, parameters, callback);
    }

    /**
     * Create Http POST multipart request
     *
     * @param url        URL of the request to make
     * @param parameters Parameters of the request to make
     * @param callback   Callback of the response from request
     */
    public void postMultipart(String url, Map<String, String> parameters, final ServiceMultipartListener callback) {
        try {
            MultipartUploadRequest request = new MultipartUploadRequest(context, UUID.randomUUID().toString(), url);

            for (String key : httpHeader.keySet()) {
                request.addHeader(key, httpHeader.get(key));
            }

            request.setDelegate(new UploadStatusDelegate() {
                @Override
                public void onProgress(UploadInfo uploadInfo) {
                    callback.onProgress(uploadInfo.getProgressPercent(), uploadInfo.getUploadedBytes(), uploadInfo.getUploadRate());
                    LogHelper.i(TAG, "file : " + uploadInfo.getUploadId()
                            + "\nprogress : " + uploadInfo.getProgressPercent()
                            + "\nbyte     : " + uploadInfo.getUploadedBytes()
                            + "\nrate     : " + uploadInfo.getUploadRate());
                }

                @Override
                public void onError(UploadInfo uploadInfo, Exception exception) {
                    callback.onFailed(exception.getLocalizedMessage());
                    LogHelper.e(TAG, exception.getLocalizedMessage() + "");
                }

                @Override
                public void onCompleted(UploadInfo uploadInfo, ServerResponse serverResponse) {
                    handleResponse(serverResponse.getBodyAsString(), callback);
                }

                @Override
                public void onCancelled(UploadInfo uploadInfo) {
                    callback.onCanceled();
                    LogHelper.e(TAG, context.getString(R.string.status_canceled));
                }
            });

            Map<String, String> httpParameter = generateHttpParameter(parameters);

            for (String key : httpParameter.keySet()) {
                if (key.startsWith("file-")) {
                    request.addFileToUpload(httpParameter.get(key), key.replace("file-", ""));
                } else {
                    request.addParameter(key, httpParameter.get(key));
                }
            }

            printHttpLog(url, httpHeader, httpParameter);

            request.startUpload();
        } catch (Exception ex) {
            LogHelper.e(TAG, ex.getLocalizedMessage() + "");
        }
    }

    private <T> void addToRequestQueue(Request<T> request) {
        getRequestQueue().add(request);
    }

    private RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context);
        }
        return requestQueue;
    }

    private Map<String, String> generateHttpParameter(Map<String, String> parameters) {
        if (RestConfigs.defaultFormBody.size() > 0) {
            for (String key : RestConfigs.defaultFormBody.keySet()) {
                parameters.put(key, RestConfigs.defaultFormBody.get(key));
            }
        }
        return parameters;
    }

    private void printHttpLog(String url, HashMap<String, String> headers, Map<String, String> parameters) {
        String requestHttpPost = "request : " + url;
        if (headers.keySet().size() > 0) {
            requestHttpPost += "\nheaders : ";

            for (String key : headers.keySet()) {
                requestHttpPost += "\n-" + key + " : " + parameters.get(key) + ",";
            }
        }
        if (parameters.keySet().size() > 0) {
            requestHttpPost += "\nparameter : ";

            for (String key : parameters.keySet()) {
                requestHttpPost += "\n- " + key + " : " + parameters.get(key) + ",";
            }
        }
        LogHelper.i(TAG, requestHttpPost);
    }

    private <T> void handleResponse(String response, ServiceListener<T> callback) {
        try {
            Class<T> valueType = callback.getType();
            callback.onSuccess(JsonHelper.getInstance().toObject(response, valueType));
        } catch (Exception ex) {
            callback.onFailed(ex.getLocalizedMessage());
            LogHelper.e(TAG, ex.getLocalizedMessage() + "");
        }
    }

    private <T> void handleResponse(String response, ServiceMultipartListener<T> callback) {
        try {
            Class<T> valueType = callback.getType();
            callback.onSuccess(JsonHelper.getInstance().toObject(response, valueType));
        } catch (Exception ex) {
            callback.onFailed(ex.getLocalizedMessage());
            LogHelper.e(TAG, ex.getLocalizedMessage() + "");
        }
    }
}
