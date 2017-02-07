package id.codigo.seedroid.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import id.codigo.seedroid.SeedroidApplication;
import id.codigo.seedroid.view.holder.BaseItemHolder;

/**
 * Created by Lukma on 3/29/2016.
 */
public abstract class BaseRecyclerAdapter extends RecyclerView.Adapter<BaseItemHolder> {
    public static final int ITEM_VIEW_TYPE_HEADER = 0;
    public static final int ITEM_VIEW_TYPE_ITEM = 1;
    public static final int ITEM_VIEW_TYPE_FOOTER = 2;

    protected ArrayList<?> items = new ArrayList<>();

    protected Context context;

    protected View headerView;
    protected View footerView;

    public BaseRecyclerAdapter(ArrayList<?> items) {
        this.items = items;

        context = SeedroidApplication.getInstance();

        headerView = new View(context);
        footerView = new View(context);
    }

    public void setHeaderView(View headerView) {
        this.headerView = headerView;
    }

    public void setFooterView(View footerView) {
        this.footerView = footerView;
    }

    @Override
    public BaseItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_VIEW_TYPE_ITEM) {
            return onCreateViewHolderItem(parent, viewType);
        } else if (viewType == ITEM_VIEW_TYPE_HEADER) {
            return new BaseItemHolder(headerView);
        } else if (viewType == ITEM_VIEW_TYPE_FOOTER) {
            return new BaseItemHolder(footerView);
        } else {
            return onCreateViewHolderOther(parent, viewType);
        }
    }

    /**
     * Function create view at child recycler view with type item
     *
     * @param viewGroup Parent view
     * @param viewType  Type view
     */
    protected abstract BaseItemHolder onCreateViewHolderItem(ViewGroup viewGroup, int viewType);

    /**
     * Function create view at child recycler view except type item
     *
     * @param viewGroup Parent view
     * @param viewType  Type view
     */
    protected BaseItemHolder onCreateViewHolderOther(ViewGroup viewGroup, int viewType) {
        return new BaseItemHolder(new View(context));
    }

    @Override
    public void onBindViewHolder(BaseItemHolder holder, int position) {
        if (getItemViewType(position) == ITEM_VIEW_TYPE_ITEM) {
            onBindViewHolderItem(holder, position, position - 1);
        } else {
            onBindViewHolderOther(holder, position);
        }
    }

    /**
     * Function set data inside view at child recycler view with type item
     *
     * @param holder       Holder view
     * @param positionItem Position item at child recycler view
     * @param positionData Position data
     */
    protected abstract void onBindViewHolderItem(BaseItemHolder holder, int positionItem, int positionData);

    /**
     * Function set data inside view at child recycler view except type item
     *
     * @param holder       Holder view
     * @param positionItem Position item at child recycler view
     */
    protected void onBindViewHolderOther(BaseItemHolder holder, int positionItem) {
    }

    @Override
    public int getItemCount() {
        return items.size() + 2;
    }

    @Override
    public int getItemViewType(int position) {
        return position > 0 && position < getItemCount() - 1 ? ITEM_VIEW_TYPE_ITEM
                : position == 0 ? ITEM_VIEW_TYPE_HEADER : ITEM_VIEW_TYPE_FOOTER;
    }
}
