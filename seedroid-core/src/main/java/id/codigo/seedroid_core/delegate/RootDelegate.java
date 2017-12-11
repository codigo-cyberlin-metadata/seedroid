package id.codigo.seedroid_core.delegate;

import android.databinding.ViewDataBinding;

import id.codigo.seedroid_core.presenter.BasePresenter;
import id.codigo.seedroid_core.view.BaseView;

/**
 * Created by papahnakal on 11/12/17.
 */

public interface RootDelegate <V extends BaseView, P extends BasePresenter> {
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
}
