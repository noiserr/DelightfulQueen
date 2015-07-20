package com.droidsonroids.awesomeprogressbar;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

public class AwesomeProgressBar extends View {
    private static final float DEFAULT_LINE_THICKNESS = 12.0f;
    private static final int DEFAULT_ANIMATION_DURATION = 800;

    private Paint mBackgroundPaint;
    private Paint mProgressBarPaint;
    private ValueAnimator mProgressAnimation;

    private float mProgressValue;
    private int mAnimationDuration;
    private int mBackgroundColor;
    private int mProgressBarColor;

    private float mLineThickness;
    private boolean isAnimationInitialized = false;

    private enum State {RUNNING_STATE, END_STATE, IDLE_STATE}
    private State mState;

    public AwesomeProgressBar(Context context) {
        this(context, null);
    }

    public AwesomeProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs);
    }

    private void initialize(Context context, AttributeSet attrs) {
        if(attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.AwesomeProgressBar);
            try {
                mLineThickness = array.getDimension(R.styleable.AwesomeProgressBar_lineThickness, DEFAULT_LINE_THICKNESS);
                mAnimationDuration = array.getInteger(
                    R.styleable.AwesomeProgressBar_animationDuration, DEFAULT_ANIMATION_DURATION);
                mBackgroundColor = array.getColor(R.styleable.AwesomeProgressBar_backgroundColor,
                    getDefaultBackgroundColor());
                mProgressBarColor = array.getColor(R.styleable.AwesomeProgressBar_progressBarColor, getDefaultProgressBarColor());
            } finally {
                array.recycle();
            }
        }
        mBackgroundPaint = new Paint();
        mBackgroundPaint.setColor(mBackgroundColor);
        mProgressBarPaint = new Paint();
        mProgressBarPaint.setColor(mProgressBarColor);
        mState = State.IDLE_STATE;
    }

    private int getDefaultBackgroundColor() {
        return getResources().getColor(R.color.default_background_color);
    }

    private int getDefaultProgressBarColor() {
        return getResources().getColor(R.color.default_progress_bar_color);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(!isAnimationInitialized) {
            setupAnimations(w, h);
            isAnimationInitialized = true;
        }
    }

    private void setupAnimations(final int w, final int h) {
        mProgressAnimation = ValueAnimator.ofFloat(0, w);
        mProgressAnimation.setDuration(mAnimationDuration);
        mProgressAnimation.setInterpolator(new LinearInterpolator());
        mProgressAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override public void onAnimationUpdate(ValueAnimator animation) {
                mProgressValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        mProgressAnimation.addListener(new Animator.AnimatorListener() {
            @Override public void onAnimationStart(Animator animation) {
                mState = State.RUNNING_STATE;
            }

            @Override public void onAnimationEnd(Animator animation) {
                postDelayed(new Runnable() {
                    @Override public void run() {
                        mState = State.END_STATE;
                    }
                }, 500);
            }

            @Override public void onAnimationCancel(Animator animation) {

            }

            @Override public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0, getHeight() / 2 - mLineThickness / 2, getWidth(),
            getHeight() / 2 + mLineThickness / 2, mBackgroundPaint);
        if(mState == State.RUNNING_STATE) {
            canvas.drawRect(0, getHeight() / 2 - mLineThickness / 2, mProgressValue,
                getHeight() / 2 + mLineThickness / 2, mProgressBarPaint);
        }
    }

    public void play() {
        mProgressAnimation.start();
    }
}
