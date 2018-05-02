package id.codigo.seedroid_uikit.sharedpreference;

import android.content.SharedPreferences;
import android.util.Log;

import id.codigo.seedroid_uikit.SEEDROIDUI;

/**
 * Created by papahnakal on 01/03/18.
 */

public class UiSharedPreference {
    private static final String TAG = UiSharedPreference.class.getSimpleName();
    private SharedPreferences sharedPreferences;
    private int maxStringValueLen = 1024000;

    public UiSharedPreference(String fileName) {
        this.sharedPreferences = SEEDROIDUI.getApplicationContext().getSharedPreferences(fileName, 0);
    }

    public String getString(String key, String defaultValue) {
        return this.sharedPreferences.getString(key.replaceAll("\n", "_").replaceAll("\r", "-"), defaultValue);
    }

    public int getInt(String key, int defaultValue) {
        return this.sharedPreferences.getInt(key, defaultValue);
    }

    public long getLong(String key, long defaultValue) {
        return this.sharedPreferences.getLong(key, defaultValue);
    }

    public float getFloat(String key, float defaultValue) {
        return this.sharedPreferences.getFloat(key, defaultValue);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return this.sharedPreferences.getBoolean(key, defaultValue);
    }

    private boolean isInputValid(String value) {
        return value == null?true:value.length() <= this.maxStringValueLen;
    }

    public void putString(String key, String value) {
        key = key.replaceAll("\n", "_").replaceAll("\r", "-");
        if(this.isInputValid(value)) {
            SharedPreferences.Editor editor = this.sharedPreferences.edit();
            editor.putString(key, value);
            editor.commit();
        } else {
            Log.e(TAG, "Store shared preference failed,key:" + key + ".Value is longer than " + this.maxStringValueLen + ".");
        }

    }

    public void putFloat(String key, float value) {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putFloat(key, value);
        editor.commit();
    }

    public void putInt(String key, int value) {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public void putLong(String key, long value) {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putLong(key, value);
        editor.commit();
    }

    public void putBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public void remove(String key) {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.remove(key);
        editor.commit();
    }

    public void clear() {
        SharedPreferences.Editor editor = this.sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    public int getMaxStringValueLen() {
        return this.maxStringValueLen;
    }

    public void setMaxStringValueLen(int maxStringValueLen) {
        this.maxStringValueLen = maxStringValueLen;
    }
}
