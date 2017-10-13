package id.codigo.seedroid.helper;

import android.animation.ValueAnimator;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import id.codigo.seedroid.view.utils.DynamicDimens;
import id.codigo.seedroid.view.utils.FixedDimens;
import id.codigo.seedroid.view.utils.Utils;
import id.codigo.seedroid.view.widget.CustomBottomNavigation;

/**
 * Created by papahnakal on 14/09/17.
 */

public class AnimationHelper {
    /**
     * this variable hold {@link CustomBottomNavigation#type}
     *
     * @see CustomBottomNavigation#type
     */
    private int type;

    public static int ANIMATION_DURATION = 100;

    /**
     * default constructor
     *
     * @param type used for make decision which animation should play on tab items
     */
    public AnimationHelper(int type) {
        this.type = type;
    }

    /**
     * animate tab item when it deselected
     */
    public void animateDeactivate(final TextView tabText, final ImageView tabIcon) {
        switch (type) {
            case CustomBottomNavigation.TYPE_FIXED:
                AlphaAnimation fAlphaAnimation = new AlphaAnimation(1f, 0.5f);
                fAlphaAnimation.setDuration(ANIMATION_DURATION);
                fAlphaAnimation.setFillAfter(true);
                ValueAnimator paddingAnimator = ValueAnimator.ofInt(
                        Utils.dpToPx(FixedDimens.TAB_PADDING_TOP_ACTIVE)
                        , Utils.dpToPx(FixedDimens.TAB_PADDING_TOP_INACTIVE)
                );
                paddingAnimator.setDuration(ANIMATION_DURATION);
                paddingAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        FrameLayout.LayoutParams tabIconLayoutParams = (FrameLayout.LayoutParams) tabIcon.getLayoutParams();
                        tabIconLayoutParams.topMargin = (int) valueAnimator.getAnimatedValue();
                        tabIcon.setLayoutParams(tabIconLayoutParams);
                    }
                });
                ScaleAnimation fScaleAnimation = new ScaleAnimation(1.1f, 1, 1.1f, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1f);

                AnimationSet fAnimationSet = new AnimationSet(true);
                fAnimationSet.setDuration(100);
                fAnimationSet.addAnimation(fScaleAnimation);
                fAnimationSet.addAnimation(fAlphaAnimation);
                fAnimationSet.setFillAfter(true);

                tabIcon.startAnimation(fAlphaAnimation);
                tabText.startAnimation(fAnimationSet);
                paddingAnimator.start();
                break;

            case CustomBottomNavigation.TYPE_DYNAMIC:
                AlphaAnimation dAlphaAnimation = new AlphaAnimation(1f, 0.5f);
                dAlphaAnimation.setDuration(ANIMATION_DURATION);
                dAlphaAnimation.setFillAfter(true);
                ValueAnimator dPaddingAnimator = ValueAnimator.ofInt(
                        Utils.dpToPx(DynamicDimens.TAB_PADDING_TOP_ACTIVE)
                        , Utils.dpToPx(DynamicDimens.TAB_PADDING_TOP_INACTIVE)
                );
                dPaddingAnimator.setDuration(ANIMATION_DURATION);
                dPaddingAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        FrameLayout.LayoutParams tabIconLayoutParams = (FrameLayout.LayoutParams) tabIcon.getLayoutParams();
                        tabIconLayoutParams.topMargin = (int) valueAnimator.getAnimatedValue();
                        tabIcon.setLayoutParams(tabIconLayoutParams);

                    }
                });
                ScaleAnimation dScaleAnimation = new ScaleAnimation(1, 0, 1, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1f);
                dScaleAnimation.setDuration(100);
                dScaleAnimation.setFillAfter(true);
                tabText.startAnimation(dScaleAnimation);
                tabIcon.startAnimation(dAlphaAnimation);
                dPaddingAnimator.start();
                break;
        }
    }

    /**
     * animate tab item when it selected
     */
    public void animateActivate(final TextView tabText, final ImageView tabIcon) {
        switch (type) {
            case CustomBottomNavigation.TYPE_FIXED:
                AlphaAnimation fAlphaAnimation = new AlphaAnimation(0.5f, 1f);
                fAlphaAnimation.setDuration(ANIMATION_DURATION);
                fAlphaAnimation.setFillAfter(true);

                ValueAnimator paddingAnimator = ValueAnimator.ofInt(
                        Utils.dpToPx(FixedDimens.TAB_PADDING_TOP_INACTIVE)
                        , Utils.dpToPx(FixedDimens.TAB_PADDING_TOP_ACTIVE)
                );
                paddingAnimator.setDuration(ANIMATION_DURATION);
                paddingAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        FrameLayout.LayoutParams tabIconLayoutParams = (FrameLayout.LayoutParams) tabIcon.getLayoutParams();
                        tabIconLayoutParams.topMargin = (int) valueAnimator.getAnimatedValue();
                        tabIcon.setLayoutParams(tabIconLayoutParams);

                    }
                });

                ScaleAnimation fTextScaleAnimation = new ScaleAnimation(1, 1.1f, 1, 1.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1f);
                AnimationSet fAnimationSet = new AnimationSet(true);
                fAnimationSet.setDuration(ANIMATION_DURATION);
                fAnimationSet.addAnimation(fAlphaAnimation);
                fAnimationSet.addAnimation(fTextScaleAnimation);
                fAnimationSet.setFillAfter(true);

                tabIcon.startAnimation(fAlphaAnimation);
                tabText.startAnimation(fAnimationSet);
                paddingAnimator.start();
                break;
            case CustomBottomNavigation.TYPE_DYNAMIC:

                AlphaAnimation dAlphaAnimation = new AlphaAnimation(0.5f, 1f);
                dAlphaAnimation.setDuration(ANIMATION_DURATION);
                dAlphaAnimation.setFillAfter(true);

                ValueAnimator dPaddingAnimator = ValueAnimator.ofInt(
                        Utils.dpToPx(DynamicDimens.TAB_PADDING_TOP_INACTIVE)
                        , Utils.dpToPx(DynamicDimens.TAB_PADDING_TOP_ACTIVE)
                );
                dPaddingAnimator.setDuration(ANIMATION_DURATION);
                dPaddingAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        FrameLayout.LayoutParams tabIconLayoutParams = (FrameLayout.LayoutParams) tabIcon.getLayoutParams();
                        tabIconLayoutParams.topMargin = (int) valueAnimator.getAnimatedValue();
                        tabIcon.setLayoutParams(tabIconLayoutParams);
                    }
                });

                ScaleAnimation dTextScaleAnimation = new ScaleAnimation(0, 1.1f, 0, 1.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1f);
                dTextScaleAnimation.setDuration(ANIMATION_DURATION);
                dTextScaleAnimation.setFillAfter(true);
                tabText.startAnimation(dTextScaleAnimation);
                tabIcon.startAnimation(dAlphaAnimation);
                dPaddingAnimator.start();
                break;
        }

    }

    public void animateActivate(final TextView tabText, final ImageView tabIcon,
                                int unselectedTextColor, int selectedTextColor,
                                Drawable selectedTabIcon, Drawable unselectedTabIcon) {
        tabText.setTextColor(selectedTextColor);

        Drawable[] tabIcons = new Drawable[]{unselectedTabIcon, selectedTabIcon};
        TransitionDrawable iconTransition = new TransitionDrawable(tabIcons);
        tabIcon.setImageDrawable(iconTransition);
        iconTransition.startTransition(ANIMATION_DURATION);
        switch (type) {
            case CustomBottomNavigation.TYPE_FIXED:

                ValueAnimator paddingAnimator = ValueAnimator.ofInt(
                        Utils.dpToPx(FixedDimens.TAB_PADDING_TOP_INACTIVE)
                        , Utils.dpToPx(FixedDimens.TAB_PADDING_TOP_ACTIVE)
                );
                paddingAnimator.setDuration(ANIMATION_DURATION);
                paddingAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        FrameLayout.LayoutParams tabIconLayoutParams = (FrameLayout.LayoutParams) tabIcon.getLayoutParams();
                        tabIconLayoutParams.topMargin = (int) valueAnimator.getAnimatedValue();
                        tabIcon.setLayoutParams(tabIconLayoutParams);
                    }
                });

                ScaleAnimation fTextScaleAnimation = new ScaleAnimation(1, 1.1f, 1, 1.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1f);
                AnimationSet fAnimationSet = new AnimationSet(true);
                fAnimationSet.setDuration(ANIMATION_DURATION);
                fAnimationSet.addAnimation(fTextScaleAnimation);
                fAnimationSet.setFillAfter(true);

                tabText.startAnimation(fAnimationSet);
                paddingAnimator.start();
                break;
            case CustomBottomNavigation.TYPE_DYNAMIC:

                ValueAnimator dPaddingAnimator = ValueAnimator.ofInt(
                        Utils.dpToPx(DynamicDimens.TAB_PADDING_TOP_INACTIVE)
                        , Utils.dpToPx(DynamicDimens.TAB_PADDING_TOP_ACTIVE)
                );
                dPaddingAnimator.setDuration(ANIMATION_DURATION);
                dPaddingAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        FrameLayout.LayoutParams tabIconLayoutParams = (FrameLayout.LayoutParams) tabIcon.getLayoutParams();
                        tabIconLayoutParams.topMargin = (int) valueAnimator.getAnimatedValue();
                        tabIcon.setLayoutParams(tabIconLayoutParams);
                    }
                });

                ScaleAnimation dTextScaleAnimation = new ScaleAnimation(0, 1.1f, 0, 1.1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1f);
                dTextScaleAnimation.setDuration(ANIMATION_DURATION);
                dTextScaleAnimation.setFillAfter(true);
                tabText.startAnimation(dTextScaleAnimation);
                dPaddingAnimator.start();
                break;
        }

    }

    /**
     * animate tab item when it deselected
     */
    public void animateDeactivate(final TextView tabText, final ImageView tabIcon,
                                  int unselectedTabTextColor,int selectedTextColor,
                                  Drawable selectedTabIcon, Drawable unselectedTabIcon) {

        Drawable[] tabIcons = new Drawable[]{selectedTabIcon, unselectedTabIcon};
        TransitionDrawable iconTransition = new TransitionDrawable(tabIcons);
        tabIcon.setImageDrawable(iconTransition);
        iconTransition.startTransition(ANIMATION_DURATION);

        switch (type) {
            case CustomBottomNavigation.TYPE_FIXED:
                ValueAnimator paddingAnimator = ValueAnimator.ofInt(
                        Utils.dpToPx(FixedDimens.TAB_PADDING_TOP_ACTIVE)
                        , Utils.dpToPx(FixedDimens.TAB_PADDING_TOP_INACTIVE)
                );
                paddingAnimator.setDuration(ANIMATION_DURATION);
                paddingAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        FrameLayout.LayoutParams tabIconLayoutParams = (FrameLayout.LayoutParams) tabIcon.getLayoutParams();
                        tabIconLayoutParams.topMargin = (int) valueAnimator.getAnimatedValue();
                        tabIcon.setLayoutParams(tabIconLayoutParams);
                    }
                });
                ScaleAnimation fScaleAnimation = new ScaleAnimation(1.1f, 1, 1.1f, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1f);

                AnimationSet fAnimationSet = new AnimationSet(true);
                fAnimationSet.setDuration(100);
                fAnimationSet.addAnimation(fScaleAnimation);
                fAnimationSet.setFillAfter(true);

                tabText.startAnimation(fAnimationSet);
                paddingAnimator.start();
                tabText.setTextColor(unselectedTabTextColor);

                break;

            case CustomBottomNavigation.TYPE_DYNAMIC:
                AlphaAnimation dAlphaAnimation = new AlphaAnimation(1f, 0.5f);
                dAlphaAnimation.setDuration(ANIMATION_DURATION);
                dAlphaAnimation.setFillAfter(true);
                ValueAnimator dPaddingAnimator = ValueAnimator.ofInt(
                        Utils.dpToPx(DynamicDimens.TAB_PADDING_TOP_ACTIVE)
                        , Utils.dpToPx(DynamicDimens.TAB_PADDING_TOP_INACTIVE)
                );
                dPaddingAnimator.setDuration(ANIMATION_DURATION);
                dPaddingAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        FrameLayout.LayoutParams tabIconLayoutParams = (FrameLayout.LayoutParams) tabIcon.getLayoutParams();
                        tabIconLayoutParams.topMargin = (int) valueAnimator.getAnimatedValue();
                        tabIcon.setLayoutParams(tabIconLayoutParams);

                    }
                });
                ScaleAnimation dScaleAnimation = new ScaleAnimation(1, 0, 1, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 1f);
                dScaleAnimation.setDuration(100);
                dScaleAnimation.setFillAfter(true);
                tabText.startAnimation(dScaleAnimation);
                dPaddingAnimator.start();
                break;
        }
    }
}
