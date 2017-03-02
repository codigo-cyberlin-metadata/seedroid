package id.codigo.seedroid.helper;

import android.app.Activity;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import id.codigo.seedroid.R;

/**
 * Created by Gayo on 2/13/2017.
 */

public class GaHelper {
    private Tracker tracker;

    private boolean isScreenNameByDefault = true;

    private String activityClassName = null;
    private String screenName = null;

    public void setScreenNameByDefault(boolean screenNameByDefault) {
        isScreenNameByDefault = screenNameByDefault;
    }

    public void setActivityClassName(String activityClassName) {
        this.activityClassName = activityClassName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public void init(Activity activity) {
        GoogleAnalytics analytics = GoogleAnalytics.getInstance(activity);
        tracker = analytics.newTracker(R.xml.global_tracker);

        if (isScreenNameByDefault) {
            screenName = (activity.getTitle() != null && activity.getTitle().length() > 0) ? activity.getTitle().toString() : activityClassName;
        }
    }

    public void captureScreen() {
        tracker.setScreenName(screenName);
        tracker.send(new HitBuilders.ScreenViewBuilder().build());
    }
}
