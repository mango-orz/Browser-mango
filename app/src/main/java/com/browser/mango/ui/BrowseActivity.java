package com.browser.mango.ui;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.browser.mango.AppModule;
import com.browser.mango.BaseCompatActivity;
import com.browser.mango.R;
import com.browser.mango.model.ActionModel;
import com.browser.mango.model.BrowserModel;
import com.browser.mango.utils.InputMethods;
import com.browser.mango.utils.Security;
import com.mango.seed.client.ErrorRes;

/**
 * @author tic
 */
@Security(value = {Manifest.permission.WRITE_EXTERNAL_STORAGE})
public class BrowseActivity extends BaseCompatActivity implements View.OnClickListener {

    /**
     * 网页容器
     */
    private ViewGroup mContainer;
    private ProgressBar mProgressBar;

    private BrowserModel mModel;
    private ActionModel mActionModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);

        setView();
        setValue();
        setListener();
    }

    private void setView() {
        mContainer = findViewById(R.id.container);
        mProgressBar = findViewById(R.id.progress);
    }

    private void setListener() {
        findViewById(R.id.iv_forward).setOnClickListener(this);
        findViewById(R.id.iv_go_back).setOnClickListener(this);
        findViewById(R.id.iv_add).setOnClickListener(this);
        findViewById(R.id.iv_marker).setOnClickListener(this);
        findViewById(R.id.iv_menu).setOnClickListener(this);
    }

    private void setValue() {
        mModel = AppModule.provideBrowser();
        mModel.bind(this);
        mModel.setCallback(mPageCallback);

        mActionModel = new ActionModel();

        loadActionBar(getLayoutInflater());
        loadHomePage();
    }

    private void loadActionBar(LayoutInflater layoutInflater) {
        View actionView = mActionModel.inflate(layoutInflater, mContainer);
        ((ViewGroup) findViewById(R.id.ll_action_bar)).addView(actionView);
    }

    private void loadHomePage() {
        View view = mModel.addHomePage();
        mContainer.removeAllViews();
        mContainer.addView(view);
    }

    public void loadUrl(String url) {
        View view = mModel.addNewPage(url);
        mContainer.removeAllViews();
        mContainer.addView(view);
    }

    private BrowserModel.Callbacks mPageCallback = new BrowserModel.Callbacks() {

        @Override
        public void onPageStarted(String url, Bitmap favicon) {
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(String url) {
            mProgressBar.setVisibility(View.GONE);
        }

        @Override
        public void onProgressChanged(int progress) {
            mProgressBar.setProgress(progress);
        }

        @Override
        public void onReceivedTitle(String title) {

        }

        @Override
        public void onReceivedIcon(Bitmap icon) {

        }

        @Override
        public void onReceivedError(int errorCode, CharSequence description, String failingUrl) {
            switch (errorCode) {
                case ErrorRes.CONNECTION_REFUSED:
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onReload(String url) {
            loadUrl(url);
        }

        @Override
        public void onAppUriDetected(Uri parse) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, parse);
                startActivity(intent);
            } catch (Exception e) {
                // no related app installed
                Log.i(BrowserModel.TAG, "No Activity found with:" + parse.toString());
            }
        }

        @Override
        public void onSearchStart(String content) {
            InputMethods.hide(BrowseActivity.this);
            loadUrl(content);
        }
    };

    @Override
    public void onPermissionRefuse(@NonNull String permissions) {
        super.onPermissionRefuse(permissions);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mModel.unBind();
    }

    @Override
    public void onBackPressed() {
        if (mModel.canGoBack()) {
            mModel.goBack();
        } else if (mModel.getCurrentPage().getWebView() != null) {
            loadHomePage();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.iv_forward: {
                if (mModel.canForward()) {
                    mModel.forward();
                } else {
                    // home --> web page
                }
            }
            break;
            case R.id.iv_go_back: {
                if (mModel.canGoBack()) {
                    mModel.goBack();
                } else {
                    // go back to home
                }
            }
            break;
            case R.id.iv_add:
                break;
            case R.id.iv_marker:
                break;
            case R.id.iv_menu:
                break;
            default:
                break;
        }
    }
}
