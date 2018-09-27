package com.browser.mango.ui.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;

/**
 * @author tic
 * created on 18-9-27
 */
public class NavigationView extends NestedScrollView {

    public NavigationView(Context context) {
        super(context);
    }

    public NavigationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }


}
