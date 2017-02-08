package id.codigo.seedroid.configs;

/**
 * Created by Lukma on 3/29/2016.
 */
public class RestConfigs {
    // authentication mode for rest api
    public static boolean isUsingBasicAuth = false;
    public static boolean isUsingJWT = false;
    public static boolean isUsingCodigoAuth = false;

    // rest api authentication config
    public static int requestTimeout = 50000;
    public static int requestRetryCount = 50000;

    // end point of rest api
    public static String rootUrl = "";
    public static String refreshTokenUrl = "";

    // default parameter for Codigo authentication
    public static String apiIdParameter = "api_id";
    public static String apiKeyParameter = "api_key";
    public static String apiSecretParameter = "api_secret";
    public static String userIdParameter = "user_id";
    public static String userAccessTokenParameter = "user_access_token";

    // value of authentication basic auth
    public static String basicAuthValue = "";

    // value of authentication ums
    public static String apiIdValue = "";
    public static String apiKeyValue = "";
    public static String apiSecretValue = "";
}
