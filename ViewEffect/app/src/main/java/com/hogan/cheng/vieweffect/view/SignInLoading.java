package com.hogan.cheng.vieweffect.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by hogan on 2016/8/23.
 */
public class SignInLoading extends View {
    private Context context;
    private final int TEXT_MOVE_DISTANCE = DensityUtil.dp2px(getContext(), 20f);
    private final int LINE_WIDTH_DISTANCE = DensityUtil.dp2px(getContext(), 10f);
    private Paint textPaint;
    private Paint inArcPaint;
    private Paint cirBluePaint;
    private Paint cirWhitePaint;
    private float mChangedRadius;
    private float mChangedLeftAngle;
    private float mChangedRightAngle;
    private float mChangedPosition;
    private float mTransitionLeftX;
    private float mTransitionLeftY;
    private float mTransitionRightX;
    private float mTransitionRightY;
    private int mTotalWidth;
    private int mTotalHeight;
    private float mTotalRadius;
    private int mChangedLineLength;
    private int mChangedLineLengthForBlue;
    private List<Animator> animators = new ArrayList<>();
    private List<Animator> animators1 = new ArrayList<>();
    private List<Animator> animators2 = new ArrayList<>();
    private AnimatorSet animatorSet;

    public SignInLoading(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    private void init() {
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.WHITE);
        cirBluePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        cirBluePaint.setColor(Color.BLUE);
        cirWhitePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        cirWhitePaint.setColor(Color.WHITE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawCircleForBackgroup(canvas);
        drawTopLine(canvas);
        drawTopPoint(canvas);
        drawCenterLine(canvas);
        drawCenterPoint(canvas);
        drawButtomLine(canvas);
        drawButtomPoint(canvas);
        drawCircleForBackgroupForRed(canvas);
        drawCenterLineForBlue(canvas);
    }

    private void drawCircleForBackgroup(Canvas canvas) {
        canvas.drawCircle(mTotalWidth / 2, mTotalHeight / 2, mTotalRadius / 2, cirBluePaint);
    }

    private void drawCircleForBackgroupForRed(Canvas canvas) {
        canvas.drawCircle(mTotalWidth / 2, mTotalHeight / 2, mChangedRadius / 2, cirWhitePaint);
    }

    private void drawTopLine(Canvas canvas) {
        canvas.drawRect(mTotalWidth / 2 - 3 * LINE_WIDTH_DISTANCE + mChangedLineLength, mTotalHeight / 2 - 5 * LINE_WIDTH_DISTANCE / 2, mTotalWidth / 2 + 3 * LINE_WIDTH_DISTANCE - mChangedLineLength, mTotalHeight / 2 - 3 * LINE_WIDTH_DISTANCE / 2, textPaint);
    }

    private void drawTopPoint(Canvas canvas) {
        canvas.drawCircle(mTotalWidth / 2, mTotalHeight / 2 - 2 * LINE_WIDTH_DISTANCE + mChangedPosition, LINE_WIDTH_DISTANCE / 2, textPaint);
    }

    private void drawCenterLine(Canvas canvas) {
        canvas.drawRect(mTotalWidth / 2 - 3 * LINE_WIDTH_DISTANCE + mChangedLineLength, mTotalHeight / 2 - LINE_WIDTH_DISTANCE / 2, mTotalWidth / 2 + 3 * LINE_WIDTH_DISTANCE - mChangedLineLength, mTotalHeight / 2 + LINE_WIDTH_DISTANCE / 2, textPaint);
    }

    private void drawCenterLineForBlue(Canvas canvas) {
        mTransitionLeftX = -(float) (3 * LINE_WIDTH_DISTANCE - 3 * LINE_WIDTH_DISTANCE * Math.cos(mChangedLeftAngle));
        mTransitionLeftY = (float) (3 * LINE_WIDTH_DISTANCE * Math.sin(mChangedLeftAngle));
        mTransitionRightX = (float) (3 * LINE_WIDTH_DISTANCE - 3 * LINE_WIDTH_DISTANCE * Math.cos(mChangedLeftAngle));
        mTransitionRightY = (float) (3 * LINE_WIDTH_DISTANCE * Math.sin(mChangedLeftAngle));
        canvas.save();
        canvas.rotate(mChangedLeftAngle, mTotalWidth / 2, mTotalHeight / 2);
        canvas.drawRect(mTotalWidth / 2 - mChangedLineLengthForBlue, mTotalHeight / 2 - LINE_WIDTH_DISTANCE / 2, mTotalWidth / 2 + mChangedLineLengthForBlue, mTotalHeight / 2 + LINE_WIDTH_DISTANCE / 2, cirBluePaint);
        canvas.restore();
        canvas.rotate(mChangedRightAngle, mTotalWidth / 2, mTotalHeight / 2);
        canvas.drawRect(mTotalWidth / 2 - mChangedLineLengthForBlue, mTotalHeight / 2 - LINE_WIDTH_DISTANCE / 2, mTotalWidth / 2 + mChangedLineLengthForBlue, mTotalHeight / 2 + LINE_WIDTH_DISTANCE / 2, cirBluePaint);
    }

    private void drawCenterPoint(Canvas canvas) {
        canvas.drawCircle(mTotalWidth / 2, mTotalHeight / 2, LINE_WIDTH_DISTANCE / 2, textPaint);
    }

    private void drawButtomLine(Canvas canvas) {
        canvas.drawRect(mTotalWidth / 2 - 3 * LINE_WIDTH_DISTANCE + mChangedLineLength, mTotalHeight / 2 + 3 * LINE_WIDTH_DISTANCE / 2, mTotalWidth / 2 + 3 * LINE_WIDTH_DISTANCE - mChangedLineLength, mTotalHeight / 2 + 5 * LINE_WIDTH_DISTANCE / 2, textPaint);
    }

    private void drawButtomPoint(Canvas canvas) {
        canvas.drawCircle(mTotalWidth / 2, mTotalHeight / 2 + 2 * LINE_WIDTH_DISTANCE - mChangedPosition, LINE_WIDTH_DISTANCE / 2, textPaint);
    }

    public void initAnimator() {
        animators.clear();
        animators1.clear();
        animators2.clear();
        ValueAnimator animator = ValueAnimator.ofInt(0, 3 * LINE_WIDTH_DISTANCE);
        animator.setStartDelay(100);
        animator.setDuration(300);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mChangedLineLength = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        animators.add(animator);
        ValueAnimator mPointAnimator = ValueAnimator.ofFloat(0, 2 * LINE_WIDTH_DISTANCE);
        mPointAnimator.setDuration(300);
        mPointAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mChangedPosition = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        animators.add(mPointAnimator);
        ValueAnimator mScaleToBigAnimator = ValueAnimator.ofFloat(0, mTotalRadius);
        mScaleToBigAnimator.setDuration(300);
        mScaleToBigAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mChangedRadius = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        animators1.add(mScaleToBigAnimator);
        ValueAnimator mLineToBlue = ValueAnimator.ofInt(0, 3 * LINE_WIDTH_DISTANCE);
        mLineToBlue.setDuration(500);
        mLineToBlue.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mChangedLineLengthForBlue = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        animators1.add(mLineToBlue);
        AnimatorSet animatorSet1 = new AnimatorSet();
        animatorSet1.playTogether(animators1);
        animators.add(animatorSet1);
        ValueAnimator mRomateToBeginForLeftAnimator = ValueAnimator.ofFloat(0, 45f);
        mRomateToBeginForLeftAnimator.setDuration(500);
        mRomateToBeginForLeftAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mChangedLeftAngle = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        ValueAnimator mRomateToBeginForRightAnimator = ValueAnimator.ofFloat(0, 135f);
        mRomateToBeginForRightAnimator.setDuration(500);
        mRomateToBeginForRightAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mChangedRightAngle = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        animators2.add(mRomateToBeginForLeftAnimator);
        animators2.add(mRomateToBeginForRightAnimator);
        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.playTogether(animators2);
        animators.add(animatorSet2);
        animatorSet = new AnimatorSet();
        animatorSet.playSequentially(animators);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                startReverseAnimator();
            }
        });
        animatorSet.start();
    }

    public void startReverseAnimator() {
        animators.clear();
        animators1.clear();
        animators2.clear();
        ValueAnimator mRomateToBeginForLeftAnimator = ValueAnimator.ofFloat(45f, 0);
        mRomateToBeginForLeftAnimator.setStartDelay(100);
        mRomateToBeginForLeftAnimator.setDuration(500);
        mRomateToBeginForLeftAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mChangedLeftAngle = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        ValueAnimator mRomateToBeginForRightAnimator = ValueAnimator.ofFloat(135f, 0);
        mRomateToBeginForRightAnimator.setDuration(500);
        mRomateToBeginForRightAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mChangedRightAngle = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        animators2.add(mRomateToBeginForLeftAnimator);
        animators2.add(mRomateToBeginForRightAnimator);
        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.playTogether(animators2);
        animators.add(animatorSet2);
        ValueAnimator mScaleToBigAnimator = ValueAnimator.ofFloat(mTotalRadius, 0);
        mScaleToBigAnimator.setDuration(300);
        mScaleToBigAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mChangedRadius = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        animators1.add(mScaleToBigAnimator);
        ValueAnimator mLineToBlue = ValueAnimator.ofInt(3 * LINE_WIDTH_DISTANCE, 0);
        mLineToBlue.setDuration(500);
        mLineToBlue.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mChangedLineLengthForBlue = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        animators1.add(mLineToBlue);
        AnimatorSet animatorSet1 = new AnimatorSet();
        animatorSet1.playTogether(animators1);
        animators.add(animatorSet1);
        ValueAnimator mPointAnimator = ValueAnimator.ofFloat(2 * LINE_WIDTH_DISTANCE, 0);
        mPointAnimator.setDuration(300);
        mPointAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mChangedPosition = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        animators.add(mPointAnimator);
        ValueAnimator animator = ValueAnimator.ofInt(3 * LINE_WIDTH_DISTANCE, 0);
        animator.setDuration(300);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mChangedLineLength = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        animators.add(animator);
        animatorSet = new AnimatorSet();
        animatorSet.playSequentially(animators);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                initAnimator();
            }
        });
        animatorSet.start();
    }

    public void startOrPauseAnimator() {
        if (animatorSet.isPaused())
            animatorSet.resume();
        else
            animatorSet.pause();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(1000,
                MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTotalWidth = w;
        mTotalHeight = h;
        mTotalRadius = (float) Math.sqrt((double) (w * w + h * h));

    }
}
