package com.fffz.badgeview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class BadgeLayout extends ViewGroup {

    public BadgeLayout(Context context) {
        super(context);
    }

    public BadgeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public View getTargetView() {
        return getChildAt(0);
    }

    public BadgeView getBadgeView() {
        return (BadgeView) getChildAt(1);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View targetView = getChildAt(0);
        BadgeView badgeView = (BadgeView) getChildAt(1);
        layoutTargetView(targetView, badgeView);
        layoutBadgeView(targetView, badgeView);
    }

    private void layoutTargetView(View targetView, BadgeView badgeView) {
        int childWidth = targetView.getMeasuredWidth();
        int childHeight = targetView.getMeasuredHeight();
        int t = getMeasuredHeight() - childHeight;
        int l = 0;
        if (childWidth + 2 * (badgeView.getMeasuredWidth() - (int) badgeView.getOffsetX()) <=
                getMeasuredWidth()) {
            l = (getMeasuredWidth() - childWidth) / 2;
        }
        if (childHeight + 2 * (badgeView.getMeasuredHeight() - (int) badgeView.getOffsetY()) <=
                getMeasuredHeight()) {
            t = t / 2;
        }
        targetView.layout(l, t, l + childWidth, t + childHeight);
    }

    private void layoutBadgeView(View targetView, BadgeView badgeView) {
        int childWidth = badgeView.getMeasuredWidth();
        int childHeight = badgeView.getMeasuredHeight();
        int l = targetView.getRight() - (int) badgeView.getOffsetX();
        int t = targetView.getTop() + (int) badgeView.getOffsetY() - childHeight;
        badgeView.layout(l, t, l + childWidth, t + childHeight);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        View targetView = getChildAt(0);
        BadgeView badgeView = (BadgeView) getChildAt(1);
        int widthMeasureSpec2 = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec),
                MeasureSpec.AT_MOST);
        int heightMeasureSpec2 = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(heightMeasureSpec),
                MeasureSpec.AT_MOST);
        targetView.measure(widthMeasureSpec2, heightMeasureSpec2);
        badgeView.measure(widthMeasureSpec2, heightMeasureSpec2);
        int measuredWidth;
        LayoutParams layoutParams = getLayoutParams();
        int times = badgeView.isKeepTargetCenter() ? 2 : 1;
        if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.AT_MOST || layoutParams.width ==
                WRAP_CONTENT) {
            measuredWidth = targetView.getMeasuredWidth() + times * (badgeView.getMeasuredWidth() -
                    (int) badgeView.getOffsetX());
        } else {
            measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
        }
        int measuredHeight;
        if (MeasureSpec.getMode(heightMeasureSpec) == MeasureSpec.AT_MOST || layoutParams.height ==
                WRAP_CONTENT) {
            measuredHeight = targetView.getMeasuredHeight() + times * (badgeView.getMeasuredHeight()
                    - (int) badgeView.getOffsetY());
        } else {
            measuredHeight = MeasureSpec.getSize(heightMeasureSpec);
        }
        setMeasuredDimension(measuredWidth, measuredHeight);
    }

}