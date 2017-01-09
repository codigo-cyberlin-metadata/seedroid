package id.codigo.seedroid.view.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import id.codigo.seedroid.R;
import id.codigo.seedroid.model.widget.PagerChildModel;
import id.codigo.seedroid.view.adapter.BasePagerAdapter;

/**
 * Created by Lukma on 3/29/2016.
 */
public abstract class BasePagerFragment extends BaseFragment {
    protected Integer customContentLayout = null;
    protected ArrayList<PagerChildModel> items = new ArrayList<>();

    protected ViewPager viewPager;
    protected BasePagerAdapter pagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView;
        if (customContentLayout == null) {
            rootView = inflater.inflate(R.layout.fragment_base_pager, container, false);
        } else {
            rootView = inflater.inflate(customContentLayout, container, false);
        }

        viewPager = (ViewPager) rootView.findViewById(R.id.view_pager);
        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tab_layout);

        onInitItems();

        pagerAdapter = new BasePagerAdapter(getChildFragmentManager(), items);
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
