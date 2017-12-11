package id.codigo.seedroid_core.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import id.codigo.seedroid_core.delegate.RootDelegate;
import id.codigo.seedroid_core.presenter.BasePresenter;

/**
 * Created by papahnakal on 11/12/17.
 */

public abstract class BaseActivity<V extends BaseView, P extends BasePresenter<V>> extends AppCompatActivity implements RootDelegate<V, P>, BaseView {
    private P mvpPresenter;

    public BaseActivity() {

    }

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

    @Override
    public V getMvpView() {
        return (V) this;
    }

    @Override
    public P getMvpPresenter() {
        if (mvpPresenter == null) {
            mvpPresenter = createPresenter();
            mvpPresenter.setMvpView(getMvpView());
        }

        return this.mvpPresenter;
    }
}
