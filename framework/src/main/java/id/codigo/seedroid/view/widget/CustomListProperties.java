package id.codigo.seedroid.view.widget;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Gayo on 3/3/2017.
 */
public class CustomListProperties {
    private boolean onReverse;
    private boolean hasSwipe, hasLoadMoreBase, hasLoadMore, onCustomBackground;
    private int spanCount, spaceSize;
    private int limit, offset;
    private int colorBackground;

    private RecyclerView.ItemDecoration itemDecoration;
    private LinearLayoutManager layoutManager;

    public CustomListProperties() {
        onReverse = false;
        hasSwipe = true;
        hasLoadMore = true;
        onCustomBackground = false;
        spanCount = 1;
        spaceSize = 8;
        limit = 10;
        offset = 0;
    }

    public boolean isOnReverse() {
        return onReverse;
    }

    public void setOnReverse(boolean onReverse) {
        this.onReverse = onReverse;
    }

    public boolean isHasSwipe() {
        return hasSwipe;
    }

    public void setHasSwipe(boolean hasSwipe) {
        this.hasSwipe = hasSwipe;
    }

    public boolean isHasLoadMoreBase() {
        return hasLoadMoreBase;
    }

    public void setHasLoadMoreBase(boolean hasLoadMoreBase) {
        this.hasLoadMoreBase = hasLoadMoreBase;
    }

    public boolean isHasLoadMore() {
        return hasLoadMore;
    }

    public void setHasLoadMore(boolean hasLoadMore) {
        this.hasLoadMore = hasLoadMore;
    }

    public boolean isOnCustomBackground() {
        return onCustomBackground;
    }

    public void setOnCustomBackground(boolean onCustomBackground) {
        this.onCustomBackground = onCustomBackground;
    }

    public int getSpanCount() {
        return spanCount;
    }

    public void setSpanCount(int spanCount) {
        this.spanCount = spanCount;
    }

    public int getSpaceSize() {
        return spaceSize;
    }

    public void setSpaceSize(int spaceSize) {
        this.spaceSize = spaceSize;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getColorBackground() {
        return colorBackground;
    }

    public void setColorBackground(int colorBackground) {
        this.colorBackground = colorBackground;
    }

    public RecyclerView.ItemDecoration getItemDecoration() {
        return itemDecoration;
    }

    public void setItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        this.itemDecoration = itemDecoration;
    }

    public LinearLayoutManager getLayoutManager() {
        return layoutManager;
    }

    public void setLayoutManager(LinearLayoutManager layoutManager) {
        this.layoutManager = layoutManager;

        if (onReverse) {
            this.layoutManager.setReverseLayout(true);
        }
    }
}
