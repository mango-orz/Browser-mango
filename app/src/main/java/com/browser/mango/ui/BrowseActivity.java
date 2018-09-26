package com.browser.mango.ui;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.browser.mango.AppModule;
import com.browser.mango.BaseCompatActivity;
import com.browser.mango.R;
import com.browser.mango.entities.Nav;
import com.browser.mango.model.ActionModel;
import com.browser.mango.model.BrowserModel;
import com.browser.mango.model.NavModel;
import com.browser.mango.ui.adapter.BaseAdapter;
import com.browser.mango.ui.adapter.NavigationAdapter;
import com.browser.mango.utils.InputMethods;
import com.browser.mango.utils.Security;
import com.mango.seed.Utilities;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * @author tic
 */
@Security(value = {Manifest.permission.WRITE_EXTERNAL_STORAGE})
public class BrowseActivity extends BaseCompatActivity implements View.OnClickListener {

    /**
     * 网页容器
     */
    private ViewGroup mContainer;
    private AppCompatEditText mSearchView;
    private RecyclerView mNavigation;
    private NavigationAdapter mAdapter;

    private BrowserModel mModel;
    private ActionModel mActionModel;

    private Disposable mDispose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);

        setView();
        setValue();
        setListener();
        mDispose = Observable
                .timer(1500L, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                }, Throwable::printStackTrace, this::loadNav);
    }

    private void setView() {
        mContainer = findViewById(R.id.container);
    }

    private void setListener() {
        mSearchView.setOnClickListener(this);
        mSearchView.addTextChangedListener(mTextWatcher);
        mSearchView.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == KeyEvent.KEYCODE_ENTER
                    || actionId == KeyEvent.KEYCODE_ENDCALL) {
                InputMethods.hide(BrowseActivity.this);
                String content = mSearchView.getEditableText().toString();
                loadUrl(content);
            }
            return false;
        });

        findViewById(R.id.iv_forward).setOnClickListener(this);
        findViewById(R.id.iv_go_back).setOnClickListener(this);
        findViewById(R.id.iv_add).setOnClickListener(this);
        findViewById(R.id.iv_marker).setOnClickListener(this);
        findViewById(R.id.iv_menu).setOnClickListener(this);
    }

    private void setValue() {
        mAdapter = new NavigationAdapter(getLayoutInflater());
        mAdapter.setOnItemClickListener(mOnNavClickListener);
        mModel = AppModule.provideBrowser();
        mModel.bind(this);
        mModel.setCallback(mPageCallback);

        mActionModel = new ActionModel();

        loadActionBar(getLayoutInflater());
        loadHomePage(getLayoutInflater());
    }

    private void loadHomePage(LayoutInflater layoutInflater) {
        View homeView = mModel.inflateHomeLayout(layoutInflater, mContainer);
        if (mContainer.getChildCount() > 0) {
            mContainer.removeAllViews();
        }
        mModel.destroyCurrentPage();
        mSearchView = homeView.findViewById(R.id.edit_search);
        mNavigation = homeView.findViewById(R.id.rv_navigation);

        mNavigation.setLayoutManager(new GridLayoutManager(this, 5));
        mNavigation.setAdapter(mAdapter);

        mContainer.addView(homeView);
    }

    private void loadActionBar(LayoutInflater layoutInflater) {
        View actionView = mActionModel.inflate(layoutInflater, mContainer);
        ((ViewGroup) findViewById(R.id.ll_action_bar)).addView(actionView);
    }

    private void loadNav() {
        NavModel mNavModel = new NavModel();
        List<Nav> data = mNavModel.getRecentlyNav();
        if (Utilities.isNotNull(data)) {
            mAdapter.setData(data);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    private void loadUrl(String url) {
        View view = mModel.addNewPage(url);
        mContainer.removeAllViews();
        mContainer.addView(view);
    }

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private BrowserModel.Callbacks mPageCallback = new BrowserModel.Callbacks() {

        @Override
        public void onPageStarted(String url, Bitmap favicon) {

        }

        @Override
        public void onPageFinished(String url) {

        }

        @Override
        public void onProgressChanged(int progress) {

        }

        @Override
        public void onReceivedTitle(String title) {

        }

        @Override
        public void onReceivedIcon(Bitmap icon) {

        }

        @Override
        public void onReceivedError(int errorCode, CharSequence description, String failingUrl) {

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
    };

    @Override
    public void onPermissionRefuse(@NonNull String permissions) {
        super.onPermissionRefuse(permissions);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (Utilities.isNotNull(mDispose)) {
            mDispose.dispose();
        }
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
        } else if(mModel.getCurrentPage().getWebView() != null) {
            loadHomePage(getLayoutInflater());
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

    private BaseAdapter.OnItemClickListener<Nav> mOnNavClickListener = (view, position, data) -> {
        String url = data.getUrl();
        loadUrl(url);
    };
}
