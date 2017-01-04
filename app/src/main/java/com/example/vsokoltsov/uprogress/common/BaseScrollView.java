package com.example.vsokoltsov.uprogress.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

/**
 * Created by vsokoltsov on 04.01.17.
 */

public class BaseScrollView extends ScrollView {
    private ScrollViewInt screen;

    public void setScreen(ScrollViewInt screen) {
        this.screen = screen;
    }

    public BaseScrollView(Context context) {
        super(context);
    }

    public BaseScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        // Grab the last child placed in the ScrollView, we need it to determinate the bottom position.
        View view = (View) getChildAt(getChildCount()-1);

        // Calculate the scrolldiff
        int diff = (view.getBottom()-(getHeight()+getScrollY()));

        // if diff is zero, then the bottom has been reached
        if( diff == 0 )
        {
            // notify that we have reached the bottom
            screen.scrolledDown();
        }
        super.onScrollChanged(l, t, oldl, oldt);
    }
}
