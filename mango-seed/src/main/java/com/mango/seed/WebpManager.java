package com.mango.seed;

import android.content.Context;
import android.view.View;
import android.webkit.WebView;

import com.mango.seed.client.ChromeCallback;
import com.mango.seed.client.OnPageCallback;
import com.mango.seed.internal.TbsWebView;
import com.mango.seed.internal.XWebView;
import com.mango.seed.view.MySwipeRefreshLayout;

/**
 * 页面控制类
 *
 * @author tic
 * created on 18-9-21
 */
public class WebpManager {

    private static final boolean CHROMIUM_ENABLE = true;
    private final MySwipeRefreshLayout mContaner;
    private final WebView mWebView;
    private SeedTag mSeed;

    public WebpManager(Context context) {
        if (CHROMIUM_ENABLE) {
            mWebView = new XWebView(context);
        } else {
            mWebView = new TbsWebView(context);
        }

        // wrap refresh view
        mContaner = new MySwipeRefreshLayout(context);
        mContaner.setEnabled(true);

        mContaner.setOnRefreshListener(() -> {
            reload();
            mWebView.postDelayed(()-> mContaner.setRefreshing(false), 500L);
        });

        mContaner.setCallback(new MySwipeRefreshLayout.Callback() {
            @Override
            public void onBacking(float xDiff) {

            }

            @Override
            public void onForwarding(float xDiff) {

            }

            @Override
            public void onGoBack() {
                if (mWebView instanceof XWebView) {
                    ChromeCallback call = ((XWebView) mWebView).getChromeCallback();
                    call.onGoBack();
                }
            }

            @Override
            public void onForward() {
                if (mWebView instanceof XWebView) {
                    ChromeCallback call = ((XWebView) mWebView).getChromeCallback();
                    call.onForward();
                }
            }
        });

        mContaner.addView(mWebView);
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

    public View getRoot() {
        return mContaner;
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

    public String getUrl() {
        return mWebView.getUrl();
    }

    public void reload() {
        mWebView.reload();
    }

    public void destroy() {
        mWebView.destroy();
    }
}
