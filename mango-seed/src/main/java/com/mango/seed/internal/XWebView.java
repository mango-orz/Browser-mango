package com.mango.seed.internal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.mango.seed.Utilities;
import com.mango.seed.XWebViewCallback;
import com.mango.seed.client.ChromeCallback;
import com.mango.seed.client.OnPageCallback;

/**
 * 原生Webkit实现
 * TODO 添加下拉刷新功能
 * TODO 左右滑动 前进后退
 *
 * @author tic
 * created on 18-9-21
 */
public class XWebView extends WebView implements XWebViewCallback {

    private OnPageCallback mOnPageCallback;
    private ChromeCallback mChromeCallback;

    public XWebView(Context context) {
        super(context);
        setup();
    }

    public void setup() {
        setWebChromeClient(new MyWebChromeClient(this));
        setWebViewClient(new MyWebViewClient(this));

        WebSettings settings = getSettings();
        initializeSettings(settings);

        if (Utilities.ATLEAST_O) {
            setRendererPriorityPolicy(RENDERER_PRIORITY_BOUND, true);
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initializeSettings(WebSettings settings) {
        settings.setJavaScriptEnabled(true);

        // configure local storage apis and their database paths.
        settings.setAppCachePath(Utilities.getAppCacheDir(getContext()));
        settings.setDatabasePath(Utilities.getDatabaseDir(getContext()));
        settings.setGeolocationDatabasePath(Utilities.getGeolocationDatabaseDir(getContext()));

        settings.setAppCacheEnabled(true);
        settings.setGeolocationEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setDomStorageEnabled(true);

        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        setVerticalScrollBarEnabled(false);
        setHorizontalScrollBarEnabled(false);
        setVerticalScrollbarOverlay(false);
        setHorizontalScrollbarOverlay(false);

    }

    @Override
    public void setOnPageCallback(OnPageCallback callback) {
        mOnPageCallback = callback;
    }

    @Override
    public void setChromeCallback(ChromeCallback callback) {
        mChromeCallback = callback;
    }

    @Override
    public ChromeCallback getChromeCallback() {
        return mChromeCallback;
    }

    @Override
    public OnPageCallback getOnPageCallback() {
        return mOnPageCallback;
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
    }
}
