package id.codigo.seedroid.helper;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import id.codigo.seedroid.SeedroidApplication;

/**
 * Created by Lukma on 4/29/2016.
 */
public class PreferenceHelper {
    private static PreferenceHelper instance;
    private SharedPreferences preferences;

    public PreferenceHelper() {
        preferences = PreferenceManager.getDefaultSharedPreferences(SeedroidApplication.getInstance());
    }

    public static PreferenceHelper getInstance() {
        if (instance == null) {
            instance = new PreferenceHelper();
        }
        return instance;
    }

    /**
     * Save boolean value to shared preference
     *
     * @param key   Key to mapping value
     * @param value Value to save at shared preference
     */
    public void saveSession(String key, boolean value) {
        preferences.edit().putBoolean(key, value).apply();
    }

    /**
     * Save long value to shared preference
     *
     * @param key   Key to mapping value
     * @param value Value to save at shared preference
     */
    public void saveSession(String key, long value) {
        preferences.edit().putLong(key, value).apply();
    }

    /**
     * Save string value to shared preference
     *
     * @param key   Key to mapping value
     * @param value Value to save at shared preference
     */
    public void saveSession(String key, String value) {
        preferences.edit().putString(key, value).apply();
    }

    /**
     * Get boolean value from shared preference
     *
     * @param key Key to get value
     */
    public boolean getSessionBoolean(String key) {
        return preferences.getBoolean(key, false);
    }

    /**
     * Get long value from shared preference
     *
     * @param key Key to get value
     */
    public int getSessionLong(String key) {
        return preferences.getInt(key, 0);
    }

    /**
     * Get string value from shared preference
     *
     * @param key Key to get value
     */
    public String getSessionString(String key) {
        return preferences.getString(key, "");
    }

    /**
     * Clear all value at shared preference
     */
    public void clear() {
        preferences.edit().clear().apply();
    }
}
