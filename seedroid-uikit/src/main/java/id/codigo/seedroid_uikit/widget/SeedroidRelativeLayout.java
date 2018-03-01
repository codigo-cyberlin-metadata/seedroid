package id.codigo.seedroid_uikit.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import id.codigo.seedroid_uikit.sdkutils.ResourceUtils;
import id.codigo.seedroid_uikit.uiutils.Drawables;

/**
 * Created by papahnakal on 01/03/18.
 */

public class SeedroidRelativeLayout extends RelativeLayout {
    public SeedroidRelativeLayout(Context context) {
        super(context);
    }

    public SeedroidRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }

    public SeedroidRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SeedroidRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
    private void initView(AttributeSet attrs)
    {
        if (!this.isInEditMode() && ResourceUtils.isNeedLoadResource())
        {
            String resourceName = "";
            int resourceId = -1;
            int count = attrs.getAttributeCount();
            for (int i = 0; i < count; i++)
            {
                resourceName = attrs.getAttributeName(i);
                resourceId = attrs.getAttributeResourceValue(i, -1);
                if (-1 != resourceId && "background".equals(resourceName))
                {
                    Drawables.getInstance().setDrawable(this, resourceId, true);
                }
            }
        }
    }

    @Override
    public boolean hasOverlappingRendering() {
        return false;
    }
}
