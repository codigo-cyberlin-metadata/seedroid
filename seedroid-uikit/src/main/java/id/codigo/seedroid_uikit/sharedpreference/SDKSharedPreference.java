package id.codigo.seedroid_uikit.sharedpreference;

/**
 * Created by papahnakal on 01/03/18.
 */

public class SDKSharedPreference extends UiSharedPreference{
    private static SDKSharedPreference sdkSharePreferenceUtil = null;

    private SDKSharedPreference() {
        super("SDK_SharedPreference");
    }

    public static SDKSharedPreference getInstance() {
        if(null == sdkSharePreferenceUtil) {
            sdkSharePreferenceUtil = new SDKSharedPreference();
        }

        return sdkSharePreferenceUtil;
    }

    public interface Key {
        String EXTRA_RAN = "extra_ran";
        String EXTRA_RAN_I = "extra_ran_i";
        String U_K_FLAG = "_U_K_FLAG";
        String U_IV_FLAG = "_U_IV_FLAG";
        String NEED_USE_RESOURCE_PKG = "NEED_USE_RESOURCE_PKG";
        String RESOURCE_PATH = "RESOURCE_PATH";
        String APP_CURRENT_LANGUAGE = "APP_CURRENT_LANGUAGE";
        String PURE_APP_VERSION_KEY = "PURE_APP_VERSION_KEY";
        String RESOURCE_PACKAGE_VERSION_KEY = "RESOURCE_PACKAGE_VERSION_KEY";
        String NTP_DOMAIN = "ntp_domain";
        String NTP_BACKUP_DOMAIN = "ntp_backup_domain";
    }
}
