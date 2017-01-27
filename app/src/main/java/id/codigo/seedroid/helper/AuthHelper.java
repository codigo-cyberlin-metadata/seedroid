package id.codigo.seedroid.helper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Lukma on 5/2/2016.
 */
public class AuthHelper {
    private static final String KEY_IS_AUTHENTICATED = "is_authenticated";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USER_ACCESS_TOKEN = "user_access_token";
    private static final String KEY_USER_ACCESS_TOKEN_EXPIRED = "user_access_token_expired";

    /**
     * Check status user logging
     */
    public static boolean isAuthenticated() {
        return PreferenceHelper.getInstance().getSessionBoolean(KEY_IS_AUTHENTICATED);
    }

    /**
     * Set user is logging
     */
    public static void login() {
        PreferenceHelper.getInstance().saveSession(KEY_IS_AUTHENTICATED, true);
    }

    /**
     * Set user is not logging
     */
    public static void logout() {
        PreferenceHelper.getInstance().clear();
    }

    /**
     * Save user id at shared preference
     *
     * @param value URL of the request to make
     */
    public static void saveUserId(String value) {
        PreferenceHelper.getInstance().saveSession(KEY_USER_ID, value);
    }

    /**
     * Save user access token at shared preference
     *
     * @param value URL of the request to make
     */
    public static void saveUserAccessToken(String value) {
        PreferenceHelper.getInstance().saveSession(KEY_USER_ACCESS_TOKEN, value);
    }

    /**
     * Save user access token expired at shared preference
     *
     * @param value URL of the request to make
     */
    public static void saveUserAccessTokenExpired(String value) throws Exception {
        long expiredDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).parse(value).getTime();

        PreferenceHelper.getInstance().saveSession(KEY_USER_ACCESS_TOKEN_EXPIRED, expiredDate);
    }

    /**
     * Get user id from shared preference
     */
    public static String getUserId() {
        return PreferenceHelper.getInstance().getSessionString(KEY_USER_ID);
    }

    /**
     * Get user access token from shared preference
     */
    public static String getUserAccessToken() {
        return PreferenceHelper.getInstance().getSessionString(KEY_USER_ACCESS_TOKEN);
    }

    /**
     * Check is user access token is expired
     */
    public static boolean isUserAccessTokenExpired() {
        Date currentDate = new Date();
        Date expireDate = new Date(PreferenceHelper.getInstance().getSessionLong(KEY_USER_ACCESS_TOKEN_EXPIRED));

        return currentDate.before(expireDate);
    }
}
