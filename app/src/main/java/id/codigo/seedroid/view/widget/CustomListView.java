package id.codigo.seedroid.view.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;

import id.codigo.seedroid.R;
import id.codigo.seedroid.view.adapter.BaseRecyclerAdapter;

/**
 * Created by Lukma on 3/29/2016.
 */
public class CustomListView<T> extends FrameLayout implements SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
    private boolean hasSwipe = true, hasLoadMoreBase, hasLoadMore = true, onCustomBackground = false;
    private int limit = 10, offset = 0;
    private int colorBackground;

    private boolean onLoading = false, onRefresh = false;
    private int pastVisibleItems, visibleItemCount, totalItemCount;

    private SwipeRefreshLayout refreshLayout;
    private EmptyView emptyView;
    private RecyclerView recyclerView;
    private EmptyView footerView;

    private GridLayoutManager layoutManager;
    private BaseRecyclerAdapter recyclerAdapter;

    private ArrayList<T> items = new ArrayList<>();

    private CustomRecyclerListener listener;

    public CustomListView(Context context) {
        super(context);
    }

    public CustomListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public boolean isHasSwipe() {
        return hasSwipe;
    }

    public boolean isOnLoading() {
        return onLoading;
    }

    public int getLimit() {
        return limit;
    }

    public int getOffset() {
        return offset;
    }

    public SwipeRefreshLayout getRefreshLayout() {
        return refreshLayout;
    }

    public EmptyView getEmptyView() {
        return emptyView;
    }

    public GridLayoutManager getLayoutManager() {
        return layoutManager;
    }

    public ArrayList<T> getItems() {
        return items;
    }

    public void init(boolean onReverse, final int spanCount, RecyclerView.ItemDecoration itemDecoration, CustomRecyclerListener listener) {
        this.listener = listener;

        hasSwipe = !onReverse;
        hasLoadMoreBase = hasLoadMore;

        inflate(getContext(), R.layout.view_custom_list, this);

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
        emptyView = (EmptyView) findViewById(R.id.view_empty);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        if (!hasSwipe) {
            refreshLayout.setEnabled(false);
        } else {
            refreshLayout.setOnRefreshListener(this);
        }

        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                visibleItemCount = layoutManager.getChildCount();
                totalItemCount = layoutManager.getItemCount();
                pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                if (items.size() != 0 && (hasLoadMore && !onLoading) && ((visibleItemCount + pastVisibleItems) >= totalItemCount)) {
                    offset += limit;
                    onLoadItems(limit, offset);
                }
            }
        });

        layoutManager = new GridLayoutManager(getContext(), spanCount);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return recyclerAdapter.getItemViewType(position) == BaseRecyclerAdapter.ITEM_VIEW_TYPE_ITEM
                        ? 1 : spanCount;
            }
        });

        if (onReverse) {
            layoutManager.setReverseLayout(true);
        }

        recyclerView.setLayoutManager(layoutManager);

        recyclerAdapter = onInitAdapter();
        recyclerView.setAdapter(recyclerAdapter);

        footerView = new EmptyView(getContext());
        footerView.setLayoutParams(new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        if (onCustomBackground) {
            emptyView.setViewBackground(colorBackground);
            footerView.setViewBackground(colorBackground);
        }

        if (hasLoadMore) {
            recyclerAdapter.setFooterView(footerView);
        }

        emptyView.setOnClickListener(this);
        footerView.setOnClickListener(this);
    }

    @Override
    public void onRefresh() {
        onRefreshItems();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == emptyView.getId()) {
            emptyView.onRefresh();
            onRefreshItems();
        } else if (v.getId() == footerView.getId()) {
            footerView.onRefresh();

            if (items.size() == 0) {
                offset = 0;
            }

            onLoadItems(limit, offset);
        }
    }

    public void setViewBackground(int color) {
        recyclerView.setBackgroundColor(color);
        emptyView.setViewBackground(color);
        emptyView.setViewBackground(colorBackground);
        footerView.setViewBackground(colorBackground);

        colorBackground = color;
        onCustomBackground = true;
    }

    /**
     * Function to init adapter for recycler view at child class
     */
    private BaseRecyclerAdapter onInitAdapter() {
        return listener.onInitAdapter();
    }

    /**
     * Function to start get items to fill recycler view at child class
     *
     * @param limit  Limit data every load
     * @param offset Offset data every load
     */
    private void onLoadItems(int limit, int offset) {
        onLoading = true;

        listener.onLoadItems(limit, offset);
    }

    /**
     * Function to reset recycler view
     */
    public void onRefreshItems() {
        hasLoadMore = hasLoadMoreBase;
        offset = 0;
        onRefresh = true;

        footerView.setVisibility(View.VISIBLE);
        footerView.getLayoutParams().height = LayoutParams.WRAP_CONTENT;

        onLoadItems(limit, offset);
    }

    /**
     * Function to fill recycler view after get items at child class
     *
     * @param status  Status get data
     * @param message Message to show at view if status false
     * @param items   Data to fill at recycler view
     */
    public void bindItems(boolean status, String message, ArrayList<T> items) {
        onLoading = false;

        if (onRefresh) {
            onRefresh = false;
            refreshLayout.setRefreshing(false);
            this.items.clear();
        }

        int itemsLength = this.items.size();

        if (status && items != null) {
            if (items.size() > 0) {
                if (itemsLength == 0) {
                    emptyView.onSuccessResult();
                }

                this.items.addAll(items);

                if (items.size() < limit) {
                    hasLoadMore = false;

                    footerView.setVisibility(View.GONE);
                    footerView.getLayoutParams().height = 0;

                    recyclerAdapter.notifyItemRangeInserted(recyclerAdapter.getItemCount(), items.size() + 1);
                } else {
                    recyclerAdapter.notifyItemRangeInserted(recyclerAdapter.getItemCount(), items.size());
                }
            } else {
                if (itemsLength == 0) {
                    emptyView.onNotFoundResult(message);
                } else {
                    hasLoadMore = false;

                    footerView.setVisibility(View.GONE);
                    footerView.getLayoutParams().height = 0;
                }

                recyclerAdapter.notifyDataSetChanged();
            }
        } else {
            if (itemsLength == 0) {
                emptyView.onFailedResult(message);
            } else {
                footerView.onFailedResult(message);
            }

            recyclerAdapter.notifyDataSetChanged();
        }
    }

    public interface CustomRecyclerListener {
        BaseRecyclerAdapter onInitAdapter();

        void onLoadItems(int limit, int offset);
    }
}
