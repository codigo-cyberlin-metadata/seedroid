package id.codigo.seedroid.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import id.codigo.seedroid.view.fragment.BaseFragment;

/**
 * Created by Lukma on 3/29/2016.
 */
public class BasePagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<BaseFragment> items;

    public BasePagerAdapter(FragmentManager fragmentManager, ArrayList<BaseFragment> items) {
        super(fragmentManager);
        this.items = items;
    }

    @Override
    public Fragment getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return items.get(position).getTitle();
    }
}
