package com.mango.seed.internal;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.HttpAuthHandler;
import android.webkit.RenderProcessGoneDetail;
import android.webkit.SafeBrowsingResponse;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.mango.seed.Utilities;
import com.mango.seed.XWebViewCallback;
import com.mango.seed.client.OnPageCallback;

/**
 * @author tic
 * created on 18-9-21
 */
class MyWebViewClient extends WebViewClient {

    private static final String TAG = MyWebViewClient.class.getSimpleName();

    private final XWebViewCallback mCallback;

    MyWebViewClient(XWebViewCallback callback) {
        mCallback = callback;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        Log.i(TAG, "onPageStarted");

        OnPageCallback callback = mCallback.getOnPageCallback();
        if (Utilities.isNotNull(callback)) {
            callback.onPageStarted(view, url, favicon);
        }
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        Log.i(TAG, "onPageFinished");

        OnPageCallback callback = mCallback.getOnPageCallback();
        if (Utilities.isNotNull(callback)) {
            callback.onPageFinished(view, url);
        }

    }

    @Override
    public void onLoadResource(WebView view, String url) {
        super.onLoadResource(view, url);
        Log.i(TAG, "onLoadResource:" + url);
    }

    @Nullable
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        Log.i(TAG, "shouldInterceptRequest");
        return super.shouldInterceptRequest(view, request);
    }

    @Nullable
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        Log.i(TAG, "shouldInterceptRequest");
        return super.shouldInterceptRequest(view, url);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        Log.i(TAG, "shouldOverrideUrlLoading");
        return true;
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        String url = request.getUrl().toString();
        // Chrome browser keywords, ignore "about:" and "chrome:"
        if (url.startsWith("about:") || url.startsWith("chrome:")) {
            return false;
        }
        view.loadUrl(url);
        return true;
    }

    @Override
    public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
        Log.i(TAG, "shouldOverrideKeyEvent" + event.toString());
        return super.shouldOverrideKeyEvent(view, event);
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description,
                                String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);

        Log.e(TAG, errorCode + " ==> " + description);

        OnPageCallback callback = mCallback.getOnPageCallback();
        if (Utilities.isNotNull(callback)) {
            callback.onReceivedError(view, errorCode, description, failingUrl);
        }

    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
        Log.e(TAG, "onReceivedError:" + error.getErrorCode() + " ==> " + error.getDescription());

        OnPageCallback callback = mCallback.getOnPageCallback();
        if (Utilities.isNotNull(callback)) {
            callback.onReceivedError(view, error.getErrorCode(),
                    error.getDescription(), request.getUrl().toString());
        }

    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse error) {
        super.onReceivedHttpError(view, request, error);
        Log.e(TAG, "onReceivedHttpError:" + error.getReasonPhrase());

        OnPageCallback callback = mCallback.getOnPageCallback();
        if (Utilities.isNotNull(callback)) {
            callback.onReceivedError(view, error.getStatusCode(),
                    error.getReasonPhrase(), request.getUrl().toString());
        }

    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        super.onReceivedSslError(view, handler, error);
        Log.e(TAG, "onReceivedSslError");

        OnPageCallback callback = mCallback.getOnPageCallback();
        if (Utilities.isNotNull(callback)) {
            callback.onReceivedError(view, error.getPrimaryError(),
                    error.getCertificate().toString(), error.getUrl());
        }

    }

    @Override
    public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
        super.doUpdateVisitedHistory(view, url, isReload);
        Log.i(TAG, "doUpdateVisitedHistory:" + url + " isReload:" + isReload);
        OnPageCallback callback = mCallback.getOnPageCallback();
        if (Utilities.isNotNull(callback)) {
            callback.updateVisitedHistory(view, url, isReload);
        }
    }

    @Override
    public void onFormResubmission(WebView view, Message dontResend, Message resend) {
        super.onFormResubmission(view, dontResend, resend);
    }

    @Override
    public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
        super.onReceivedHttpAuthRequest(view, handler, host, realm);
    }

    @Override
    public void onScaleChanged(WebView view, float oldScale, float newScale) {
        super.onScaleChanged(view, oldScale, newScale);
    }

    @Override
    public void onUnhandledKeyEvent(WebView view, KeyEvent event) {
        super.onUnhandledKeyEvent(view, event);
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean onRenderProcessGone(WebView view,
                                       RenderProcessGoneDetail detail) {
        if (!detail.didCrash()) {
            // Renderer was killed because the system ran out of memory.
            // The app can recover gracefully by creating a new WebView instance
            // in the foreground.
            Log.e("MY_APP_TAG", "System killed the WebView rendering process " +
                    "to reclaim memory. Recreating...");
//                if (mWebView != null) {
//                    ViewGroup webViewContainer =
//                            (ViewGroup) findViewById(R.id.my_web_view_container);
//                    webViewContainer.removeView(mWebView);
//                    mWebView.destroy();
//                    mWebView = null;
//                }
            // By this point, the instance variable "mWebView" is guaranteed
            // to be null, so it's safe to reinitialize it.
            // The app continues executing.
            return true;
        }


        // Renderer crashed because of an internal error, such as a memory
        // access violation.
        Log.e("MY_APP_TAG", "The WebView rendering process crashed!");

        // In this example, the app itself crashes after detecting that the
        // renderer crashed. If you choose to handle the crash more gracefully
        // and allow your app to continue executing, you should 1) destroy the
        // current WebView instance, 2) specify logic for how the app can
        // continue executing, and 3) return "true" instead.
        return false;
    }

    // Automatically go "back to safety" when attempting to load a website that
    // Google has identified as a known threat. An instance of WebView calls
    // this method only after Safe Browsing is initialized, so there's no
    // conditional logic needed here.
    @Override
    public void onSafeBrowsingHit(WebView view, WebResourceRequest request,
                                  int threatType, SafeBrowsingResponse callback) {
        // The "true" argument indicates that your app reports incidents like
        // this one to Safe Browsing.
        if (Utilities.ATLEAST_O) {
            callback.backToSafety(true);
            Toast.makeText(view.getContext(), "Unsafe web page blocked.", Toast.LENGTH_LONG).show();
        }
    }
}