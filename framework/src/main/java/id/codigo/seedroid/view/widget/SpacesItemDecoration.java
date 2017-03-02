package id.codigo.seedroid.view.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Lukma on 3/29/2016.
 */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int space;
    private int span = 1;

    public SpacesItemDecoration(int space) {
        this.space = space;
    }

    public SpacesItemDecoration(int space, int span) {
        this.space = space;
        this.span = span;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildLayoutPosition(view);
        if (span == 1) {
            outRect.left = space;
            outRect.right = space;
            outRect.bottom = position == 0 ? 0 : space / 2;
            outRect.top = position == 0 ? 0 : position == 1 ? space : space / 2;
        } else {
            outRect.left = position % 2 == 1 ? space : space / 2;
            outRect.right = position % 2 != 1 ? space : space / 2;
            outRect.bottom = position == 0 ? 0 : space / 2;
            outRect.top = position == 0 ? 0 : space / 2;
        }
    }
}
