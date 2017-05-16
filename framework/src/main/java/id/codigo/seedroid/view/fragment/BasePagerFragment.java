package id.codigo.seedroid.view.fragment;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import id.codigo.seedroid.R;
import id.codigo.seedroid.presenter.BasePresenter;
import id.codigo.seedroid.view.BaseView;
import id.codigo.seedroid.view.adapter.BasePagerAdapter;

/**
 * Created by Lukma on 3/29/2016.
 */
public abstract class BasePagerFragment<V extends BaseView, P extends BasePresenter<V>> extends BaseFragment<ViewDataBinding, V, P> {
    protected ArrayList<BaseFragment> items = new ArrayList<>();

    @Override
    public int attachLayout() {
        return R.layout.fragment_base_pager;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);

        ViewPager viewPager = (ViewPager) rootView.findViewById(R.id.view_pager);
        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout);

        onInitItems();

        BasePagerAdapter pagerAdapter = new BasePagerAdapter(getChildFragmentManager(), items);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(items.size());

        tabLayout.setupWithViewPager(viewPager);

        return rootView;
    }

    /**
     * Function to fill view pager at child class
     */
    protected abstract void onInitItems();
}
