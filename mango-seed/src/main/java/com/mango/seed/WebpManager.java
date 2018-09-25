package com.mango.seed;

import android.content.Context;
import android.view.View;
import android.webkit.WebView;

import com.mango.seed.client.ChromeCallback;
import com.mango.seed.client.OnPageCallback;
import com.mango.seed.internal.TbsWebView;
import com.mango.seed.internal.XWebView;

/**
 * 页面控制类
 *
 * @author tic
 * created on 18-9-21
 */
public class WebpManager {

    private static final boolean CHROMIUM_ENABLE = true;

    private SeedTag mSeed;
    private WebView mWebView;

    public WebpManager(Context context) {
        if (CHROMIUM_ENABLE) {
            mWebView = new XWebView(context);
        } else {
            mWebView = new TbsWebView(context);
        }
    }

    public void setChromeCallback(ChromeCallback callback) {
        if (mWebView instanceof XWebView) {
            ((XWebView) mWebView).setChromeCallback(callback);
        }
    }

    public void setOnPageCallback(OnPageCallback callback) {
        if (mWebView instanceof XWebView) {
            ((XWebView) mWebView).setOnPageCallback(callback);
        }
    }

    public View getWebView() {
        return mWebView;
    }

    public void loadUrl(String url) {
        mWebView.loadUrl(url);
    }

    public boolean canForward() {
        return mWebView.canGoForward();
    }

    public boolean canGoBack() {
        return mWebView.canGoBack();
    }

    public void forward() {
        mWebView.goForward();
    }

    public void goBack() {
        mWebView.goBack();
    }
}
