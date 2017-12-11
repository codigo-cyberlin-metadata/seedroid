package id.codigo.seedroid_volley.helper;

import android.content.Context;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Low on 11/1/17.
 */
public class AuthHelper {
    private static final String KEY_IS_AUTHENTICATED = "is_authenticated";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USER_ACCESS_TOKEN = "user_access_token";
    private static final String KEY_USER_ACCESS_TOKEN_EXPIRED = "user_access_token_expired";
    private static final String KEY_USER_SIGN_IN_VIA = "user_sign_in_via";
    private static final String KEY_USER_PASSWORD = "user_password";
    private static final String KEY_USER_BASIC_INFO = "user_basic_info";

    /**
     * Check status user logging
     */
    public static boolean isAuthenticated(Context ctx) {
        return PreferenceHelper.getInstance(ctx).getSessionBoolean(KEY_IS_AUTHENTICATED);
    }

    /**
     * Set user is logging
     */
    public static void login(Context ctx) {
        PreferenceHelper.getInstance(ctx).saveSession(KEY_IS_AUTHENTICATED, true);
    }

    /**
     * Set user is not logging
     */
    public static void logout(Context ctx) {
        PreferenceHelper.getInstance(ctx).clear();
    }

    /**
     * Save user id at shared preference
     *
     * @param value URL of the request to make
     */
    public static void saveUserId(Context ctx,String value) {
        PreferenceHelper.getInstance(ctx).saveSession(KEY_USER_ID, value);
    }

    /**
     * Save user access token at shared preference
     *
     * @param value URL of the request to make
     */
    public static void saveUserAccessToken(Context ctx,String value) {
        PreferenceHelper.getInstance(ctx).saveSession(KEY_USER_ACCESS_TOKEN, value);
    }

    /**
     * Save user sign in via at shared preference
     *
     * @param value URL of the request to make
     */
    public static void saveSignInVia(Context ctx,String value) {
        PreferenceHelper.getInstance(ctx).saveSession(KEY_USER_SIGN_IN_VIA, value);
    }

    /**
     * Save user password at shared preference
     *
     * @param value URL of the request to make
     */
    public static void savePassword(Context ctx,String value) {
        PreferenceHelper.getInstance(ctx).saveSession(KEY_USER_PASSWORD, value);
    }

    /**
     * Save user access token expired at shared preference
     *
     * @param value URL of the request to make
     */
    public static void saveUserAccessTokenExpired(Context ctx,String value) {
        try {
            long expiredDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).parse(value).getTime();

            PreferenceHelper.getInstance(ctx).saveSession(KEY_USER_ACCESS_TOKEN_EXPIRED, expiredDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Save user basic info at shared preference
     *
     * @param value URL of the request to make
     */
    public static <T> void saveUserBasicInfo(Context ctx,T value) {
        try {
            String jsonString = JsonHelper.getInstance().toString(value);
            PreferenceHelper.getInstance(ctx).saveSession(KEY_USER_BASIC_INFO, jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get user id from shared preference
     */
    public static String getUserId(Context ctx) {
        return PreferenceHelper.getInstance(ctx).getSessionString(KEY_USER_ID);
    }

    /**
     * Get user access token from shared preference
     */
    public static String getUserAccessToken(Context ctx) {
        return PreferenceHelper.getInstance(ctx).getSessionString(KEY_USER_ACCESS_TOKEN);
    }

    /**
     * Check is user access token is expired
     */
    public static boolean isUserAccessTokenExpired(Context ctx) {
        Date currentDate = new Date();
        Date expireDate = new Date(PreferenceHelper.getInstance(ctx).getSessionLong(KEY_USER_ACCESS_TOKEN_EXPIRED));

        return currentDate.after(expireDate);
    }

    /**
     * Get user sign in via from shared preference
     */
    public static String getSignInVia(Context ctx) {
        return PreferenceHelper.getInstance(ctx).getSessionString(KEY_USER_SIGN_IN_VIA);
    }

    /**
     * Get user password from shared preference
     */
    public static String getPassword(Context ctx) {
        return PreferenceHelper.getInstance(ctx).getSessionString(KEY_USER_PASSWORD);
    }

    /**
     * Get user basic info from shared preference
     */
    public static <T> T getUserBasicInfo(Context ctx, Class<T> valueType) {
        try {
            String jsonString = PreferenceHelper.getInstance(ctx).getSessionString(KEY_USER_BASIC_INFO);
            return JsonHelper.getInstance().toObject(jsonString, valueType);
        } catch (IOException e) {
            return null;
        }
    }
}
