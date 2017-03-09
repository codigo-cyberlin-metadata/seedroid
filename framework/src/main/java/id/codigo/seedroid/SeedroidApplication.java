package id.codigo.seedroid;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import io.realm.Realm;

/**
 * Created by Lukma on 3/29/2016.
 */
public class SeedroidApplication extends MultiDexApplication {
    private static SeedroidApplication instance;

    public static synchronized SeedroidApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);

        instance = this;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
