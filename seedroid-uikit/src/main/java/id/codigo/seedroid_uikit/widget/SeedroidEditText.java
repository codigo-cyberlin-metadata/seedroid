package id.codigo.seedroid_uikit.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.seedroid_uikit.R;

import java.lang.reflect.Field;

import id.codigo.seedroid_uikit.sdkutils.ReflectUtil;
import id.codigo.seedroid_uikit.sdkutils.ResourceUtils;
import id.codigo.seedroid_uikit.uiutils.Colors;
import id.codigo.seedroid_uikit.uiutils.Drawables;
import id.codigo.seedroid_uikit.uiutils.Strings;

/**
 * Created by papahnakal on 01/03/18.
 */

public class SeedroidEditText extends AppCompatEditText{
    private static final String TAG = SeedroidEditText.class.getSimpleName();
    public SeedroidEditText(Context context) {
        super(context);
        disableCustomSelectionAction();
    }

    public SeedroidEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
        disableCustomSelectionAction();
    }

    public SeedroidEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs);
        disableCustomSelectionAction();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    private void setCusorStyleForEditText(AttributeSet attrs, int count, boolean hasSetCusor)
    {
        String resourceName;
        int resourceId;
        Log.i(TAG, "textCursorDrawable is in normal state");

        for (int i = 0; i < count; i++)
        {
            resourceName = attrs.getAttributeName(i);
            resourceId = attrs.getAttributeResourceValue(i, -1);
            if (-1 != resourceId)
            {
                if ("textCursorDrawable".equals(resourceName))
                {
                    hasSetCusor = true;
                    setCursorStyle(resourceId);
                }
            }
        }
        if (!hasSetCusor)
        {
            Log.w(TAG, "textCursorDrawable , hasn't SetCusor");
            setCursorStyle(R.drawable.color_cursor);
        }
    }

    private void setCursorStyle(int resId)
    {
        Log.i(TAG, "setCursorStyle");

        try
        {
            Field fCursorDrawableRes = null;
            fCursorDrawableRes = TextView.class.getDeclaredField("mCursorDrawableRes");
            ReflectUtil.changeAccesibility(fCursorDrawableRes);

            fCursorDrawableRes.set(this, resId);

            /*if (ResourceUtils.isNeedLoadResource())
            {
                fCursorDrawableRes.set(this, 0);
            }*/
        }
        catch (IllegalAccessException e)
        {
            Log.e(TAG, e.toString());
        }
        catch (NoSuchFieldException e)
        {
            Log.e(TAG, e.toString());
        }
    }

    public void disableCustomSelectionAction()
    {
        setCustomSelectionActionModeCallback(new ActionMode.Callback()
        {
            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu)
            {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode)
            {
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu)
            {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item)
            {
                return false;
            }
        });
    }

    private void initView(AttributeSet attrs)
    {
        if (isInEditMode()) {
            return;
        }

        String resourceName = "";
        int resourceId = -1;
        int count = attrs.getAttributeCount();
        boolean hasSetCusor = false;

        if (ResourceUtils.isNeedLoadResource())
        {

            for (int i = 0; i < count; i++)
            {
                resourceName = attrs.getAttributeName(i);

                resourceId = attrs.getAttributeResourceValue(i, -1);
                if (-1 != resourceId)
                {
                    if ("background".equals(resourceName))
                    {
                        Drawables.getInstance().setDrawable(this, resourceId, true);
                    }
                    else if ("hint".equals(resourceName))
                    {
                        setHint(Strings.getInstance().getString(getResources(), resourceId));
                    }
                    else if ("textColor".equals(resourceName))
                    {
                        setTextColor(Colors.getInstance().getColor(getResources(), resourceId));
                    }
                    else if ("textColorHint".equals(resourceName))
                    {
                        setHintTextColor(Colors.getInstance().getColor(getResources(), resourceId));
                    }

                    else if ("textCursorDrawable".equals(resourceName))
                    {
                        hasSetCusor = true;

                        setCursorStyle(resourceId);
                    }
                }
            }

            if (!hasSetCusor)
            {
                Log.w(TAG, "textCursorDrawable , hasn't SetCusor");
                setCursorStyle(R.drawable.color_cursor);
            }
        }
        else
        {
            setCusorStyleForEditText(attrs, count, hasSetCusor);
        }
    }

    public void enableCustomSelectionAction()
    {
        setCustomSelectionActionModeCallback(null);
    }
}
