package id.codigo.seedroid.view.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import id.codigo.seedroid.R;
import id.codigo.seedroid.view.adapter.BaseRecyclerAdapter;
import id.codigo.seedroid.view.widget.CustomListView;
import id.codigo.seedroid.view.widget.SpacesItemDecoration;

/**
 * Created by Lukma on 3/29/2016.
 */
public abstract class BaseRecyclerActivity extends BaseActivity implements
        AppBarLayout.OnOffsetChangedListener,
        View.OnClickListener,
        CustomListView.CustomRecyclerListener {
    protected Integer customContentLayout = null;
    protected boolean onReverse = false;
    protected int spanCount = 1;
    protected int spaceSize = 8;
    protected RecyclerView.ItemDecoration itemDecoration;

    protected AppBarLayout appBarLayout;
    private View toolbarIcon;
    protected CustomListView customListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (customContentLayout == null) {
            setContentView(R.layout.activity_base_recycler);
        } else {
            setContentView(customContentLayout);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (onReverse) {
            AppBarLayout.LayoutParams params = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
            params.setScrollFlags(0);
            toolbar.setLayoutParams(params);
        }

        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        toolbarIcon = getToolbarLogoIcon(toolbar);
        customListView = (CustomListView) findViewById(R.id.custom_list);

        if (itemDecoration == null) {
            if (spanCount == 1) {
                itemDecoration = new SpacesItemDecoration(spaceSize);
            } else {
                itemDecoration = new SpacesItemDecoration(spaceSize, spanCount);
            }
        }

        if (toolbarIcon != null) {
            toolbarIcon.setOnClickListener(this);
        }

        customListView.init(onReverse, spanCount, itemDecoration, this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (customListView.isHasSwipe()) {
            appBarLayout.addOnOffsetChangedListener(this);
        }

        if (customListView.getItems().size() == 0 && !customListView.isOnLoading()) {
            customListView.onRefreshItems();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (customListView.isHasSwipe()) {
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
        if (toolbarIcon != null && view.getId() == toolbarIcon.getId()) {
            onBack();
        }
    }

    public abstract BaseRecyclerAdapter onInitAdapter();

    public abstract void onLoadItems(int limit, int offset);
}
