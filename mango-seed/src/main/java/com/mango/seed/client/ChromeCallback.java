package com.mango.seed.client;

import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.PermissionRequest;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * ChromeClient 相关UI接口回调
 *
 * @author tic
 * created on 18-9-21
 */
public interface ChromeCallback {
    /**
     * 调用Android的文件选择器
     *
     * @param webView
     * @param filePathCallback
     * @param fileChooserParams
     * @return
     */
    boolean onShowFileChooser(WebView webView,
                              ValueCallback<Uri[]> filePathCallback,
                              WebChromeClient.FileChooserParams fileChooserParams);

    /**
     * location permission
     *
     * @param origin
     * @param callback
     */
    void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback);

    /**
     * 权限请求
     *
     * @param request
     */
    void onPermissionRequest(PermissionRequest request);

    /**
     * 提供视屏加载进度view
     *
     * @return
     */
    View getVideoLoadingProgressView();

    /**
     * 默认视频未播放时的遮罩层
     *
     * @return
     */
    Bitmap getDefaultVideoPoster();

    /**
     * 应用URI跳转
     *
     * @param parse
     */
    void onAppUriDetected(Uri parse);

    void onGoBack();

    void onForward();

    void onBacking(float xDiff);

    void onForwarding(float xDiff);
}
