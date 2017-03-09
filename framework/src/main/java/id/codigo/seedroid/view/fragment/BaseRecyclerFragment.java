package id.codigo.seedroid.view.fragment;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import id.codigo.seedroid.R;
import id.codigo.seedroid.view.adapter.BaseRecyclerAdapter;
import id.codigo.seedroid.view.widget.CustomListProperties;
import id.codigo.seedroid.view.widget.CustomListView;
import id.codigo.seedroid.view.widget.SpacesItemDecoration;

/**
 * Created by Lukma on 3/29/2016.
 */
public abstract class BaseRecyclerFragment<T> extends BaseFragment implements
        AppBarLayout.OnOffsetChangedListener,
        CustomListView.CustomRecyclerListener {
    protected Integer customContentLayout = null;
    private CustomListProperties properties = new CustomListProperties();
    protected RecyclerView.ItemDecoration itemDecoration;

    protected AppBarLayout appBarLayout;
    protected CustomListView<T> customListView;

    public CustomListProperties getProperties() {
        if (customListView == null) {
            return properties;
        } else {
            return customListView.getProperties();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        appBarLayout = (AppBarLayout) getActivity().findViewById(R.id.app_bar_layout);

        View rootView;
        if (customContentLayout == null) {
            rootView = inflater.inflate(R.layout.fragment_base_recycler, container, false);
        } else {
            rootView = inflater.inflate(customContentLayout, container, false);
        }

        customListView = (CustomListView) rootView.findViewById(R.id.custom_list);

        if (itemDecoration == null) {
            if (properties.getSpanCount() == 1) {
                itemDecoration = new SpacesItemDecoration(properties.getSpaceSize());
            } else {
                itemDecoration = new SpacesItemDecoration(properties.getSpaceSize(), properties.getSpanCount());
            }
        }

        customListView.init(properties, itemDecoration, this);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (customListView.getProperties().isHasSwipe()) {
            appBarLayout.addOnOffsetChangedListener(this);
        }

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

    @Override
    public void onPause() {
        super.onPause();

        if (customListView.getProperties().isHasSwipe()) {
            appBarLayout.removeOnOffsetChangedListener(this);
        }
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        if (i == 0) {
            customListView.getRefreshLayout().setEnabled(true);
        } else {
            customListView.getRefreshLayout().setEnabled(false);
        }
    }

    public abstract BaseRecyclerAdapter onInitAdapter();

    public abstract void onLoadItems(int limit, int offset);
}
