package id.codigo.seedroid.view.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

import id.codigo.seedroid.R;
import id.codigo.seedroid.view.adapter.BasePagerAdapter;
import id.codigo.seedroid.view.fragment.BaseFragment;
import id.codigo.seedroid.view.widget.EmptyView;

/**
 * Created by Lukma on 3/29/2016.
 */
public abstract class BasePagerActivity extends BaseActivity implements AppBarLayout.OnOffsetChangedListener, View.OnClickListener {
    protected Integer customContentLayout = null;
    protected boolean hasHeader = false;

    protected AppBarLayout appBarLayout;
    protected View headerView;
    protected Toolbar toolbar;
    protected TabLayout tabLayout;
    protected ViewPager viewPager;
    protected BasePagerAdapter pagerAdapter;
    protected EmptyView emptyView;

    protected ArrayList<BaseFragment> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (customContentLayout == null) {
            if (!hasHeader) {
                setContentView(R.layout.activity_base_pager);
            } else {
                setContentView(R.layout.activity_base_pager_with_header);
            }
        } else {
            setContentView(customContentLayout);
        }

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        emptyView = (EmptyView) findViewById(R.id.view_empty_root);

        if (hasHeader) {
            headerView = onInitHeaderView();
            LinearLayout headerLayout = (LinearLayout) findViewById(R.id.header_layout);
            headerLayout.addView(headerView);
        }

        if (emptyView != null) {
            emptyView.setOnClickListener(this);
        }

        onInitItems();

        pagerAdapter = new BasePagerAdapter(getSupportFragmentManager(), items);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(items.size());

        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (appBarLayout != null) {
            appBarLayout.addOnOffsetChangedListener(this);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (appBarLayout != null) {
            appBarLayout.removeOnOffsetChangedListener(this);
        }
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
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

    /**
     * Function to fill view pager at child class
     */
    protected abstract void onInitItems();
}
