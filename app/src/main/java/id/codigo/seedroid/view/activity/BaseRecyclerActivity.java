package id.codigo.seedroid.view.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import id.codigo.seedroid.R;
import id.codigo.seedroid.view.adapter.BaseRecyclerAdapter;
import id.codigo.seedroid.view.widget.CustomListView;
import id.codigo.seedroid.view.widget.EmptyView;
import id.codigo.seedroid.view.widget.SpacesItemDecoration;

/**
 * Created by Lukma on 3/29/2016.
 */
public abstract class BaseRecyclerActivity<T> extends BaseActivity implements
        AppBarLayout.OnOffsetChangedListener,
        View.OnClickListener,
        CustomListView.CustomRecyclerListener {
    protected Integer customContentLayout = null;
    protected boolean hasHeader = false;
    protected boolean onReverse = false;
    protected int spanCount = 1;
    protected int spaceSize = 8;
    protected RecyclerView.ItemDecoration itemDecoration;

    protected AppBarLayout appBarLayout;
    protected View headerView;
    protected Toolbar toolbar;
    protected CustomListView<T> customListView;
    protected EmptyView emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (customContentLayout == null) {
            if (!hasHeader) {
                setContentView(R.layout.activity_base_recycler);
            } else {
                setContentView(R.layout.activity_base_recycler_with_header);
            }
        } else {
            setContentView(customContentLayout);
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (onReverse) {
            AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
            params.setScrollFlags(0);
            toolbar.setLayoutParams(params);
        }

        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        customListView = (CustomListView) findViewById(R.id.custom_list);
        emptyView = (EmptyView) findViewById(R.id.view_empty_root);

        if (hasHeader) {
            headerView = onInitHeaderView();
            LinearLayout headerLayout = (LinearLayout) findViewById(R.id.header_layout);
            headerLayout.addView(headerView);
        }

        if (emptyView != null) {
            emptyView.setOnClickListener(this);
        }

        if (itemDecoration == null) {
            if (spanCount == 1) {
                itemDecoration = new SpacesItemDecoration(spaceSize);
            } else {
                itemDecoration = new SpacesItemDecoration(spaceSize, spanCount);
            }
        }

        customListView.init(onReverse, spanCount, itemDecoration, this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (appBarLayout != null && customListView.isHasSwipe()) {
            appBarLayout.addOnOffsetChangedListener(this);
        }

        if (customListView.getItems().size() == 0 && !customListView.isOnLoading()) {
            customListView.onRefreshItems();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (appBarLayout != null && customListView.isHasSwipe()) {
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

    @Override
    public void onClick(View view) {
        if (emptyView != null && view.getId() == emptyView.getId()) {
            onLoadHeader();
        }
    }

    public void onLoadHeader() {
    }

    /**
     * Function to fill header view
     */
    public View onInitHeaderView() {
        return null;
    }

    public abstract BaseRecyclerAdapter onInitAdapter();

    public abstract void onLoadItems(int limit, int offset);
}
