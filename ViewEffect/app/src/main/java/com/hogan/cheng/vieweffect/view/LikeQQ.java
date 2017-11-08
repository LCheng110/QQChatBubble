package com.hogan.cheng.vieweffect.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by hogan on 2016/8/30.
 */
public class LikeQQ extends View {

    private List<Animator> animators = new ArrayList<>();
    private Context context;
    private int totalWidth;
    private int totalHeight;
    private Path path;
    private Paint circleRedPaint;
    private Paint circleBoomPaint;
    private boolean allowCancel = true;
    private boolean isBoomed = false;
    private float clickX;
    private float clickY;
    private float moveX;
    private float moveY;
    private float circleX;
    private float circleY;
    private float radiusSmall = DensityUtil.dp2px(getContext(), 15);
    private float radiusBig = DensityUtil.dp2px(getContext(), 15);
    private float radiusBoomCenter = DensityUtil.dp2px(getContext(), 13);
    private float radiusBoomEdge = DensityUtil.dp2px(getContext(), 12);
    private float moveAngle;
    private float interToPointLength;
    private float assistX;
    private float assistY;
    private float smallRightX;
    private float smallRightY;
    private float smallLeftX;
    private float smallLeftY;
    private float bigRightX;
    private float bigRightY;
    private float bigLeftX;
    private float bigLeftY;

