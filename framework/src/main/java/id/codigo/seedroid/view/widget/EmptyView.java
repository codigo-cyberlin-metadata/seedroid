package id.codigo.seedroid.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import id.codigo.seedroid.R;

/**
 * Created by Lukma on 3/29/2016.
 */
public class EmptyView extends FrameLayout {
    private View viewEmpty, viewEmptyLoading, viewEmptyReload;
    private ImageView viewEmptyImage;
    private TextView viewEmptyStatus;

    public EmptyView(Context context) {
        super(context);
        init();
    }

    public EmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EmptyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void init() {
        inflate(getContext(), R.layout.view_empty, this);

        viewEmpty = findViewById(R.id.view_empty);
        viewEmptyLoading = findViewById(R.id.view_loading);
        viewEmptyReload = findViewById(R.id.view_reload);
        viewEmptyImage = (ImageView) findViewById(R.id.image_view_status);
        viewEmptyStatus = (TextView) findViewById(R.id.text_view_status);

        viewEmptyLoading.setVisibility(VISIBLE);
    }

    public void setViewBackground(int color) {
        viewEmpty.setBackgroundColor(color);
        viewEmptyLoading.setBackgroundColor(color);
        viewEmptyReload.setBackgroundColor(color);
    }

    public void onRefresh() {
        viewEmpty.setVisibility(VISIBLE);
        viewEmptyReload.setVisibility(GONE);
    }

    public void onSuccessResult() {
        setVisibility(GONE);
    }

    public void onNotFoundResult(String message) {
        viewEmpty.setVisibility(VISIBLE);
        viewEmptyReload.setVisibility(View.VISIBLE);
        TypedArray a = getContext().getTheme().obtainStyledAttributes(new int[]{R.attr.noConnectionIcon});
        Drawable drawable = a.getDrawable(0);
        viewEmptyImage.setImageDrawable(drawable);
        viewEmptyStatus.setText(message);
    }

    public void onFailedResult(String message) {
        viewEmpty.setVisibility(VISIBLE);
        viewEmptyReload.setVisibility(View.VISIBLE);
        if (message.equals(getContext().getString(R.string.status_no_connection))) {
            TypedArray a = getContext().getTheme().obtainStyledAttributes(new int[]{R.attr.noConnectionIcon});
            Drawable drawable = a.getDrawable(0);
            viewEmptyImage.setImageDrawable(drawable);
            viewEmptyStatus.setText(message);
        } else {
            TypedArray a = getContext().getTheme().obtainStyledAttributes(new int[]{R.attr.failedConnectionIcon});
            Drawable drawable = a.getDrawable(0);
            viewEmptyImage.setImageDrawable(drawable);
            viewEmptyStatus.setText(R.string.status_failed);
        }
    }
}
