package id.codigo.seedroid.view.fragment;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import id.codigo.seedroid.R;
import id.codigo.seedroid.presenter.BasePresenter;
import id.codigo.seedroid.view.BaseView;
import id.codigo.seedroid.view.adapter.BaseRecyclerAdapter;
import id.codigo.seedroid.view.widget.CustomListProperties;
import id.codigo.seedroid.view.widget.CustomListView;

/**
 * Created by Lukma on 3/29/2016.
 */
public abstract class BaseRecyclerFragment<M, V extends BaseView, P extends BasePresenter<V>> extends BaseFragment<ViewDataBinding, V, P> implements
        CustomListView.CustomRecyclerListener {
    private CustomListProperties properties = new CustomListProperties();

    protected CustomListView<M> customListView;

    public CustomListProperties getProperties() {
        if (customListView == null) {
            return properties;
        } else {
            return customListView.getProperties();
        }
    }

    @Override
    public int attachLayout() {
        return R.layout.fragment_base_recycler;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        customListView = (CustomListView) rootView.findViewById(R.id.custom_list);
        customListView.init(properties, this);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (!isCreated) {
            isCreated = true;

            if (getUserVisibleHint()) {
                customListView.onRefreshItems();
            }
        } else {
            if (getUserVisibleHint() && customListView.getItems().size() == 0) {
                customListView.onRefreshItems();
            }
        }
    }

    @Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);

        if (visible && customListView != null && customListView.getItems().size() == 0) {
            customListView.onRefreshItems();
        }
    }

    public abstract BaseRecyclerAdapter onInitAdapter();

    public abstract void onLoadItems(int limit, int offset);
}
