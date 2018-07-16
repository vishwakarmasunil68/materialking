/*
 * Copyright 2017 Chaos Leong
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.appentus.materialkingseller.helper;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.annotation.Px;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputFilter;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.MovementMethod;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.EditorInfo;

import com.appentus.materialkingseller.R;

/**
 * Provides a widget for enter PIN/OTP/password etc.
 *
 * @author Chaos Leong
 *         01/04/2017
 */
public class PinView extends AppCompatEditText {

    private static final String TAG = "PinView";

    private static final boolean DBG = false;

    private static final int BLINK = 500;

    private static final int DEFAULT_COUNT = 4;

    private static final InputFilter[] NO_FILTERS = new InputFilter[0];

    private static final int VIEW_TYPE_RECTANGLE = 0;
    private static final int VIEW_TYPE_LINE = 1;

    private int mViewType;

    private int mPinItemCount;

    private float mPinItemWidth;
    private float mPinItemHeight;
    private int mPinItemRadius;
    private int mPinItemSpacing;

    private final Paint mPaint;
    private final TextPaint mTextPaint;
    private final Paint mAnimatorTextPaint;

    private ColorStateList mLineColor;
    private int mCurLineColor = Color.BLACK;
    private int mLineWidth;

    private final Rect mTextRect = new Rect();
    private final RectF mItemBorderRect = new RectF();
    private final RectF mItemLineRect = new RectF();
    private final Path mPath = new Path();
    private final PointF mItemCenterPoint = new PointF();

    private ValueAnimator mDefaultAddAnimator;
    private boolean isAnimationEnable = false;

    private Blink mBlink;
    private boolean isCursorVisible;
    private boolean drawCursor;
    private float mCursorHeight;
    private int mCursorWidth;
    private int mCursorColor;

    public PinView(Context context) {
        this(context, null);
    }

