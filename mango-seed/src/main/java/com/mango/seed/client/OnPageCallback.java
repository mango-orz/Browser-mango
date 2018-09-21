package com.mango.seed.client;

import android.graphics.Bitmap;
import android.webkit.WebView;

/**
 * 页面加载生命周期回调
 *
 * @author tic
 * created on 18-9-21
 */
public interface OnPageCallback {
    /**
     * onPageStarted
     *
     * @param view
     * @param url
     * @param favicon
     */
    void onPageStarted(WebView view, String url, Bitmap favicon);

    /**
     * 页面加载完成
     *
     * @param view
     * @param url
     */
    void onPageFinished(WebView view, String url);

    /**
     * 进度
     *
     * @param view
     * @param newProgress
     */
    void onProgressChanged(WebView view, int newProgress);

    /**
     * title received
     *
     * @param view
     * @param title
     */
    void onReceivedTitle(WebView view, String title);

    /**
     * icon received
     *
     * @param view
     * @param icon
     */
    void onReceivedIcon(WebView view, Bitmap icon);

    /**
     * 网页加载异常， http，ssl，等异常
     *
     * @param view
     * @param errorCode
     * @param description
     * @param failingUrl
     */
    void onReceivedError(WebView view, int errorCode, CharSequence description, String failingUrl);

    /**
     * 更新历史访问记录
     *
     * @param view
     * @param url
     * @param isReload
     */
    void updateVisitedHistory(WebView view, String url, boolean isReload);

}
