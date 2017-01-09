package id.codigo.seedroid.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Lukma on 3/29/2016.
 */
public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
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

    public static View getToolbarLogoIcon(Toolbar toolbar) {
        boolean hadContentDescription = android.text.TextUtils.isEmpty(toolbar.getLogoDescription());
        String contentDescription = String.valueOf(!hadContentDescription ? toolbar.getLogoDescription() : "logoContentDescription");
        toolbar.setLogoDescription(contentDescription);
        ArrayList<View> potentialViews = new ArrayList<>();
        toolbar.findViewsWithText(potentialViews, contentDescription, View.FIND_VIEWS_WITH_CONTENT_DESCRIPTION);
        View logoIcon = null;
        if (potentialViews.size() > 0) {
            logoIcon = potentialViews.get(0);
        }
        if (hadContentDescription)
            toolbar.setLogoDescription(null);
        return logoIcon;
    }
}
