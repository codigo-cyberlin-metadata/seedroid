package id.codigo.seedroid_uikit.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import com.example.seedroid_uikit.R;

import id.codigo.seedroid_uikit.sdkutils.FontCustom;
import id.codigo.seedroid_uikit.sdkutils.ResourceUtils;
import id.codigo.seedroid_uikit.uiutils.Colors;
import id.codigo.seedroid_uikit.uiutils.Drawables;
import id.codigo.seedroid_uikit.uiutils.Strings;

/**
 * Created by papahnakal on 20/02/18.
 */

public class SeedroidButton extends AppCompatButton{
    private final String TYPE_FACE_SMALL = "0";
    private final String TYPE_FACE_SEMIBOLD = "1";
    private final String TYPE_FACE_BOLD = "2";

    private Typeface curFace;
    public SeedroidButton(Context context) {
        super(context);
    }

    public SeedroidButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomTypeFace(context,attrs);
        initView(attrs);
    }

    public SeedroidButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setCustomTypeFace(context,attrs);
        initView(attrs);
    }

    private void setCustomTypeFace(Context context,AttributeSet attrs){
        if (isInEditMode()) {
            return;
        }
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.
                SeedroidTextView);
        String typeFace = typedArray.getString(R.styleable.SeedroidTextView_type_face);
        if (TYPE_FACE_SEMIBOLD.equals(typeFace))
        {
            curFace = FontCustom.setSemiBoldFont(context);
        }
        else if (TYPE_FACE_BOLD.equals(typeFace))
        {
            curFace = FontCustom.setBoldFont(context);
        }
        else
        {
            curFace = FontCustom.setSmallFont(context);
        }
        setTypeface(curFace);

        typedArray.recycle();
    }

    @Override
    public void setTextAppearance(Context context, int resid)
    {
        super.setTextAppearance(context, resid);
        if (curFace != null)
        {
            setTypeface(curFace);
        }
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
                if (-1 != resourceId)
                {
                    if ("background".equals(resourceName))
                    {
                        Drawables.getInstance().setDrawable(this, resourceId, true);
                    }
                    else if ("text".equals(resourceName))
                    {
                        setText(Strings.getInstance().getString(getResources(), resourceId));
                    }
                    else if ("textColor".equals(resourceName))
                    {
                        setTextColor(Colors.getInstance().getColor(getResources(), resourceId));
                    }
                }
            }
        }
    }
}
