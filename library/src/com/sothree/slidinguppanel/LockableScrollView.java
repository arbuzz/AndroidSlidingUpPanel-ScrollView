package com.sothree.slidinguppanel;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

/**
 * Date: 8/19/14
 * Time: 4:47 PM
 */
public class LockableScrollView extends ScrollView {

    // true if we can scroll (not locked)
    // false if we cannot scroll (locked)
    private boolean mScrollable = true;

    private boolean topPosition = true;

    public LockableScrollView(Context context) {
        super(context);
    }

    public LockableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LockableScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setScrollingEnabled(boolean enabled) {
        mScrollable = enabled;
    }

    public boolean isScrollable() {
        return mScrollable;
    }

    public boolean isTopPosition() {
        return topPosition;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.i("Panel", "Scrollview touch");

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // if we can scroll pass the event to the superclass
                if (mScrollable) {
                    boolean result = super.onTouchEvent(ev);

                    return result;
                }
                // only continue to handle the touch event if scrolling enabled

                return mScrollable; // mScrollable is always false at this point
            default:
                boolean result = super.onTouchEvent(ev);

                return result;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // Don't do anything with intercepted touch events if
        // we are not scrollable
//        Log.i("YBus-Panel", "ScrollView onInterceptTouchEvent " + mScrollable);

        if (!mScrollable) return false;
        else return super.onInterceptTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        // Grab the last child placed in the ScrollView, we need it to determinate the bottom position.
        View view = getChildAt(0);

        // Calculate the scrolldiff
        int diff = (view.getTop() - getScrollY());

        // if diff is zero, then the top has been reached
        topPosition = diff == 0;

        super.onScrollChanged(l, t, oldl, oldt);
    }
}
