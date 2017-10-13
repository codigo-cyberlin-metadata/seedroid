package id.codigo.seedroid_core;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

/**
 * Created by papahnakal on 12/10/17.
 */

public class SeedroidCoreApplication extends MultiDexApplication {
    private static SeedroidCoreApplication instance;

    public static synchronized SeedroidCoreApplication getInstance() {
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
