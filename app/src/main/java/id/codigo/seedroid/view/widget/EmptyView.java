package id.codigo.seedroid.view.widget;

import android.content.Context;
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
        viewEmpty.setVisibility(GONE);
    }

    public void onNotFoundResult(String message) {
        viewEmpty.setVisibility(VISIBLE);
        viewEmptyReload.setVisibility(View.VISIBLE);
        viewEmptyImage.setImageResource(R.drawable.ic_default_status_not_found);
        viewEmptyStatus.setText(message);
    }

    public void onFailedResult(String message) {
        viewEmpty.setVisibility(VISIBLE);
        viewEmptyReload.setVisibility(View.VISIBLE);
        if (message.equals(getContext().getString(R.string.status_no_connection))) {
            viewEmptyImage.setImageResource(R.drawable.ic_default_status_no_connection);
            viewEmptyStatus.setText(message);
        } else {
            viewEmptyImage.setImageResource(R.drawable.ic_default_status_not_found);
            viewEmptyStatus.setText(R.string.text_reload);
        }
    }
}
