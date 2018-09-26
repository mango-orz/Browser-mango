package com.browser.mango.model;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.GeolocationPermissions;
import android.webkit.PermissionRequest;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.browser.mango.AppModule;
import com.browser.mango.R;
import com.browser.mango.dao.HistoryDao;
import com.browser.mango.entities.History;
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

    public static final String TAG = BrowserModel.class.getSimpleName();
    public static final int OPEN_CURRENT = 1;
    /**
     * 新窗口打开
     */
    public static final int OPEN_NEW_BLANK = 2;
    public static final int OPEN_BLANK_BACKGROUND = 3;

    private Activity mActivity;
    /**
     * 页面缓存
     */
    private ArrayMap<String, WebpManager> mPages = new ArrayMap<>();

    private WebpManager mCurrentPage = null;
    private Callbacks mCallback;

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

    public void destroyCurrentPage() {
        WebpManager page = getCurrentPage();
        if (Utilities.isNotNull(page)) {
            View view = page.getWebView();
            if (view instanceof WebView) {
                ((WebView) view).destroy();
                mPages.remove(page);
            }
            mCurrentPage = null;
        }
    }

    public WebpManager getCurrentPage() {
        return mCurrentPage;
    }

    public boolean canForward() {
        WebpManager page = getCurrentPage();
        return Utilities.isNotNull(page) && page.canForward();
    }

    public boolean canGoBack() {
        WebpManager page = getCurrentPage();
        return Utilities.isNotNull(page) && page.canGoBack();
    }

    public void forward() {
        WebpManager page = getCurrentPage();
        if (Utilities.isNotNull(page)) {
            page.forward();
        }
    }

    public void goBack() {
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
            // TODO permission request
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
            if (Utilities.isNotNull(mCallback)) {
                mCallback.onAppUriDetected(parse);
            }
        }
    };

    private OnPageCallback mOnPageCallback = new OnPageCallback() {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            if (Utilities.isNotNull(mCallback)) {
                mCallback.onPageStarted(url, favicon);
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            if (Utilities.isNotNull(mCallback)) {
                mCallback.onPageFinished(url);
            }
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (Utilities.isNotNull(mCallback)) {
                mCallback.onProgressChanged(newProgress);
            }
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            if (Utilities.isNotNull(mCallback)) {
                mCallback.onReceivedTitle(title);
            }
        }

        @Override
        public void onReceivedIcon(WebView view, Bitmap icon) {
            if (Utilities.isNotNull(mCallback)) {
                mCallback.onReceivedIcon(icon);
            }
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, CharSequence description, String failingUrl) {
            if (Utilities.isNotNull(mCallback)) {
                mCallback.onReceivedError(errorCode, description, failingUrl);
            }
        }

        @Override
        public void updateVisitedHistory(WebView view, String url, boolean isReload) {
            if (isReload) {
                // update visit time
                HistoryDao dao = AppModule.provideDB().historyDao();
                History history = dao.loadByUrl(url);
                history.setDate(System.currentTimeMillis());

                dao.update(history);
            } else {
                History history = new History(view.getTitle(), url, System.currentTimeMillis());
                AppModule.provideDB().historyDao().insert(history);
            }
        }

        @Override
        public void onRenderProcessCrash(WebView view) {
            view.destroy();
            WebpManager page = getCurrentPage();
            if (Utilities.isNotNull(page)) {
                mPages.remove(page);
            }
            if (Utilities.isNotNull(mCallback)) {
                mCallback.onReload(view.getUrl());
            }
        }
    };

    public void setCallback(Callbacks mCallback) {
        this.mCallback = mCallback;
    }

    public View inflateHomeLayout(LayoutInflater layoutInflater, ViewGroup container) {
        return layoutInflater.inflate(R.layout.view_home, container, false);
    }

    public interface Callbacks {
        void onPageStarted(String url, Bitmap favicon);

        void onPageFinished(String url);

        void onProgressChanged(int progress);

        void onReceivedTitle(String title);

        void onReceivedIcon(Bitmap icon);

        void onReceivedError(int errorCode, CharSequence description, String failingUrl);

        void onReload(String url);

        void onAppUriDetected(Uri parse);
    }
}
