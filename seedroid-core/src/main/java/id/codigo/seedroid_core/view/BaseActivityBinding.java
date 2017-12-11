package id.codigo.seedroid_core.view;

import android.os.Bundle;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.app.AppCompatActivity;

import id.codigo.seedroid_core.delegate.RootDelegateBinding;
import id.codigo.seedroid_core.presenter.BasePresenterBinding;

/**
 * Created by papahnakal on 25/10/17.
 */

public abstract class BaseActivityBinding<B extends ViewDataBinding, V extends BaseView, P extends BasePresenterBinding<V>> extends AppCompatActivity implements RootDelegateBinding<B, V, P>, BaseView {
    private B viewBinding;
    private P mvpPresenter;

    public BaseActivityBinding() {

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
