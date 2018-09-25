package com.browser.mango.ui;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
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
    private ViewGroup mContainer;
    private AppCompatEditText mSearchView;

    private BrowserModel mModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setView();
        setValue();
        setListener();
    }

    private void setValue() {
        mModel = AppModule.provideBrowser();
        mModel.bind(this);

//        mContainer.postDelayed(() -> {
//            mDefaultView.setVisibility(View.GONE);
//            View webView = mModel.addNewPage("https://baidu.com");
//            if (Utilities.isNotNull(webView)) {
//                mContainer.addView(webView);
//            }
//        }, 1000L);

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

    @Override
    public void onPermissionRefuse(@NonNull String permissions) {
        super.onPermissionRefuse(permissions);
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
            default:
                break;
        }
    }
}