    public LikeQQ(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        totalWidth = windowManager.getDefaultDisplay().getWidth();
        totalHeight = windowManager.getDefaultDisplay().getHeight();
        circleX = totalWidth / 2;
        circleY = totalWidth / 4;
        moveX = totalWidth / 2;
        moveY = totalWidth / 4;
        path = new Path();
        circleRedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circleRedPaint.setColor(Color.RED);
        //circleRedPaint.setStyle(Paint.Style.STROKE);
        circleBoomPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circleBoomPaint.setColor(Color.GRAY);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isBoomed) {
            if (allowCancel) {
                canvas.drawCircle(circleX, circleY, radiusSmall, circleRedPaint);
                canvas.drawPath(path, circleRedPaint);
            }
            canvas.drawCircle(moveX, moveY, radiusBig, circleRedPaint);
        } else {
            canvas.drawCircle(moveX, moveY, radiusBoomCenter, circleBoomPaint);
            canvas.drawCircle(moveX + 5, moveY - 33, radiusBoomEdge, circleBoomPaint);
            canvas.drawCircle(moveX - 30, moveY - 14, radiusBoomEdge, circleBoomPaint);
            canvas.drawCircle(moveX - 27, moveY + 23, radiusBoomEdge, circleBoomPaint);
            canvas.drawCircle(moveX + 22, moveY + 28, radiusBoomEdge, circleBoomPaint);
            canvas.drawCircle(moveX + 31, moveY - 16, radiusBoomEdge, circleBoomPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isBoomed = false;
                radiusBoomCenter = DensityUtil.dp2px(getContext(), 6);
                radiusBoomEdge = DensityUtil.dp2px(getContext(), 5);
                clickX = event.getX();
                clickY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (clickX >= circleX - radiusBig && clickX <= circleX + radiusBig && clickY <= circleY + radiusBig && clickY >= circleY - radiusBig) {
                    moveX = event.getX();
                    moveY = event.getY();
                    interToPointLength = (float) Math.sqrt((moveX - circleX) * (moveX - circleX) + (moveY - circleY) * (moveY - circleY));
                    radiusSmall = radiusBig - interToPointLength / 6;
                    Log.i("ssss", "radiusSmall:  " + radiusSmall + "  interToPointLength:   " + interToPointLength);
                    if (radiusSmall <= 0) {
                        path.reset();
                        allowCancel = false;
                    } else {
                        if (moveX >= circleX && moveY >= circleY) {
                            moveAngle = (float) (Math.atan((moveY - circleY) / (moveX - circleX)));
                            smallRightX = circleX + radiusSmall * (float) Math.sin(moveAngle);
                            smallRightY = circleY - radiusSmall * (float) Math.cos(moveAngle);
                            smallLeftX = circleX - radiusSmall * (float) Math.sin(moveAngle);
                            smallLeftY = circleY + radiusSmall * (float) Math.cos(moveAngle);
                            bigRightX = moveX + radiusBig * (float) Math.sin(moveAngle);
                            bigRightY = moveY - radiusBig * (float) Math.cos(moveAngle);
                            bigLeftX = moveX - radiusBig * (float) Math.sin(moveAngle);
                            bigLeftY = moveY + radiusBig * (float) Math.cos(moveAngle);
                        } else if (moveX < circleX) {
                            moveAngle = (float) (Math.atan((moveY - circleY) / (moveX - circleX)));
                            smallRightX = circleX + radiusSmall * (float) Math.sin(moveAngle);
                            smallRightY = circleY - radiusSmall * (float) Math.cos(moveAngle);
                            smallLeftX = circleX - radiusSmall * (float) Math.sin(moveAngle);
                            smallLeftY = circleY + radiusSmall * (float) Math.cos(moveAngle);
                            bigRightX = moveX + radiusBig * (float) Math.sin(moveAngle);
                            bigRightY = moveY - radiusBig * (float) Math.cos(moveAngle);
                            bigLeftX = moveX - radiusBig * (float) Math.sin(moveAngle);
                            bigLeftY = moveY + radiusBig * (float) Math.cos(moveAngle);
                        } else if (moveX >= circleX && moveY < circleY) {
                            moveAngle = (float) (Math.atan((moveY - circleY) / (moveX - circleX)));
                            smallRightX = circleX + radiusSmall * (float) Math.sin(moveAngle);
                            smallRightY = circleY - radiusSmall * (float) Math.cos(moveAngle);
                            smallLeftX = circleX - radiusSmall * (float) Math.sin(moveAngle);
                            smallLeftY = circleY + radiusSmall * (float) Math.cos(moveAngle);
                            bigRightX = moveX + radiusBig * (float) Math.sin(moveAngle);
                            bigRightY = moveY - radiusBig * (float) Math.cos(moveAngle);
                            bigLeftX = moveX - radiusBig * (float) Math.sin(moveAngle);
                            bigLeftY = moveY + radiusBig * (float) Math.cos(moveAngle);
                        }

                        assistX = (moveX + circleX) / 2;
                        assistY = (moveY + circleY) / 2;
                        path.reset();
                        path.moveTo(smallLeftX, smallLeftY);
                        path.quadTo(assistX, assistY, bigLeftX, bigLeftY);
                        path.lineTo(bigRightX, bigRightY);
                        path.quadTo(assistX, assistY, smallRightX, smallRightY);
                        path.close();
                    }
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                if (allowCancel) {
                    path.reset();
                    initCircle(moveX, moveY);
                } else {
                    allowCancel = true;
                    circleX = moveX;
                    circleY = moveY;
                    isBoomed = true;
                    invalidate();
                    startBoom(radiusBoomCenter, radiusBoomEdge);
                }
                break;
            default:
                break;
        }
        return true;
    }

    private void initCircle(float currentPointX, float currentPointY) {
        animators.clear();
        ValueAnimator valueAnimator1 = ValueAnimator.ofFloat(currentPointX, circleX);
        valueAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                moveX = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        ValueAnimator valueAnimator2 = ValueAnimator.ofFloat(currentPointY, circleY);
        valueAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                moveY = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        animators.add(valueAnimator1);
        animators.add(valueAnimator2);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(200);
        Interpolator interpolator = new OvershootInterpolator();
        animatorSet.setInterpolator(interpolator);
        animatorSet.playTogether(animators);
        animatorSet.start();
    }

    private void startBoom(float currentCenter, float currentEdge) {
        animators.clear();
        ValueAnimator valueAnimator1 = ValueAnimator.ofFloat(currentCenter, 0);
        valueAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                radiusBoomCenter = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        ValueAnimator valueAnimator2 = ValueAnimator.ofFloat(currentEdge, 0);
        valueAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                radiusBoomEdge = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        animators.add(valueAnimator1);
        animators.add(valueAnimator2);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration(300);
        Interpolator interpolator = new AccelerateInterpolator();
        animatorSet.setInterpolator(interpolator);
        animatorSet.playTogether(animators);
        animatorSet.start();
    }
}
