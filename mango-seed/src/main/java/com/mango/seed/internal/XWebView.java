package com.mango.seed.internal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.mango.seed.Utilities;
import com.mango.seed.XWebViewCallback;
import com.mango.seed.client.ChromeCallback;
import com.mango.seed.client.OnPageCallback;

import java.util.regex.Pattern;

/**
 * 原生Webkit实现
 *
 * @author tic
 * created on 18-9-21
 */
public class XWebView extends WebView implements XWebViewCallback {

    private static final Pattern WEBVIEW_VERSION_PATTERN = Pattern.compile("(Chrome/)([\\d\\.]+)\\s");

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
}
