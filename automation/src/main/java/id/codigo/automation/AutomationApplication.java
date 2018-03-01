package id.codigo.automation;

import android.app.Application;

import id.codigo.seedroid_uikit.SEEDROIDUI;

/**
 * Created by papahnakal on 01/03/18.
 */

public class AutomationApplication extends Application {
    AutomationApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        SEEDROIDUI.init(this);
    }
}
