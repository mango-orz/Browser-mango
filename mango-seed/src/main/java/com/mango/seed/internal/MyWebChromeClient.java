package com.mango.seed.internal;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.GeolocationPermissions;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.PermissionRequest;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.mango.seed.Utilities;
import com.mango.seed.XWebViewCallback;
import com.mango.seed.client.ChromeCallback;
import com.mango.seed.client.OnPageCallback;

import java.util.Arrays;

/**
 * @author tic
 * created on 18-9-21
 */
class MyWebChromeClient extends WebChromeClient {

    private static final String TAG = MyWebChromeClient.class.getSimpleName();

    private final XWebViewCallback mCallback;

    MyWebChromeClient(XWebViewCallback callback) {
        mCallback = callback;
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        Log.i(TAG, "onProgressChanged:" + newProgress);

        OnPageCallback callback = mCallback.getOnPageCallback();
        if (Utilities.isNotNull(callback)) {
            callback.onProgressChanged(view, newProgress);
        }
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        Log.i(TAG, "onReceivedTitle:" + title);

        OnPageCallback callback = mCallback.getOnPageCallback();
        if (Utilities.isNotNull(callback)) {
            callback.onReceivedTitle(view, title);
        }
    }

    @Override
    public void onReceivedIcon(WebView view, Bitmap icon) {
        Log.i(TAG, "onReceivedIcon");

        OnPageCallback callback = mCallback.getOnPageCallback();
        if (Utilities.isNotNull(callback)) {
            callback.onReceivedIcon(view, icon);
        }
    }

    @Override
    public void onReceivedTouchIconUrl(WebView view, String url,
                                       boolean precomposed) {
        Log.i(TAG, "onReceivedTouchIconUrl:" + precomposed);

        // Notify the host application of the url for an apple-touch-icon.
    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        Log.e(TAG, "onJsAlert:" + message + " ===> " + result);
        return super.onJsAlert(view, url, message, result);
    }

    @Override
    public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
        Log.i(TAG, "onJsConfirm:" + message);
        return super.onJsConfirm(view, url, message, result);
    }

    @Override
    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
        Log.i(TAG, "onJsPrompt:" + message + " ====> " + defaultValue);
        return super.onJsPrompt(view, url, message, defaultValue, result);
    }

    @Override
    public boolean onJsBeforeUnload(WebView view, String url, String message, JsResult result) {
        Log.i(TAG, "onJsBeforeUnload:" + message);
        return super.onJsBeforeUnload(view, url, message, result);
    }

    @Override
    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        Log.e(TAG, "onConsoleMessage:" + consoleMessage);
        return super.onConsoleMessage(consoleMessage);
    }

    @Override
    public Bitmap getDefaultVideoPoster() {
        Bitmap bitmap = null;
        ChromeCallback callback = mCallback.getChromeCallback();
        if (Utilities.isNotNull(callback)) {
            bitmap = callback.getDefaultVideoPoster();
        }
        return Utilities.isNotNull(bitmap) ? bitmap : super.getDefaultVideoPoster();
    }

    @Nullable
    @Override
    public View getVideoLoadingProgressView() {
        View view = null;
        ChromeCallback callback = mCallback.getChromeCallback();
        if (Utilities.isNotNull(callback)) {
            view = callback.getVideoLoadingProgressView();
        }
        return Utilities.isNotNull(view) ? view : super.getVideoLoadingProgressView();
    }

    @Override
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
        Log.i(TAG, "onShowFileChooser");

        ChromeCallback callback = mCallback.getChromeCallback();
        if (Utilities.isNotNull(callback)) {
            return callback.onShowFileChooser(webView, filePathCallback, fileChooserParams);
        }

        return super.onShowFileChooser(webView, filePathCallback, fileChooserParams);
    }

    @Override
    public void onGeolocationPermissionsShowPrompt(String origin,
                                                   GeolocationPermissions.Callback callback) {
        Log.i(TAG, "onGeolocationPermissionsShowPrompt:" + origin);

        ChromeCallback chrome = mCallback.getChromeCallback();
        if (Utilities.isNotNull(chrome)) {
            chrome.onGeolocationPermissionsShowPrompt(origin, callback);
        }
    }

    @Override
    public void onPermissionRequest(PermissionRequest request) {
        if (Utilities.ATLEAST_L) {
            Log.i(TAG, "onPermissionRequest:" + Arrays.toString(request.getResources()));
        }

        ChromeCallback chrome = mCallback.getChromeCallback();
        if (Utilities.isNotNull(chrome)) {
            chrome.onPermissionRequest(request);
        }
    }
}
