package id.codigo.seedroid_volley.configs;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Low on 11/1/17.
 */
public class RestConfigs {
    // authentication mode for rest api
    public static boolean isUsingBasicAuth = false;

    // value of REST config
    public static int requestTimeout = 50000;
    public static int requestRetryCount = 50000;

    // value of REST base url
    public static String rootUrl = "";

    // value of REST entity
    public static Map<String, String> defaultHeader = new HashMap<>();
    public static Map<String, String> defaultFormBody = new HashMap<>();

    // value of authentication basic auth
    public static String basicAuthValue = "";
}
