package id.codigo.seedroid_uikit.sdkutils;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.util.Locale;

import id.codigo.seedroid_uikit.SEEDROIDUI;
import id.codigo.seedroid_uikit.sharedpreference.SDKSharedPreference;
import id.codigo.seedroid_uikit.sharedpreference.UiSharedPreference;

/**
 * Created by papahnakal on 01/03/18.
 */

public class ResourceUtils {
    private static final String TAG = ResourceUtils.class.getSimpleName();
    private static String colorPath;
    private static String stringPath;
    private static String messagePath;
    private static String imagePath;
    private static String buildPath;
    private static final UiSharedPreference uiSharedPreference = new UiSharedPreference("UI_SharedPreference");

    public ResourceUtils() {
    }

    public static String getCurrentLanguage() {
        return getLanguage();
    }

    public static void setNeedLoadResource() {
        String needUseResourcePkg = getNeedUseResourcePkg();
        if(null != needUseResourcePkg && "ResourceDownLoaded".equals(needUseResourcePkg)) {
            setNeedUseResourcePkg("ResourceDownLoadedNeedLoad");
        }

    }

    public static void setUserSelectedLanguage() {
        String currentLanguage = getLanguage();
        Log.d(TAG, "currentLanguage = " + currentLanguage);
        Locale locale = new Locale(currentLanguage);
        Locale.setDefault(locale);
        Resources resource = SEEDROIDUI.getApplicationContext().getResources();
        Configuration config = resource.getConfiguration();
        config.locale = new Locale(currentLanguage);
        resource.updateConfiguration(config, resource.getDisplayMetrics());
    }

    public static String getLanguage() {
        return uiSharedPreference.getString("APP_CURRENT_LANGUAGE", "en");
    }

    public static void setLanguage(String language) {
        uiSharedPreference.putString("APP_CURRENT_LANGUAGE", language);
    }

    public static boolean isNeedLoadResource() {
        boolean isNeedLoadResource = false;
        String needUseResourcePkg = getNeedUseResourcePkg();
        if(null != needUseResourcePkg && "ResourceDownLoadedNeedLoad".equals(needUseResourcePkg)) {
            isNeedLoadResource = true;
        }

        return isNeedLoadResource;
    }

    public static void setPureAppVersion(String version) {
        SDKSharedPreference.getInstance().putString("PURE_APP_VERSION_KEY", version);
    }

    public static String getPureAppVersion() {
        return SDKSharedPreference.getInstance().getString("PURE_APP_VERSION_KEY", "");
    }

    public static void setResourcePkgVersion(float version) {
        SDKSharedPreference.getInstance().putFloat("RESOURCE_PACKAGE_VERSION_KEY", version);
    }

    public static float getResourcePkgVersion() {
        return SDKSharedPreference.getInstance().getFloat("RESOURCE_PACKAGE_VERSION_KEY", 0.0F);
    }

    public static void setNeedUseResourcePkg(String needUseResourcePkg) {
        SDKSharedPreference.getInstance().putString("NEED_USE_RESOURCE_PKG", needUseResourcePkg);
    }

    public static String getNeedUseResourcePkg() {
        return SDKSharedPreference.getInstance().getString("NEED_USE_RESOURCE_PKG", (String)null);
    }

    public static void setResourcePath(String pureResourcePkgName) {
        SDKSharedPreference.getInstance().putString("RESOURCE_PATH", pureResourcePkgName);
    }

    public static String getResourcePath() {
        return SDKSharedPreference.getInstance().getString("RESOURCE_PATH", (String)null);
    }

    public static String getAppVersionFromResourceConfig(String version) {
        String appVersion = null;
        if(!TextUtils.isEmpty(version) && version.matches("([0-9]+.){2}[0-9]+(_*[0-9]{12})?")) {
            String peelUnderline = version;
            int index = version.indexOf("_");
            if(-1 != index) {
                peelUnderline = version.substring(0, index);
            }

            String[] splits = peelUnderline.split("\\.");
            if(splits.length >= 3) {
                appVersion = splits[0] + "." + splits[1] + "." + splits[2];
            }
        }

        return appVersion;
    }

    public static float getResourceNumericVersion(String version) {
        float resourcePkgVersionFloat = 0.0F;
        if(!TextUtils.isEmpty(version) && version.matches("[0-9]+(.[0-9]+)?")) {
            try {
                resourcePkgVersionFloat = Float.parseFloat(version);
            } catch (NumberFormatException var3) {
                Log.e(TAG, var3.toString());
            }
        }

        return resourcePkgVersionFloat;
    }

    public static String getVersionFromFilePath(String path) {
        String fileNameWithoutSuffix = "";
        String pathSplit = File.separatorChar == 92?"\\\\":File.separator;
        String[] split = path.split(pathSplit);
        if(0 < split.length) {
            String fileName = split[split.length - 1];
            if(!TextUtils.isEmpty(fileName)) {
                int index = fileName.lastIndexOf(".");
                if(-1 != index) {
                    fileNameWithoutSuffix = fileName.substring(0, index);
                } else {
                    fileNameWithoutSuffix = fileName;
                }
            }
        }

        return fileNameWithoutSuffix;
    }

    public static String getColorPath() {
        return TextUtils.isEmpty(colorPath)?"/color":colorPath;
    }

    public static void setColorPath(String colorPath) {
        if(TextUtils.isEmpty(colorPath)) {
            colorPath = "/color";
        }

        colorPath = colorPath;
    }

    public static String getStringPath() {
        return TextUtils.isEmpty(stringPath)?"/string":stringPath;
    }

    public static void setStringPath(String stringPath) {
        if(TextUtils.isEmpty(stringPath)) {
            stringPath = "/string";
        }

        stringPath = stringPath;
    }

    public static String getMessagePath() {
        return TextUtils.isEmpty(messagePath)?"/message":messagePath;
    }

    public static void setMessagePath(String messagePath) {
        if(TextUtils.isEmpty(messagePath)) {
            messagePath = "/message";
        }

        messagePath = messagePath;
    }

    public static String getImagePath() {
        if(TextUtils.isEmpty(imagePath)) {
            String type;
            if(DeviceInfo.isPad()) {
                type = "Pad";
            } else {
                type = "Phone";
            }

            return "/image/Android" + File.separator + type;
        } else {
            return imagePath;
        }
    }

    public static void setImagePath(String imagePath) {
        String type;
        if(DeviceInfo.isPad()) {
            type = "Pad";
        } else {
            type = "Phone";
        }

        if(TextUtils.isEmpty(imagePath)) {
            imagePath = "/image/Android" + File.separator + type;
        } else {
            imagePath = imagePath + "/Android" + File.separator + type;
        }

    }

    public static String getBuildPath() {
        return TextUtils.isEmpty(buildPath)?"/build":buildPath;
    }

    public static void setBuildPath(String buildPath) {
        if(TextUtils.isEmpty(buildPath)) {
            buildPath = "/build";
        }

        buildPath = buildPath;
    }
}
