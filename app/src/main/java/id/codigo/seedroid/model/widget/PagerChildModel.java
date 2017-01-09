package id.codigo.seedroid.model.widget;

import id.codigo.seedroid.view.fragment.BaseFragment;

/**
 * Created by Lukma on 3/29/2016.
 */
public class PagerChildModel {
    private BaseFragment fragment;
    private String title;

    public PagerChildModel(BaseFragment fragment, String title) {
        this.fragment = fragment;
        this.fragment.setTitle(title);
        this.title = title;
    }

    public BaseFragment getFragment() {
        return fragment;
    }

    public void setFragment(BaseFragment fragment) {
        this.fragment = fragment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
