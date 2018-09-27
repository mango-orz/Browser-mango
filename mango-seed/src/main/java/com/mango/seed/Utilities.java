package com.mango.seed;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

/**
 * @author tic
 * created on 18-9-21
 */
public class Utilities {

    public static final boolean ATLEAST_O = Build.VERSION.SDK_INT > Build.VERSION_CODES.O;
    public static final boolean ATLEAST_L = Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP;
    public static final boolean ATLEAST_KITKAT = Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT;
    public static final boolean ATLEAST_M = Build.VERSION.SDK_INT > Build.VERSION_CODES.M;

    private static final String SHARED_PREFERENCES_KEY = "com.mango.seed.prefs";

    public static boolean isNotNull(Object o) {
        return o != null;
    }

    public static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(
                SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
    }

    public static String getAppCacheDir(Context context) {
        return context.getDir("appcache", 0).getPath();
    }

    public static String getGeolocationDatabaseDir(Context context) {
        return context.getDir("geolocation", 0).getPath();
    }

    public static String getDatabaseDir(Context context) {
        return context.getDir("databases", 0).getPath();
    }
}
