package id.codigo.seedroid;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.crashlytics.android.Crashlytics;
import com.facebook.FacebookSdk;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import id.codigo.seedroid.configs.SocialConfigs;
import io.fabric.sdk.android.Fabric;

/**
 * Created by Lukma on 3/29/2016.
 */
public class ApplicationMain extends MultiDexApplication {
    private static ApplicationMain instance;

    public static synchronized ApplicationMain getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        FacebookSdk.sdkInitialize(this);

        TwitterAuthConfig authConfig = new TwitterAuthConfig(SocialConfigs.twitterConsumerKey, SocialConfigs.twitterConsumerSecret);
        Fabric.with(this, new Crashlytics(), new Twitter(authConfig));
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