    public PinView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, R.attr.pinViewStyle);
    }

    public PinView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        final Resources res = getResources();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);

        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.density = res.getDisplayMetrics().density;
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextSize(getTextSize());

        mAnimatorTextPaint = new TextPaint(mTextPaint);

        final Resources.Theme theme = context.getTheme();

        TypedArray a = theme.obtainStyledAttributes(attrs, R.styleable.PinView, defStyleAttr, 0);

        mViewType = a.getInt(R.styleable.PinView_viewType, VIEW_TYPE_RECTANGLE);
        mPinItemCount = a.getInt(R.styleable.PinView_itemCount, DEFAULT_COUNT);
        mPinItemHeight = a.getDimensionPixelSize(R.styleable.PinView_itemHeight,
                res.getDimensionPixelSize(R.dimen.pv_pin_view_item_size));
        mPinItemWidth = a.getDimensionPixelSize(R.styleable.PinView_itemWidth,
                res.getDimensionPixelSize(R.dimen.pv_pin_view_item_size));
        mPinItemSpacing = a.getDimensionPixelSize(R.styleable.PinView_itemSpacing,
                res.getDimensionPixelSize(R.dimen.pv_pin_view_item_spacing));
        mPinItemRadius = a.getDimensionPixelSize(R.styleable.PinView_itemRadius, 0);
        mLineWidth = a.getDimensionPixelSize(R.styleable.PinView_lineWidth,
                res.getDimensionPixelSize(R.dimen.pv_pin_view_item_line_width));
        mLineColor = a.getColorStateList(R.styleable.PinView_lineColor);
        isCursorVisible = a.getBoolean(R.styleable.PinView_android_cursorVisible, true);
        mCursorColor = a.getColor(R.styleable.PinView_cursorColor, getCurrentTextColor());
        mCursorWidth = a.getDimensionPixelSize(R.styleable.PinView_cursorWidth,
                res.getDimensionPixelSize(R.dimen.pv_pin_view_cursor_width));

        a.recycle();

        if (mLineColor != null) {
            mCurLineColor = mLineColor.getDefaultColor();
        }
        updateCursorHeight();

        checkItemRadius();

        setMaxLength(mPinItemCount);
        mPaint.setStrokeWidth(mLineWidth);
        setupAnimator();

        super.setCursorVisible(false);
        setTextIsSelectable(false);
    }

    private void setMaxLength(int maxLength) {
        if (maxLength >= 0) {
            setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});
        } else {
            setFilters(NO_FILTERS);
        }
    }

    private void setupAnimator() {
        mDefaultAddAnimator = ValueAnimator.ofFloat(0.5f, 1f);
        mDefaultAddAnimator.setDuration(150);
        mDefaultAddAnimator.setInterpolator(new DecelerateInterpolator());
        mDefaultAddAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float scale = (float) animation.getAnimatedValue();
                int alpha = (int) (255 * scale);
                mAnimatorTextPaint.setTextSize(getTextSize() * scale);
                mAnimatorTextPaint.setAlpha(alpha);
                postInvalidate();
            }
        });
    }

    private void checkItemRadius() {
        if (mViewType == VIEW_TYPE_LINE) {
            int halfOfLineWidth = mLineWidth / 2;
            if (mPinItemRadius > halfOfLineWidth) {
                throw new RuntimeException("The itemRadius can not be greater than lineWidth when viewType is line");
            }
        }
        int halfOfItemWidth = (int) (mPinItemWidth / 2);
        if (mPinItemRadius > halfOfItemWidth) {
            throw new RuntimeException("The itemRadius can not be greater than itemWidth");
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        float boxHeight = mPinItemHeight;

        if (widthMode == MeasureSpec.EXACTLY) {
            // Parent has told us how big to be. So be it.
            width = widthSize;
        } else {
            float boxesWidth = (mPinItemCount - 1) * mPinItemSpacing + mPinItemCount * mPinItemWidth;
            width = Math.round(boxesWidth + ViewCompat.getPaddingEnd(this) + ViewCompat.getPaddingStart(this));
            if (mPinItemSpacing == 0) {
                width -= (mPinItemCount - 1) * mLineWidth;
            }
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            // Parent has told us how big to be. So be it.
            height = heightSize;
        } else {
            height = Math.round(boxHeight + getPaddingTop() + getPaddingBottom());
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        if (start != text.length()) {
            moveSelectionToEnd();
        }

        makeBlink();

        if (isAnimationEnable) {
            final boolean isAdd = lengthAfter - lengthBefore > 0;
            if (isAdd) {
                if (mDefaultAddAnimator != null) {
                    mDefaultAddAnimator.end();
                    mDefaultAddAnimator.start();
                }
            }
        }
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);

        if (focused) {
            moveSelectionToEnd();
            makeBlink();
        }
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);

        if (selEnd != getText().length()) {
            moveSelectionToEnd();
        }
    }

    private void moveSelectionToEnd() {
        setSelection(getText().length());
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();

        if (mLineColor == null || mLineColor.isStateful()) {
            updateColors();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();

        updatePaints();
        drawPinView(canvas);

        canvas.restore();
    }

    private void updatePaints() {
        mPaint.setColor(mCurLineColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mLineWidth);
        mTextPaint.setColor(getCurrentTextColor());
    }

    private void drawPinView(Canvas canvas) {
        for (int i = 0; i < mPinItemCount; i++) {
            updateItemRectF(i);
            updateCenterPoint();

            if (mViewType == VIEW_TYPE_RECTANGLE) {
                drawPinBox(canvas, i);
            } else {
                drawPinLine(canvas, i);
            }

            if (DBG) {
                drawAnchorLine(canvas);
            }

            if (getText().length() > i) {
                if (isPasswordInputType(getInputType())) {
                    drawCircle(canvas, i);
                } else {
                    drawText(canvas, i);
                }
            } else if (!TextUtils.isEmpty(getHint()) && getHint().length() == mPinItemCount) {
                drawHint(canvas, i);
            }
        }

        // highlight the next item
        if (isFocused() && getText().length() != mPinItemCount) {
            int index = getText().length();
            updateItemRectF(index);
            updateCenterPoint();

            mPaint.setColor(getLineColorForState(android.R.attr.state_selected));

            drawCursor(canvas);

            if (mViewType == VIEW_TYPE_RECTANGLE) {
                drawPinBox(canvas, index);
            } else {
                drawPinLine(canvas, index);
            }
        }
    }

    private int getLineColorForState(int... states) {
        return mLineColor != null ? mLineColor.getColorForState(states, mCurLineColor) : mCurLineColor;
    }

    private void drawPinBox(Canvas canvas, int i) {
        boolean drawRightCorner = false;
        boolean drawLeftCorner = false;
        if (mPinItemSpacing != 0) {
            drawLeftCorner = drawRightCorner = true;
        } else {
            if (i == 0 && i != mPinItemCount - 1) {
                drawLeftCorner = true;
            }
            if (i == mPinItemCount - 1 && i != 0) {
                drawRightCorner = true;
            }
        }
        updateRoundRectPath(mItemBorderRect, mPinItemRadius, mPinItemRadius, drawLeftCorner, drawRightCorner);
        canvas.drawPath(mPath, mPaint);
    }

    private void drawPinLine(Canvas canvas, int i) {
        boolean l, r;
        l = r = true;
        if (mPinItemSpacing == 0 && mPinItemCount > 1) {
            if (i == 0) {
                // draw only left round
                r = false;
            } else if (i == mPinItemCount - 1) {
                // draw only right round
                l = false;
            } else {
                // draw rect
                l = r = false;
            }
        }
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(((float) mLineWidth) / 10);
        float halfLineWidth = (float) mLineWidth / 2;
        mItemLineRect.set(mItemBorderRect.left, mItemBorderRect.bottom - halfLineWidth, mItemBorderRect.right, mItemBorderRect.bottom + halfLineWidth);

        updateRoundRectPath(mItemLineRect, mPinItemRadius, mPinItemRadius, l, r);
        canvas.drawPath(mPath, mPaint);
    }

    private void drawCursor(Canvas canvas) {
        if (drawCursor) {
            float cx = mItemCenterPoint.x;
            float cy = mItemCenterPoint.y;
            float x = cx;
            float y = cy - mCursorHeight / 2;

            int color = mPaint.getColor();
            float width = mPaint.getStrokeWidth();
            mPaint.setColor(mCursorColor);
            mPaint.setStrokeWidth(mCursorWidth);

            canvas.drawLine(x, y, x, y + mCursorHeight, mPaint);

            mPaint.setColor(color);
            mPaint.setStrokeWidth(width);
        }
    }

    private void updateRoundRectPath(RectF rectF, float rx, float ry, boolean l, boolean r) {
        updateRoundRectPath(rectF, rx, ry, l, r, r, l);
    }

    private void updateRoundRectPath(RectF rectF, float rx, float ry,
                                     boolean tl, boolean tr, boolean br, boolean bl) {
        mPath.reset();

        float l = rectF.left;
        float t = rectF.top;
        float r = rectF.right;
        float b = rectF.bottom;

        float w = r - l;
        float h = b - t;

        float lw = w - 2 * rx;// line width
        float lh = h - 2 * ry;// line height

        mPath.moveTo(l, t + ry);

        if (tl) {
            mPath.rQuadTo(0, -ry, rx, -ry);// top-left corner
        } else {
            mPath.rLineTo(0, -ry);
            mPath.rLineTo(rx, 0);
        }

        mPath.rLineTo(lw, 0);

        if (tr) {
            mPath.rQuadTo(rx, 0, rx, ry);// top-right corner
        } else {
            mPath.rLineTo(rx, 0);
            mPath.rLineTo(0, ry);
        }

        mPath.rLineTo(0, lh);

        if (br) {
            mPath.rQuadTo(0, ry, -rx, ry);// bottom-right corner
        } else {
            mPath.rLineTo(0, ry);
            mPath.rLineTo(-rx, 0);
        }

        mPath.rLineTo(-lw, 0);

        if (bl) {
            mPath.rQuadTo(-rx, 0, -rx, -ry);// bottom-left corner
        } else {
            mPath.rLineTo(-rx, 0);
            mPath.rLineTo(0, -ry);
        }

        mPath.rLineTo(0, -lh);

        mPath.close();
    }

    private void updateItemRectF(int i) {
        float halfLineWidth = (float) mLineWidth / 2;
        float left = getScrollX() + ViewCompat.getPaddingStart(this) + i * (mPinItemSpacing + mPinItemWidth) + halfLineWidth;
        if (mPinItemSpacing == 0 && i > 0) {
            left = left - (mLineWidth) * i;
        }
        float right = left + mPinItemWidth - mLineWidth;
        float top = getScrollY() + getPaddingTop() + halfLineWidth;
        float bottom = top + mPinItemHeight - mLineWidth;

        mItemBorderRect.set(left, top, right, bottom);
    }

    private void drawText(Canvas canvas, int i) {
        Paint paint = getPaintByIndex(i);
        // 1, Rect(4, -39, 20, 0)
        // 您, Rect(2, -47, 51, 3)
        // *, Rect(0, -39, 23, -16)
        // =, Rect(4, -26, 26, -10)
        // -, Rect(1, -19, 14, -14)
        // +, Rect(2, -32, 29, -3)
        drawTextAtBox(canvas, paint, getText(), i);
    }

    private void drawHint(Canvas canvas, int i) {
        Paint paint = getPaintByIndex(i);
        paint.setColor(getCurrentHintTextColor());
        drawTextAtBox(canvas, paint, getHint(), i);
    }

    private void drawTextAtBox(Canvas canvas, Paint paint, CharSequence text, int charAt) {
        paint.getTextBounds(text.toString(), charAt, charAt + 1, mTextRect);
        float cx = mItemCenterPoint.x;
        float cy = mItemCenterPoint.y;
        float x = cx - Math.abs(mTextRect.width()) / 2 - mTextRect.left;
        float y = cy + Math.abs(mTextRect.height()) / 2 - mTextRect.bottom;// always center vertical
        canvas.drawText(text, charAt, charAt + 1, x, y, paint);
    }

    private void drawCircle(Canvas canvas, int i) {
        Paint paint = getPaintByIndex(i);
        float cx = mItemCenterPoint.x;
        float cy = mItemCenterPoint.y;
        canvas.drawCircle(cx, cy, paint.getTextSize() / 2, paint);
    }

    private Paint getPaintByIndex(int i) {
        if (isAnimationEnable && i == getText().length() - 1) {
            mAnimatorTextPaint.setColor(mTextPaint.getColor());
            return mAnimatorTextPaint;
        } else {
            return mTextPaint;
        }
    }

    /**
     * For seeing the font position
     */
    private void drawAnchorLine(Canvas canvas) {
        float cx = mItemCenterPoint.x;
        float cy = mItemCenterPoint.y;
        mPaint.setStrokeWidth(1);
        cx -= mPaint.getStrokeWidth() / 2;
        cy -= mPaint.getStrokeWidth() / 2;

        mPath.reset();
        mPath.moveTo(cx, mItemBorderRect.top);
        mPath.lineTo(cx, mItemBorderRect.top + Math.abs(mItemBorderRect.height()));
        canvas.drawPath(mPath, mPaint);

        mPath.reset();
        mPath.moveTo(mItemBorderRect.left, cy);
        mPath.lineTo(mItemBorderRect.left + Math.abs(mItemBorderRect.width()), cy);
        canvas.drawPath(mPath, mPaint);

        mPath.reset();

        mPaint.setStrokeWidth(mLineWidth);
    }

    private void updateColors() {
        boolean inval = false;

        int color;
        if (mLineColor != null) {
            color = mLineColor.getColorForState(getDrawableState(), 0);
        } else {
            color = getCurrentTextColor();
        }

        if (color != mCurLineColor) {
            mCurLineColor = color;
            inval = true;
        }

        if (inval) {
            invalidate();
        }
    }

    private void updateCenterPoint() {
        float cx = mItemBorderRect.left + Math.abs(mItemBorderRect.width()) / 2;
        float cy = mItemBorderRect.top + Math.abs(mItemBorderRect.height()) / 2;
        mItemCenterPoint.set(cx, cy);
    }

    private static boolean isPasswordInputType(int inputType) {
        final int variation =
                inputType & (EditorInfo.TYPE_MASK_CLASS | EditorInfo.TYPE_MASK_VARIATION);
        return variation
                == (EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_PASSWORD)
                || variation
                == (EditorInfo.TYPE_CLASS_TEXT | EditorInfo.TYPE_TEXT_VARIATION_WEB_PASSWORD)
                || variation
                == (EditorInfo.TYPE_CLASS_NUMBER | EditorInfo.TYPE_NUMBER_VARIATION_PASSWORD);
    }

    @Override
    protected MovementMethod getDefaultMovementMethod() {
        // we don't need arrow key, return null will also disable the copy/paste/cut pop-up menu.
        return null;
    }

    /**
     * Sets the line color for all the states (normal, selected,
     * focused) to be this color.
     *
     * @param color A color value in the form 0xAARRGGBB.
     *              Do not pass a resource ID. To get a color value from a resource ID, call
     *              {@link android.support.v4.content.ContextCompat#getColor(Context, int) getColor}.
     * @attr ref R.styleable#PinView_lineColor
     * @see #setLineColor(ColorStateList)
     * @see #getLineColors()
     */
    public void setLineColor(@ColorInt int color) {
        mLineColor = ColorStateList.valueOf(color);
        updateColors();
    }

    /**
     * Sets the line color.
     *
     * @attr ref R.styleable#PinView_lineColor
     * @see #setLineColor(int)
     * @see #getLineColors()
     */
    public void setLineColor(ColorStateList colors) {
        if (colors == null) {
            throw new NullPointerException();
        }

        mLineColor = colors;
        updateColors();
    }

    /**
     * Gets the line colors for the different states (normal, selected, focused) of the PinView.
     *
     * @attr ref R.styleable#PinView_lineColor
     * @see #setLineColor(ColorStateList)
     * @see #setLineColor(int)
     */
    public ColorStateList getLineColors() {
        return mLineColor;
    }

    /**
     * <p>Return the current color selected for normal line.</p>
     *
     * @return Returns the current item's line color.
     */
    @ColorInt
    public int getCurrentLineColor() {
        return mCurLineColor;
    }

    /**
     * Sets the line width.
     *
     * @attr ref R.styleable#PinView_lineWidth
     * @see #getLineWidth()
     */
    public void setLineWidth(@Px int borderWidth) {
        checkItemRadius();
        mLineWidth = borderWidth;
        requestLayout();
    }

    /**
     * @return Returns the width of the item's line.
     * @see #setLineWidth(int)
     */
    @Px
    public int getLineWidth() {
        return mLineWidth;
    }

    /**
     * Sets the count of items.
     *
     * @attr ref R.styleable#PinView_itemCount
     * @see #getItemCount()
     */
    public void setItemCount(int count) {
        mPinItemCount = count;
        setMaxLength(count);
        requestLayout();
    }

    /**
     * @return Returns the count of items.
     * @see #setItemCount(int)
     */
    public int getItemCount() {
        return mPinItemCount;
    }

    /**
     * Sets the radius of square.
     *
     * @attr ref R.styleable#PinView_itemRadius
     * @see #getItemRadius()
     */
    public void setItemRadius(@Px int itemRadius) {
        checkItemRadius();
        mPinItemRadius = itemRadius;
        requestLayout();
    }

    /**
     * @return Returns the radius of square.
     * @see #setItemRadius(int)
     */
    @Px
    public int getItemRadius() {
        return mPinItemRadius;
    }

    /**
     * Specifies extra space between two items.
     *
     * @attr ref R.styleable#PinView_itemSpacing
     * @see #getItemSpacing()
     */
    public void setItemSpacing(@Px int itemSpacing) {
        mPinItemSpacing = itemSpacing;
        requestLayout();
    }

    /**
     * @return Returns the spacing between two items.
     * @see #setItemSpacing(int)
     */
    @Px
    public int getItemSpacing() {
        return mPinItemSpacing;
    }

    /**
     * Sets the height of item.
     *
     * @attr ref R.styleable#PinView_itemHeight
     * @see #getItemHeight()
     */
    public void setItemHeight(float itemHeight) {
        mPinItemHeight = itemHeight;
        updateCursorHeight();
        requestLayout();
    }

    /**
     * @return Returns the height of item.
     * @see #setItemHeight(float)
     */
    public float getItemHeight() {
        return mPinItemHeight;
    }

    /**
     * Sets the width of item.
     *
     * @attr ref R.styleable#PinView_itemWidth
     * @see #getItemWidth()
     */
    public void setItemWidth(float itemWidth) {
        checkItemRadius();
        mPinItemWidth = itemWidth;
        requestLayout();
    }

    /**
     * @return Returns the width of item.
     * @see #setItemWidth(float)
     */
    public float getItemWidth() {
        return mPinItemWidth;
    }

    /**
     * Specifies whether the text animation should be enabled or disabled.
     * By the default, the animation is disabled.
     *
     * @param enable True to start animation when adding text, false to transition immediately
     */
    public void setAnimationEnable(boolean enable) {
        isAnimationEnable = enable;
    }

    @Override
    public void setTextSize(float size) {
        super.setTextSize(size);
        updateCursorHeight();
    }

    @Override
    public void setTextSize(int unit, float size) {
        super.setTextSize(unit, size);
        updateCursorHeight();
    }

    //region Cursor

    /**
     * Sets the width (in pixels) of cursor.
     *
     * @attr ref R.styleable#PinView_cursorWidth
     * @see #getCursorWidth()
     */
    public void setCursorWidth(@Px int width) {
        checkItemRadius();
        mCursorWidth = width;
        if (isCursorVisible()) {
            invalidateCursor(true);
        }
    }

    /**
     * @return Returns the width (in pixels) of cursor.
     * @see #setCursorWidth(int)
     */
    public int getCursorWidth() {
        return mCursorWidth;
    }

    /**
     * Sets the cursor color.
     *
     * @param color A color value in the form 0xAARRGGBB.
     *              Do not pass a resource ID. To get a color value from a resource ID, call
     *              {@link android.support.v4.content.ContextCompat#getColor(Context, int) getColor}.
     * @attr ref R.styleable#PinView_cursorColor
     * @see #getCursorColor()
     */
    public void setCursorColor(@ColorInt int color) {
        mCursorColor = color;
        if (isCursorVisible()) {
            invalidateCursor(true);
        }
    }

    /**
     * Gets the cursor color.
     *
     * @return Return current cursor color.
     * @see #setCursorColor(int)
     */
    public int getCursorColor() {
        return mCursorColor;
    }

    @Override
    public void setCursorVisible(boolean visible) {
        if (isCursorVisible != visible) {
            isCursorVisible = visible;
            invalidateCursor(isCursorVisible);
            makeBlink();
        }
    }

    @Override
    public boolean isCursorVisible() {
        return isCursorVisible;
    }

    @Override
    public void onScreenStateChanged(int screenState) {
        super.onScreenStateChanged(screenState);
        switch (screenState) {
            case View.SCREEN_STATE_ON:
                resumeBlink();
                break;
            case View.SCREEN_STATE_OFF:
                suspendBlink();
                break;
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        resumeBlink();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        suspendBlink();
    }

    private boolean shouldBlink() {
        return isCursorVisible() && isFocused();
    }

    private void makeBlink() {
        if (shouldBlink()) {
            if (mBlink == null) {
                mBlink = new Blink();
            }
            removeCallbacks(mBlink);
            drawCursor = false;
            postDelayed(mBlink, BLINK);
        } else {
            if (mBlink != null) {
                removeCallbacks(mBlink);
            }
        }
    }

    private void suspendBlink() {
        if (mBlink != null) {
            mBlink.cancel();
            invalidateCursor(false);
        }
    }

    private void resumeBlink() {
        if (mBlink != null) {
            mBlink.uncancel();
            makeBlink();
        }
    }

    private void invalidateCursor(boolean showCursor) {
        if (drawCursor != showCursor) {
            drawCursor = showCursor;
            invalidate();
        }
    }

    private void updateCursorHeight() {
        int delta = 2 * dpToPx(2);
        mCursorHeight = mPinItemHeight - getTextSize() > delta ? getTextSize() + delta : getTextSize();
    }

    private class Blink implements Runnable {
        private boolean mCancelled;

        @Override
        public void run() {
            if (mCancelled) {
                return;
            }

            removeCallbacks(this);

            if (shouldBlink()) {
                invalidateCursor(!drawCursor);
                postDelayed(this, BLINK);
            }
        }

        private void cancel() {
            if (!mCancelled) {
                removeCallbacks(this);
                mCancelled = true;
            }
        }

        void uncancel() {
            mCancelled = false;
        }
    }
    //endregion

    private int dpToPx(float dp) {
        return (int) (dp * getResources().getDisplayMetrics().density + 0.5f);
    }
}