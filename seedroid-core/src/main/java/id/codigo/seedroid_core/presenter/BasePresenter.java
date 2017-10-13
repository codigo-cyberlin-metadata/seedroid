package id.codigo.seedroid_core.presenter;

import id.codigo.seedroid_core.view.BaseView;

/**
 * Created by Gayo on 5/9/2017.
 */
public abstract class BasePresenter<V extends BaseView> {
    private V mvpView;

    /**
     * Function that called first after create UI
     */
    public abstract void onStartUI();

    /**
     * Getter mvpView variable
     */
    public V getMvpView() {
        return mvpView;
    }

    /**
     * Setter mvpView variable
     */
    public void setMvpView(V mvpView) {
        this.mvpView = mvpView;
    }
}
