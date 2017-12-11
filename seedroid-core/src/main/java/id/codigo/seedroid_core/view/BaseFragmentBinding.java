package id.codigo.seedroid_core.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import id.codigo.seedroid_core.delegate.RootDelegateBinding;
import id.codigo.seedroid_core.presenter.BasePresenter;

/**
 * Created by papahnakal on 25/10/17.
 */

public abstract class BaseFragmentBinding<B extends ViewDataBinding, V extends BaseView, P extends BasePresenter<V>> extends Fragment implements RootDelegateBinding<B, V, P>, BaseView {
    private B viewBinding;
    private P mvpPresenter;

    protected boolean isCreated = false;
    private String title = getClass().getSimpleName();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Integer resourceStyle = attachStyle();
        if (resourceStyle != null) {
            final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), resourceStyle);
            inflater = inflater.cloneInContext(contextThemeWrapper);
        }

        viewBinding = DataBindingUtil.inflate(inflater, attachLayout(), container, false);
        return viewBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getMvpPresenter().onStartUI();
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

        return mvpPresenter;
    }

    @Override
    public B getViewBinding() {
        return viewBinding;
    }

    public Integer attachStyle() {
        return null;
    }
}
