package com.browser.mango.utils;

import android.content.Context;

/**
 * SharePreference
 *
 * @author tic
 * created on 18-9-27
 */
public class AppPref {
    private static final String KEY_APP_INITED = "key_app_inited";

    public static void setAppInited(Context context, boolean value) {
        SpUtils.applyBoolean(context, KEY_APP_INITED, value);
    }

    public static boolean getAppInited(Context context) {
        return SpUtils.getBoolean(context, KEY_APP_INITED, false);
    }



}
