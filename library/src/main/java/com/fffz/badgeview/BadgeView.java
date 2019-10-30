package com.fffz.badgeview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class BadgeView extends View {

    private static final int VALUE_NOT_SET = Integer.MIN_VALUE;

    private View mTargetView;
    private Paint mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
    private Paint.FontMetrics mFontMetrics;
    private RectF mBackgroundRect = new RectF();
    private RectF mTextRect = new RectF();
    private float mCornerRadius;
    private int mTextColor;
    private float mTextSize;
    private String mText;
    private float mOffsetX = VALUE_NOT_SET, mOffsetY = VALUE_NOT_SET;
    private boolean mIsCircle;
    private boolean mIsSemicircle;
    private PorterDuffXfermode mSrcInMode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    private boolean mIsKeepTargetCenter;
    private int mWidth, mHeight;

    public BadgeView(BadgeView badgeView) {
        super(badgeView.getContext());
        mText = badgeView.mText;
        mTextSize = badgeView.mTextSize;
        mTextColor = badgeView.mTextColor;
        mCornerRadius = badgeView.mCornerRadius;
        mOffsetX = badgeView.mOffsetX;
        mOffsetY = badgeView.mOffsetY;
        mIsCircle = badgeView.mIsCircle;
        mIsSemicircle = badgeView.mIsSemicircle;
        mIsKeepTargetCenter = badgeView.mIsKeepTargetCenter;
        setBackgroundDrawable(badgeView.getBackground());
        setupPaint();
    }

    public BadgeView(Context context) {
        super(context);
        mTextColor = Color.WHITE;
        mTextSize = dp2px(context, 12);
        setupPaint();
    }

    public BadgeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BadgeView);
        mText = a.getString(R.styleable.BadgeView_text);
        mTextSize = a.getDimension(R.styleable.BadgeView_textSize, dp2px(context, 12));
        mTextColor = a.getColor(R.styleable.BadgeView_textColor, Color.RED);
        mCornerRadius = a.getDimension(R.styleable.BadgeView_cornerRadius, 0);
        mOffsetX = a.getDimension(R.styleable.BadgeView_offsetX, VALUE_NOT_SET);
        mOffsetY = a.getDimension(R.styleable.BadgeView_offsetY, VALUE_NOT_SET);
        mIsCircle = a.getBoolean(R.styleable.BadgeView_circle, false);
        mIsSemicircle = a.getBoolean(R.styleable.BadgeView_semicircle, false);
        mIsKeepTargetCenter = a.getBoolean(R.styleable.BadgeView_keepTargetCenter, false);
        a.recycle();
        setupPaint();
    }

    private void setupPaint() {
        mBackgroundPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(mTextColor);
    }

    private static int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public void attach(View targetView) {
        ViewGroup parent = (ViewGroup) getParent();
        if (parent != null) {
            if (parent instanceof BadgeLayout &&
                    ((BadgeLayout) parent).getTargetView() == targetView) {
                return;
            }
            throw new IllegalStateException("BadgeView already has a parent");
        }
        ViewGroup targetParent = (ViewGroup) targetView.getParent();
        if (targetParent == null) {
            throw new IllegalStateException("targetView must have a parent");
        }
        mTargetView = targetView;
        if (targetParent instanceof BadgeLayout) {
            targetParent.addView(this);
        } else {
            int indexOfTarget = targetParent.indexOfChild(targetView);
            ViewGroup.LayoutParams targetParams = targetView.getLayoutParams();
            targetParent.removeView(targetView);
            BadgeLayout badgeLayout = new BadgeLayout(getContext());
            if (targetParent instanceof RelativeLayout) {
                badgeLayout.setId(targetView.getId());
            }
            badgeLayout.addView(targetView);
            badgeLayout.addView(this);
            targetParent.addView(badgeLayout, indexOfTarget, targetParams);
        }
    }

    public void detach() {
        ViewGroup parent = (ViewGroup) getParent();
        if (parent == null) {
            return;
        }
        ViewGroup badgeLayoutParent = (ViewGroup) parent.getParent();
        if (badgeLayoutParent == null) {
            return;
        }
        if (!(parent instanceof BadgeLayout)) {
            throw new IllegalStateException("BadgeView's parent must be BadgeLayout");
        }
        BadgeLayout badgeLayout = (BadgeLayout) parent;
        View targetView = badgeLayout.getTargetView();
        ViewGroup.LayoutParams targetParams = targetView.getLayoutParams();
        int indexOfBadgeLayout = badgeLayoutParent.indexOfChild(badgeLayout);
        badgeLayoutParent.removeView(badgeLayout);
        badgeLayout.removeAllViews();
        badgeLayoutParent.addView(targetView, indexOfBadgeLayout, targetParams);
    }

    public View getTargetView() {
        return mTargetView;
    }

    public boolean isKeepTargetCenter() {
        return mIsKeepTargetCenter;
    }

    public BadgeView setKeepTargetCenter(boolean keepTargetCenter) {
        mIsKeepTargetCenter = keepTargetCenter;
        return this;
    }

    public void setWidth(int width) {
        mWidth = width;
        setRight(width);
    }

    public void setHeight(int height) {
        mHeight = height;
        setBottom(height);
    }

    public float getCornerRadius() {
        return mCornerRadius;
    }

    public BadgeView setCornerRadius(float cornerRadius) {
        mCornerRadius = cornerRadius;
        return this;
    }

    public boolean getIsCircle() {
        return mIsCircle;
    }

    public void setCircle(boolean isCircle) {
        mIsCircle = isCircle;
    }

    public boolean isSemicircle() {
        return mIsSemicircle;
    }

    public BadgeView setSemicircle(boolean semicircle) {
        mIsSemicircle = semicircle;
        return this;
    }

    public int getTextColor() {
        return mTextColor;
    }

    public BadgeView setTextColor(int textColor) {
        mTextColor = textColor;
        mTextPaint.setColor(mTextColor);
        return this;
    }

    public float getTextSize() {
        return mTextSize;
    }

    public BadgeView setTextSize(float textSize) {
        mTextSize = textSize;
        mTextPaint.setTextSize(mTextSize);
        return this;
    }

    public String getText() {
        return mText;
    }

    public BadgeView setText(String text) {
        mText = text;
        requestLayout();
        return this;
    }

    public float getOffsetX() {
        return mOffsetX;
    }

    public BadgeView setOffset(int offsetX, int offsetY) {
        mOffsetX = offsetX;
        mOffsetY = offsetY;
        return this;
    }

    public float getOffsetY() {
        return mOffsetY;
    }

    private void measureText() {
        mTextRect.left = 0;
        mTextRect.top = 0;
        if (TextUtils.isEmpty(mText)) {
            mTextRect.right = 0;
            mTextRect.bottom = 0;
        } else {
            mTextRect.right = mTextPaint.measureText(mText);
            mFontMetrics = mTextPaint.getFontMetrics();
            mTextRect.bottom = mFontMetrics.descent - mFontMetrics.ascent;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureText();
        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        if (mIsCircle) {
            int diameter = Math.max(width, height);
            setMeasuredDimension(diameter, diameter);
            // mOffsetX = (int) ((1 - 1 / Math.sqrt(2)) * diameter / 2); // 紧贴边角
        } else {
            if (mIsSemicircle) {
                mCornerRadius = height / 2;
            }
            setMeasuredDimension(width, height);
        }
        mBackgroundRect.right = getMeasuredWidth();
        mBackgroundRect.bottom = getMeasuredHeight();
        if (mOffsetX == VALUE_NOT_SET) {
            mOffsetX = getMeasuredHeight() / 2;
        }
        if (mOffsetY == VALUE_NOT_SET) {
            mOffsetY = getMeasuredHeight() / 2;
        }
    }

    private int measureWidth(int widthMeasureSpec) {
        int width = mWidth;
        if (width <= 0) {
            width = getLayoutParams().width;
        }
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        if (width <= 0 && widthMode == MeasureSpec.EXACTLY) {
            width = MeasureSpec.getSize(widthMeasureSpec);
        }
        if (width <= 0) {
            width = (int) mTextRect.width() + getPaddingLeft() + getPaddingRight();
            Drawable background = getBackground();
            if (background != null && background instanceof BitmapDrawable) {
                width = Math.max(width, background.getIntrinsicWidth());
            }
        }
        return width;
    }

    private int measureHeight(int heightMeasureSpec) {
        int height = mHeight;
        if (height <= 0) {
            height = getLayoutParams().height;
        }
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (height <= 0 && heightMode == MeasureSpec.EXACTLY) {
            height = MeasureSpec.getSize(heightMeasureSpec);
        }
        if (height <= 0) {
            height = (int) mTextRect.height() + getPaddingTop() + getPaddingBottom();
            Drawable background = getBackground();
            if (background != null && background instanceof BitmapDrawable) {
                height = Math.max(height, background.getIntrinsicHeight());
            }
        }
        return height;
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void draw(Canvas canvas) {
        canvas.saveLayerAlpha(0, 0, getWidth(), getHeight(), 255, Canvas.ALL_SAVE_FLAG);
        Drawable background = getBackground();
        if (background instanceof BitmapDrawable) {
            drawShape(canvas);
            drawBitmap(canvas, (BitmapDrawable) background);
        } else if (background instanceof ColorDrawable) {
            mBackgroundPaint.setColor(((ColorDrawable) background).getColor());
            drawShape(canvas);
        }
        drawText(canvas);
    }

    private void drawShape(Canvas canvas) {
        if (mIsCircle) {
            float radius = mBackgroundRect.width() / 2;
            canvas.drawCircle(radius, radius, radius, mBackgroundPaint);
        } else {
            canvas.drawRoundRect(mBackgroundRect, mCornerRadius, mCornerRadius, mBackgroundPaint);
        }
    }

    private void drawBitmap(Canvas canvas, BitmapDrawable background) {
        mBackgroundPaint.setXfermode(mSrcInMode);
        canvas.drawBitmap(background.getBitmap(), null, mBackgroundRect, mBackgroundPaint);
        mBackgroundPaint.setXfermode(null);
    }

    private void drawText(Canvas canvas) {
        if (TextUtils.isEmpty(mText)) {
            return;
        }
        canvas.drawText(mText, (getMeasuredWidth() - mTextRect.width()) / 2,
                (getMeasuredHeight() - mFontMetrics.top - mFontMetrics.bottom) / 2, mTextPaint);
    }

}