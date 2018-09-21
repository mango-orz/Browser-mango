package com.browser.mango;

import android.app.Application;

import com.browser.mango.model.BrowserModel;
import com.browser.mango.utils.RxJava;

/**
 * @author tic
 * created on 18-9-21
 */
class AppModule {

    private static AppModule mApp;

    private BrowserModel browserModel;

    private AppModule(Application application) {
    }

    /**
     * 提供网页加载核心模块
     */
    public static BrowserModel provideBrowser() {
        return mApp.browserModel;
    }

    public static void init(Application application) {
        mApp = new AppModule(application);
        // 异步加载全局组件
        RxJava.create()
                .observable(e -> {
                    mApp.browserModel = new BrowserModel();

                })
                .subscribeOn()
                .go();
    }
}
