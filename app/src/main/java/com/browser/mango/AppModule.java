package com.browser.mango;

import android.app.Application;

import com.browser.mango.dao.AppDatabase;
import com.browser.mango.model.BrowserModel;
import com.browser.mango.model.NavModel;
import com.browser.mango.utils.RxJava;

/**
 * @author tic
 * created on 18-9-21
 */
public class AppModule {

    private static AppModule mApp;

    private BrowserModel sBrowserModel;

    private AppModule(Application application) {
    }

    public static AppDatabase provideDB() {
        return AppDatabase.get();
    }

    /**
     * 提供网页加载核心模块
     */
    public static BrowserModel provideBrowser() {
        return mApp.sBrowserModel;
    }

    public static void init(Application application) {
        mApp = new AppModule(application);
        mApp.sBrowserModel = new BrowserModel();
        // 异步加载全局组件
        RxJava.create()
                .observable(e -> {
                    AppDatabase.init(application);
                    new NavModel().init(application);
                })
                .subscribeOn()
                .go();
    }
}
