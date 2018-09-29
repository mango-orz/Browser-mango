package com.mango.seed.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

/**
 * @author tic
 * created on 18-9-29
 */
public class MySwipeRefreshLayout extends SwipeRefreshLayout {

    private int mTouchSlop;
    private float mPrevX;
    private MySwipeRefreshLayout.Callback mCallback;

    public MySwipeRefreshLayout(@NonNull Context context) {
        super(context);
        // 200容差
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop() + 200;
    }

    public interface Callback {

        void onBacking(float xDiff);

        void onForwarding(float xDiff);

        void onGoBack();

        void onForward();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPrevX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                final float eventX = event.getX();
                float xDiff = eventX - mPrevX;
                if (xDiff > mTouchSlop || xDiff < -mTouchSlop) {
                    return true;
                }
            default:
                break;
        }

        return super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mCallback == null) {
            return super.onTouchEvent(event);
        }
        float xDiff;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPrevX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                xDiff = event.getX() - mPrevX;
                if (xDiff > mTouchSlop) {
                    mCallback.onBacking(xDiff);
                } else if (xDiff < -mTouchSlop) {
                    mCallback.onForwarding(xDiff);
                }
            case MotionEvent.ACTION_UP:
                float eventX = event.getX();
                xDiff = eventX - mPrevX;

                Log.e("swipeRefresh", "xDiff:" + xDiff);
                if (xDiff > mTouchSlop) {
                    mCallback.onGoBack();
                } else if (xDiff < -mTouchSlop) {
                    mCallback.onForward();
                }
                break;
            default:
                break;
        }

        return super.onTouchEvent(event);
    }

    public void setCallback(Callback callback) {
        this.mCallback = callback;
    }
}
