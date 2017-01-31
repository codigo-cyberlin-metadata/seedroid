package id.codigo.seedroid.configs;

/**
 * Created by Lukma on 3/29/2016.
 */
public class RestConfigs {
    // authentication mode for rest api
    public static boolean isUsingBasicAuth = false;
    public static boolean isUsingJWT = false;
    public static boolean isUsingUms = false;

    // value of authentication basic auth
    public static String basicAuth = "";

    // value of authentication ums
    public static String umsAppId = "";
    public static String umsAppKey = "";
    public static String umsAppSecret = "";

    // rest api authentication config
    public static int requestTimeout = 50000;
    public static int requestRetryCount = 50000;

    // end point of rest api
    public static String rootUrl = "";
    public static String refreshTokenUrl = "";

    // default parameter for authentication
    public static String appIdUrlParameter = "api_id";
    public static String appKeyUrlParameter = "api_key";
    public static String appSecretUrlParameter = "api_secret";
    public static String userIdUrlParameter = "user_id";
    public static String userAccessTokenUrlParameter = "user_access_token";
}
