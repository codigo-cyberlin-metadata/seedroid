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
    private CustomListProperties properties;
    private boolean onLoading = false, onRefresh = false, hasLoadMoreBase;
    private int pastVisibleItems, visibleItemCount, totalItemCount;

    private SwipeRefreshLayout refreshLayout;
    private EmptyView emptyView;
    private RecyclerView recyclerView;
    private EmptyView footerView;

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

    public CustomListProperties getProperties() {
        return properties;
    }

    public boolean isOnLoading() {
        return onLoading;
    }

    public SwipeRefreshLayout getRefreshLayout() {
        return refreshLayout;
    }

    public EmptyView getEmptyView() {
        return emptyView;
    }

    public BaseRecyclerAdapter getRecyclerAdapter() {
        return recyclerAdapter;
    }

    public ArrayList<T> getItems() {
        return items;
    }

    public void init(CustomListProperties properties, CustomRecyclerListener listener) {
        this.listener = listener;

        this.properties = properties;

        if (this.properties.isHasSwipe()) {
            this.properties.setHasSwipe(!this.properties.isOnReverse());
        }

        hasLoadMoreBase = properties.isHasLoadMore();

        if (this.properties.getItemDecoration() == null) {
            if (this.properties.getSpanCount() == 1) {
                this.properties.setItemDecoration(new SpacesItemDecoration(this.properties.getSpaceSize()));
            } else {
                this.properties.setItemDecoration(new SpacesItemDecoration(this.properties.getSpaceSize(), this.properties.getSpanCount()));
            }
        }

        if (this.properties.getLayoutManager() == null) {
            GridLayoutManager layoutManager = new GridLayoutManager(getContext(), this.properties.getSpanCount());
            layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getRecyclerAdapter().getItemViewType(position) == BaseRecyclerAdapter.ITEM_VIEW_TYPE_ITEM
                            ? 1 : CustomListView.this.properties.getSpanCount();
                }
            });
            this.properties.setLayoutManager(layoutManager);
        }

        inflate(getContext(), R.layout.view_custom_list, this);

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
        emptyView = (EmptyView) findViewById(R.id.view_empty);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        if (this.properties.isHasSwipe()) {
            refreshLayout.setOnRefreshListener(this);
        } else {
            refreshLayout.setEnabled(false);
        }

        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(this.properties.getItemDecoration());
        recyclerView.setLayoutManager(this.properties.getLayoutManager());
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                visibleItemCount = getProperties().getLayoutManager().getChildCount();
                totalItemCount = getProperties().getLayoutManager().getItemCount();
                pastVisibleItems = getProperties().getLayoutManager().findFirstVisibleItemPosition();

                if (items.size() != 0 && (getProperties().isHasLoadMore() && !onLoading) && ((visibleItemCount + pastVisibleItems) >= totalItemCount)) {
                    getProperties().setOffset(getProperties().getOffset() + getProperties().getLimit());
                    onLoadItems(getProperties().getLimit(), getProperties().getOffset());
                }
            }
        });

        recyclerAdapter = onInitAdapter();
        recyclerView.setAdapter(recyclerAdapter);

        footerView = new EmptyView(getContext());
        footerView.setLayoutParams(new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

        if (properties.isHasLoadMore()) {
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
                properties.setOffset(0);
            }

            onLoadItems(properties.getLimit(), properties.getOffset());
        }
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
        properties.setHasLoadMore(hasLoadMoreBase);
        properties.setOffset(0);
        onRefresh = true;

        footerView.setVisibility(View.VISIBLE);
        footerView.getLayoutParams().height = LayoutParams.WRAP_CONTENT;

        onLoadItems(properties.getLimit(), properties.getOffset());
    }

    /**
     * Function to fill recycler view after get items at child class
     *
     * @param items Data to fill at recycler view
     */
    public void bindItems(ArrayList<T> items) {
        bindItems(true, getContext().getString(R.string.status_success), items);
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

                if (items.size() < properties.getLimit()) {
                    properties.setHasLoadMore(false);

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
                    properties.setHasLoadMore(false);

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
