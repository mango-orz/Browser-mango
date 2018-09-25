package com.browser.mango.model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.PermissionRequest;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.google.common.base.Preconditions;
import com.mango.seed.Utilities;
import com.mango.seed.WebpManager;
import com.mango.seed.client.ChromeCallback;
import com.mango.seed.client.OnPageCallback;

/**
 * 网页浏览管理器
 *
 * @author tic
 * created on 18-9-21
 */
public class BrowserModel {

    private static final String TAG = BrowserModel.class.getSimpleName();

    private Context mContext;

    private Activity mActivity;
    /**
     * 页面缓存
     */
    private ArrayMap<String, WebpManager> mPages = new ArrayMap<>();

    private WebpManager mCurrentPage = null;

    public static final int OPEN_CURRENT = 1;
    /**
     * 新窗口打开
     */
    public static final int OPEN_NEW_BLANK = 2;
    public static final int OPEN_BLANK_BACKGROUND = 3;

    public BrowserModel() {
    }

    public void bind(Activity activity) {
        // 绑定 Activity 生命周期
        // TODO 其他页面是否需要该组件
        this.mActivity = activity;
    }

    public void unBind() {
        // TODO 回收webView资源
    }

    /**
     * 添加新页面
     *
     * @param url 加载链接
     * @return WebView
     */
    public View addNewPage(String url) {
        return addNewPage(url, OPEN_CURRENT);
    }

    public View addNewPage(String url, int type) {
        if (TextUtils.isEmpty(url)) {
            url = "blank";
        }

        if (!url.startsWith("http") && !url.startsWith("https")) {
            // force https
            url = "https://" + url;
        }

        WebpManager manager = create();
        manager.loadUrl(url);

        mPages.put(url, manager);
        mCurrentPage = manager;

        if (type == OPEN_NEW_BLANK) {

        }

        return manager.getWebView();
    }

    public boolean removePage(String url) {
        return mPages.remove(url) != null;
    }

    private WebpManager create() {
        Preconditions.checkNotNull(mActivity,
                "Checking whether you are bind with an activity");
        WebpManager manager = new WebpManager(mActivity);
        manager.setChromeCallback(mChromeCallback);
        manager.setOnPageCallback(mOnPageCallback);
        return manager;
    }

    public WebpManager getCurrentPage() {
        return mCurrentPage;
    }

    public boolean canForward() {
        WebpManager page = getCurrentPage();
        return Utilities.isNotNull(page) && page.canForward();
    }

    public boolean canGoback() {
        WebpManager page = getCurrentPage();
        return Utilities.isNotNull(page) && page.canGoBack();
    }

    public void forward() {
        WebpManager page = getCurrentPage();
        if (Utilities.isNotNull(page)) {
            page.forward();
        }
    }

    public void goback() {
        WebpManager page = getCurrentPage();
        if (Utilities.isNotNull(page)) {
            page.goBack();
        }
    }

    private ChromeCallback mChromeCallback = new ChromeCallback() {
        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
            return false;
        }

        @Override
        public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {

        }

        @Override
        public void onPermissionRequest(PermissionRequest request) {

        }

        @Override
        public View getVideoLoadingProgressView() {
            return null;
        }

        @Override
        public Bitmap getDefaultVideoPoster() {
            return null;
        }

        @Override
        public void onAppUriDetected(Uri parse) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, parse);
                mActivity.startActivity(intent);
            } catch (Exception e) {
                // no related app installed
                Log.i(TAG, "No Activity found with:" + parse.toString());
            }
        }
    };

    private OnPageCallback mOnPageCallback = new OnPageCallback() {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

        }

        @Override
        public void onPageFinished(WebView view, String url) {

        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {

        }

        @Override
        public void onReceivedTitle(WebView view, String title) {

        }

        @Override
        public void onReceivedIcon(WebView view, Bitmap icon) {

        }

        @Override
        public void onReceivedError(WebView view, int errorCode, CharSequence description, String failingUrl) {

        }

        @Override
        public void updateVisitedHistory(WebView view, String url, boolean isReload) {

        }
    };
}
