package id.codigo.seedroid.view.callback;

import android.databinding.ViewDataBinding;

import id.codigo.seedroid.presenter.BasePresenter;
import id.codigo.seedroid.view.BaseView;

/**
 * Created by Gayo on 5/9/2017.
 */

public interface BaseCallback<B extends ViewDataBinding, V extends BaseView, P extends BasePresenter> {
    /**
     * Function that return id of layout resource
     */
    int attachLayout();

    /**
     * Function that return new class of mvpPresenter
     */
    P createPresenter();

    /**
     * Getter mvpView variable
     */
    V getMvpView();

    /**
     * Getter mvpPresenter variable
     */
    P getMvpPresenter();

    /**
     * Getter ViewDataBinding variable
     */
    B getViewBinding();
}
