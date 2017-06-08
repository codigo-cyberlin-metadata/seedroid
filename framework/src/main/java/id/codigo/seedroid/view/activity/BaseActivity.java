package id.codigo.seedroid.view.activity;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import id.codigo.seedroid.configs.ThirdPartyConfigs;
import id.codigo.seedroid.helper.GaHelper;
import id.codigo.seedroid.helper.GtmHelper;
import id.codigo.seedroid.presenter.BasePresenter;
import id.codigo.seedroid.view.BaseView;
import id.codigo.seedroid.view.delegate.RootDelegate;

/**
 * Created by Lukma on 3/29/2016.
 */
public abstract class BaseActivity<B extends ViewDataBinding, V extends BaseView, P extends BasePresenter<V>> extends AppCompatActivity implements RootDelegate<B, V, P>, BaseView {
    private B viewBinding;
    private P mvpPresenter;

    protected GtmHelper gtmHelper;
    protected GaHelper gaHelper;

    public BaseActivity() {
        if (ThirdPartyConfigs.isUsingGtm) {
            gtmHelper = new GtmHelper();
            gtmHelper.setActivityClassName(getClass().getSimpleName());
        }
        if (ThirdPartyConfigs.isUsingGa) {
            gaHelper = new GaHelper();
            gaHelper.setActivityClassName(getClass().getSimpleName());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = DataBindingUtil.setContentView(this, attachLayout());
        getMvpPresenter().onStartUI();

        if (ThirdPartyConfigs.isUsingGtm) {
            gtmHelper.init(this);
        }
        if (ThirdPartyConfigs.isUsingGa) {
            gaHelper.init(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (ThirdPartyConfigs.isUsingGtm) {
            gtmHelper.captureScreen();
        }
        if (ThirdPartyConfigs.isUsingGa) {
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

    @Override
    public B getViewBinding() {
        return this.viewBinding;
    }
}
