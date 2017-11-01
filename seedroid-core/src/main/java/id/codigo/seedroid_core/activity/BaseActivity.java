package id.codigo.seedroid_core.activity;

import android.os.Bundle;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.app.AppCompatActivity;

import id.codigo.seedroid_core.delegate.RootDelegate;
import id.codigo.seedroid_core.presenter.BasePresenter;
import id.codigo.seedroid_core.view.BaseView;

/**
 * Created by papahnakal on 25/10/17.
 */

public abstract class BaseActivity <B extends ViewDataBinding, V extends BaseView, P extends BasePresenter<V>> extends AppCompatActivity implements RootDelegate<B, V, P>, BaseView {
    private B viewBinding;
    private P mvpPresenter;

    public BaseActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = DataBindingUtil.setContentView(this, attachLayout());
        getMvpPresenter().onStartUI();

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

    @Override
    public B getViewBinding() {
        return this.viewBinding;
    }
}
