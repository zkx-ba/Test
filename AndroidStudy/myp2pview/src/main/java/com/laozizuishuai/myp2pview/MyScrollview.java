package com.laozizuishuai.myp2pview;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

public class MyScrollview extends ScrollView {

    private View innerView;
    private float y;
    Rect normal = new Rect();
    private boolean animationFinsh = true;

    public MyScrollview(Context context) {
        super(context);
    }

    public MyScrollview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (innerView == null) {
            return super.onTouchEvent(ev);
        } else {
            commonTouchEvent(ev);
        }

        return super.onTouchEvent(ev);
    }

    private void commonTouchEvent(MotionEvent ev) {
        if (animationFinsh) {
            int action = ev.getAction();
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    y = ev.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    float prey = y == 0 ? ev.getY() : y;
                    float nowy = ev.getY();
                    int detaiy = (int) (prey - nowy);
                    y = nowy;
                    //操做View进行拖动detaiY的一半
                    if (isNeedMove()) {
                        //布局改变之前记录下位置
                        if (normal.isEmpty()) {
                            normal.set(innerView.getLeft(), innerView.getTop(), innerView.getRight(), innerView.getBottom());
                        }
                        innerView.layout(innerView.getLeft(), innerView.getTop() - detaiy / 2, innerView.getRight(), innerView.getBottom() - detaiy / 2);
                    }


                    break;
                case MotionEvent.ACTION_UP:
                    y = 0;
                    //抬起手指回滚到原始位置
                    if (isNeedAnimation()) {
                        TranslateAnimation translateAnimation = new TranslateAnimation(0, 0, 0, normal.top - innerView.getTop());
                        translateAnimation.setDuration(200);
                        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                                animationFinsh = false;
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                innerView.clearAnimation();
                                animationFinsh = true;
                                innerView.layout(normal.left, normal.top, normal.right, normal.bottom);
                                normal.setEmpty();

                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                        innerView.startAnimation(translateAnimation);
                    }

                    break;
            }


        }
    }

    /**
     * 判断是否需要回滚
     *
     * @return
     */
    private boolean isNeedAnimation() {
        return !normal.isEmpty();
    }

    private boolean isNeedMove() {
        int offest = innerView.getMeasuredHeight() - getHeight();
        int scrollY = getScrollY();
        if (scrollY == 0 || scrollY == offest) {
            return true;
        }
        return false;
    }

    /**
     * 布局加载完成调用的方法
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int childCount = getChildCount();
        if (childCount > 0) {
            innerView = getChildAt(0);
        }
    }
}
