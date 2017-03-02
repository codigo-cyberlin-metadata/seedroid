package id.codigo.seedroid.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import id.codigo.seedroid.configs.ThirdPartyConfigs;
import id.codigo.seedroid.helper.GaHelper;
import id.codigo.seedroid.helper.GtmHelper;

/**
 * Created by Lukma on 3/29/2016.
 */
public class BaseActivity extends AppCompatActivity {
    protected GtmHelper gtmHelper = ThirdPartyConfigs.isUsingGtm ? new GtmHelper() : null;
    protected GaHelper gaHelper = ThirdPartyConfigs.isUsingGtm ? new GaHelper() : null;

    public BaseActivity() {
        if (gtmHelper != null) {
            gtmHelper.setActivityClassName(getClass().getSimpleName());
        }
        if (gaHelper != null) {
            gaHelper.setActivityClassName(getClass().getSimpleName());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (gtmHelper != null) {
            gtmHelper.init(this);
        }
        if (gaHelper != null) {
            gaHelper.init(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (gtmHelper != null) {
            gtmHelper.captureScreen();
        }
        if (gaHelper != null) {
            gaHelper.captureScreen();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBack();
        return true;
    }

    @Override
    public void onBackPressed() {
        onBack();
    }

    public void onBack() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count >= 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }
}
