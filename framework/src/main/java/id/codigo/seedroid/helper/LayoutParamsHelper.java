package id.codigo.seedroid.helper;

import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import id.codigo.seedroid.view.utils.Dimens;
import id.codigo.seedroid.view.utils.DynamicDimens;
import id.codigo.seedroid.view.utils.FixedDimens;
import id.codigo.seedroid.view.utils.Utils;
import id.codigo.seedroid.view.widget.CustomBottomNavigation;

/**
 * Created by papahnakal on 14/09/17.
 */

public class LayoutParamsHelper {
    /**
     * this method provide tab icon layout params
     * @param bottomNavigationType layout params can be different according to {@link CustomBottomNavigation#type}
     * @return return appropriate layout params for tab icon
     */
    public static FrameLayout.LayoutParams getTabItemIconLayoutParams(int bottomNavigationType) {
        int iconSize = Utils.dpToPx(Dimens.TAB_ICON_SIZE);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(iconSize, iconSize);
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
        switch (bottomNavigationType) {
            case CustomBottomNavigation.TYPE_FIXED:
                layoutParams.topMargin = Utils.dpToPx(FixedDimens.TAB_PADDING_TOP_INACTIVE);
                return layoutParams;
            case CustomBottomNavigation.TYPE_DYNAMIC:
                layoutParams.topMargin = Utils.dpToPx(DynamicDimens.TAB_PADDING_TOP_INACTIVE);
                return layoutParams;
        }
        return null;
    }

    /**
     * this provide tab label layout params
     * @param bottomNavigationType layout params can be different according to {@link CustomBottomNavigation#type}
     * @return return appropriate layout params for tab label
     */
    public static FrameLayout.LayoutParams getTabItemTextLayoutParams(int bottomNavigationType) {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER_HORIZONTAL;

        switch (bottomNavigationType) {
            case CustomBottomNavigation.TYPE_FIXED:
                layoutParams.bottomMargin = Utils.dpToPx(FixedDimens.TAB_PADDING_BOTTOM);
                layoutParams.rightMargin = Utils.dpToPx(FixedDimens.TAB_PADDING_RIGHT);
                layoutParams.leftMargin = Utils.dpToPx(FixedDimens.TAB_PADDING_LEFT);
                layoutParams.topMargin = Utils.dpToPx(FixedDimens.TAB_ICON_SIZE) + Utils.dpToPx(FixedDimens.TAB_PADDING_TOP_INACTIVE);
                return layoutParams;
            case CustomBottomNavigation.TYPE_DYNAMIC:
                layoutParams.bottomMargin = Utils.dpToPx(DynamicDimens.TAB_PADDING_BOTTOM_ACTIVE);
                layoutParams.rightMargin = Utils.dpToPx(DynamicDimens.TAB_PADDING_RIGHT);
                layoutParams.leftMargin = Utils.dpToPx(DynamicDimens.TAB_PADDING_LEFT);
                layoutParams.topMargin = Utils.dpToPx(FixedDimens.TAB_ICON_SIZE) + Utils.dpToPx(FixedDimens.TAB_PADDING_TOP_ACTIVE);
                return layoutParams;
        }
        return null;
    }
}
