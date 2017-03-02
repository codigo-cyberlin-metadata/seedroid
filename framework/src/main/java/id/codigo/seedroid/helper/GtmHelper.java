package id.codigo.seedroid.helper;

import android.app.Activity;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

/**
 * Created by Gayo on 2/13/2017.
 */
public class GtmHelper {
    private FirebaseAnalytics firebaseAnalytics;

    private boolean isScreenNameByDefault = true;
    private boolean isFirebaseAnalyticsItemByDefault = true;

    private String activityClassName = null;
    private String screenName = null;
    private Bundle bundle;

    public void setScreenNameByDefault(boolean screenNameByDefault) {
        isScreenNameByDefault = screenNameByDefault;
    }

    public void setFirebaseAnalyticsItemByDefault(boolean firebaseAnalyticsItemByDefault) {
        isFirebaseAnalyticsItemByDefault = firebaseAnalyticsItemByDefault;
    }

    public void setActivityClassName(String activityClassName) {
        this.activityClassName = activityClassName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public void init(Activity activity) {
        firebaseAnalytics = FirebaseAnalytics.getInstance(activity);

        if (isScreenNameByDefault) {
            screenName = (activity.getTitle() != null && activity.getTitle().length() > 0) ? activity.getTitle().toString() : activityClassName;
        }
    }

    public void captureScreen() {
        if (screenName != null) {
            if (isFirebaseAnalyticsItemByDefault) {
                bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, screenName);
            }

            if (bundle != null) {
                firebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM, bundle);
            }
        }
    }
}
