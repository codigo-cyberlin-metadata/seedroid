package id.codigo.seedroid_volley;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

/**
 * Created by Low on 11/1/17.
 */

public class SeedroidVolleyApplication extends MultiDexApplication {
    private static SeedroidVolleyApplication instance;

    public static synchronized SeedroidVolleyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

//        Realm.init(this);
//        RealmConfiguration config = new RealmConfiguration
//                .Builder()
//                .deleteRealmIfMigrationNeeded()
//                .build();
//        Realm.setDefaultConfiguration(config);

        instance = this;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}