package id.codigo.seedroid.view.fragment;

import android.support.v4.app.Fragment;

/**
 * Created by Lukma on 3/29/2016.
 */
public class BaseFragment extends Fragment {
    protected boolean isCreated = false;
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
