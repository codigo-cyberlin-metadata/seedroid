package id.codigo.seedroid.view.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import id.codigo.seedroid.R;
import id.codigo.seedroid.helper.AnimationHelper;
import id.codigo.seedroid.helper.LayoutParamsHelper;
import id.codigo.seedroid.view.utils.Utils;

/**
 * Created by papahnakal on 14/09/17.
 */

public class BottomNavigationView extends FrameLayout implements View.OnClickListener{
    private OnTabItemClickListener onTabItemClickListener;
    private int position;

    //Attributes
    private String text;
    private Drawable selectedTabIcon;
    private int selectedTabTextColor;

    private Drawable unselectedTabIcon;
    private int unselectedTabTextColor;

    //Views
    private TextView textView;
    private ImageView iconImageView;

    private boolean isActive = false;
    private int type = CustomBottomNavigation.TYPE_FIXED;
    private AnimationHelper animationHelper;
    private CustomBottomNavigation customBottomNavigation;
    public BottomNavigationView(@NonNull Context context) {
        super(context);
        customAttributes(null);
    }

    public BottomNavigationView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        customAttributes(attrs);
    }

    public BottomNavigationView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        customAttributes(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BottomNavigationView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        customAttributes(attrs);
    }

    public void customAttributes (AttributeSet attributeSet){
        if(attributeSet!=null){

        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        checkParent();
    }

    private void checkParent() {
        post(new Runnable() {
            @Override
            public void run() {
                if (getParent() instanceof CustomBottomNavigation) {
                    customBottomNavigation = (CustomBottomNavigation) getParent();
                    type = customBottomNavigation.getType();
                    setupView();
                } else {
                    throw new RuntimeException("TabItem parent must be BottomNavigation!");
                }
            }
        });
    }
    public void setupView(){
        setOnClickListener(this);
        if (customBottomNavigation.getMode() == CustomBottomNavigation.MODE_PHONE) {
            setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));
        } else {
            setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Utils.dpToPx(56)));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setForeground(getResources().getDrawable(R.drawable.tab_forground, null));
        }

        animationHelper = new AnimationHelper(type);

        textView = new TextView(getContext());
        textView.setTextColor(selectedTabTextColor);
        textView.setText(text);
        textView.setGravity(Gravity.CENTER);
        textView.setLayoutParams(LayoutParamsHelper.getTabItemTextLayoutParams(type));
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);

        iconImageView = new ImageView(getContext());
        iconImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        iconImageView.setImageDrawable(selectedTabIcon);
        iconImageView.setLayoutParams(LayoutParamsHelper.getTabItemIconLayoutParams(type));

        if (position == customBottomNavigation.getDefaultItem()) {
            isActive = true;
            if (unselectedTabIcon==null || unselectedTabTextColor==0) {
                animationHelper.animateActivate(textView, iconImageView);
            }else {
                animationHelper.animateActivate(textView,iconImageView,selectedTabTextColor,selectedTabTextColor,selectedTabIcon,unselectedTabIcon);
            }
        } else {
            if (unselectedTabIcon==null || unselectedTabTextColor==0) {
                animationHelper.animateDeactivate(textView, iconImageView);
            }else {
                animationHelper.animateDeactivate(textView,iconImageView,unselectedTabTextColor,selectedTabTextColor,selectedTabIcon,unselectedTabIcon);
            }
        }

        switch (type) {
            case CustomBottomNavigation.TYPE_FIXED:
                addView(iconImageView);
                addView(textView);
                break;
            case CustomBottomNavigation.TYPE_DYNAMIC:
                if (isActive) {
                    addView(iconImageView);
                    addView(textView);
                } else {
                    addView(iconImageView);
                    addView(textView);
                }
                break;
        }
    }
    public void setSelected(boolean isActive) {
        if (this.isActive != isActive) {
            notifyChange();
            this.isActive = isActive;
        }
    }

    public void setOnTabItemClickListener(OnTabItemClickListener onTabItemClickListener) {
        this.onTabItemClickListener = onTabItemClickListener;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return this.position;
    }

    @Override
    public void onClick(View view) {
        if (onTabItemClickListener != null) {
            onTabItemClickListener.onTabItemClick(position);
        }
    }

    private void notifyChange() {
        if (unselectedTabIcon==null || unselectedTabTextColor==0){
            if (isActive){
                animationHelper.animateDeactivate(textView, iconImageView);
            }else {
                animationHelper.animateActivate(textView, iconImageView);
            }
        }else {
            if (isActive){
                animationHelper.animateDeactivate(textView,iconImageView,unselectedTabTextColor,selectedTabTextColor,selectedTabIcon,unselectedTabIcon);
            }else {
                animationHelper.animateActivate(textView,iconImageView,unselectedTabTextColor,selectedTabTextColor,selectedTabIcon,unselectedTabIcon);
            }
        }
    }

    public void setTypeface(Typeface typeface) {
        if (textView != null) {
            textView.setTypeface(typeface);
        }
    }
    public interface OnTabItemClickListener{
        void onTabItemClick(int position);
    }
}
