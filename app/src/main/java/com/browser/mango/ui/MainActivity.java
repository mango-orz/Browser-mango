package com.browser.mango.ui;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;

import com.browser.mango.AppModule;
import com.browser.mango.BaseCompatActivity;
import com.browser.mango.R;
import com.browser.mango.model.BrowserModel;
import com.browser.mango.utils.InputMethods;
import com.browser.mango.utils.Security;

/**
 * @author tic
 */
@Security(value = {Manifest.permission.WRITE_EXTERNAL_STORAGE})
public class MainActivity extends BaseCompatActivity implements View.OnClickListener {

    private View mDefaultView;
    /**
     * 网页容器
     */
    private ViewGroup mContainer;
    private AppCompatEditText mSearchView;

    private BrowserModel mModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setView();
        setListener();
        setValue();
    }

    private void setValue() {
        mModel = AppModule.provideBrowser();
        mModel.bind(this);
        mModel.setCallback(mPageCallback);
    }

    private void setView() {
        mContainer = findViewById(R.id.container_web_view);
        mDefaultView = findViewById(R.id.container_home);

        mSearchView = findViewById(R.id.edit_search);
    }

    private void setListener() {
        mSearchView.setOnClickListener(this);
        mSearchView.addTextChangedListener(mTextWatcher);
        mSearchView.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == KeyEvent.KEYCODE_ENTER
                    || actionId == KeyEvent.KEYCODE_ENDCALL) {
                InputMethods.hide(MainActivity.this);
                String content = mSearchView.getEditableText().toString();
                loadUrl(content);
            }
            return false;
        });

        findViewById(R.id.iv_forward).setOnClickListener(this);
        findViewById(R.id.iv_go_back).setOnClickListener(this);
    }

    private void loadUrl(String url) {
        View view = mModel.addNewPage(url);
        mContainer.addView(view);

        mDefaultView.setVisibility(View.GONE);
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mModel.unBind();
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
            default:
                break;
        }
    }
}
