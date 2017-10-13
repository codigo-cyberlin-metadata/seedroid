package id.codigo.seedroid.view.activity;

import android.databinding.ViewDataBinding;

import id.codigo.seedroid.presenter.BasePresenter;
import id.codigo.seedroid.view.BaseView;
import id.codigo.seedroid.view.widget.BottomNavigationView;

/**
 * Created by papahnakal on 13/09/17.
 */

public class BaseBottomNavigationActivity<V extends BaseView, P extends BasePresenter<V>> extends BaseActivity<ViewDataBinding, V, P> implements BottomNavigationView.OnTabItemClickListener{
    @Override
    public int attachLayout() {
        return 0;
    }

    @Override
    public P createPresenter() {
        return null;
    }

    @Override
    public void onTabItemClick(int position) {

    }
}
