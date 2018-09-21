package com.mango.seed;

import com.mango.seed.client.ChromeCallback;
import com.mango.seed.client.OnPageCallback;

/**
 * 使用WebView组件时必须提供该接口实现
 * 用于页面加载回调
 *
 * @author tic
 * created on 18-9-21
 */
public interface XWebViewCallback {
    /**
     * 添加页面加载生命周期回调
     *
     * @param callback c
     */
    void setOnLoadPageCallback(OnPageCallback callback);

    /**
     * 添加ChromeClient 相关UI回调
     *
     * @param callback c
     */
    void setChromeCallback(ChromeCallback callback);

    /**
     * ChromeCallback
     *
     * @return ChromeCallback
     */
    ChromeCallback getChromeCallback();

    /**
     * OnPageCallback
     *
     * @return OnPageCallback
     */
    OnPageCallback getOnPageCallback();

}
