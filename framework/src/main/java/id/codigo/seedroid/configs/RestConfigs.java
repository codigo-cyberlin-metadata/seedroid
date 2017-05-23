package id.codigo.seedroid.configs;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lukma on 3/29/2016.
 */
public class RestConfigs {
    // authentication mode for rest api
    public static boolean isUsingBasicAuth = false;

    // rest api authentication config
    public static int requestTimeout = 50000;
    public static int requestRetryCount = 50000;

    // end point of rest api
    public static String rootUrl = "";

    // default parameter for Codigo authentication
    public static Map<String, String> defaultHeader = new HashMap<>();
    public static Map<String, String> defaultFormBody = new HashMap<>();

    // value of authentication basic auth
    public static String basicAuthValue = "";
}
