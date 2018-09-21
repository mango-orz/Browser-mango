package com.browser.mango.ui;

import android.os.Bundle;

import com.browser.mango.BaseCompatActivity;
import com.browser.mango.R;
import com.mango.seed.internal.XWebView;

/**
 * @author tic
 */
public class MainActivity extends BaseCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        XWebView webView = findViewById(R.id.web_view);

        webView.loadUrl("https://baidu.com");
    }
}
