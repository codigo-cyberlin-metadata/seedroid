package id.codigo.seedroid_retrofit.service;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by papahnakal on 31/10/17.
 */

public class SeedroidSharedPreference implements SeedroidSessionManager {
    private SharedPreferences mPref;
    private SharedPreferences.Editor mEditor;
    private Context mContext;

    private static int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "SeedroidShared";
    private static final String KEY_AUTHORIZED = "userId";

    public SeedroidSharedPreference(Context context){
        this.mContext = context;
        this.mPref = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        this.mEditor = mPref.edit();
    }
    @Override
    public void setAuthorization(String authorization) {
        mEditor.putString(KEY_AUTHORIZED, authorization);
        mEditor.commit();
    }

    @Override
    public String getAutorization() {
        return mPref.getString(KEY_AUTHORIZED,"");
    }

    @Override
    public void clear() {
        mEditor.clear();
        mEditor.commit();
    }
}
