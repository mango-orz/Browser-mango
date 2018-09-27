package com.browser.mango.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.mango.seed.Utilities;

import java.util.Set;

/**
 * should not use directly
 * use AppPref instead.
 *
 * @author tic
 * created on 18-9-27
 */
class SpUtils {

    private static SharedPreferences.Editor editor(Context context) {
        return Utilities.getPrefs(context).edit();
    }

    static boolean getBoolean(Context context, String key, boolean defValue) {
        return Utilities.getPrefs(context).getBoolean(key, defValue);
    }

    static void applyBoolean(Context context, String key, boolean value) {
        editor(context).putBoolean(key, value).apply();
    }

    static void commitBoolean(Context context, String key, boolean value) {
        editor(context).putBoolean(key, value).commit();
    }

    static String getString(Context context, String key, String defValue) {
        return Utilities.getPrefs(context).getString(key, defValue);
    }

    static void applyString(Context context, String key, String value) {
        editor(context).putString(key, value).apply();
    }

    static void commitString(Context context, String key, String value) {
        editor(context).putString(key, value).commit();
    }

    static int getInt(Context context, String key, int defValue) {
        return Utilities.getPrefs(context).getInt(key, defValue);
    }

    static void applyInt(Context context, String key, int value) {
        editor(context).putInt(key, value).apply();
    }

    static void commitInt(Context context, String key, int value) {
        editor(context).putInt(key, value).commit();
    }

    static long getLong(Context context, String key, long defValue) {
        return Utilities.getPrefs(context).getLong(key, defValue);
    }

    static void applyLong(Context context, String key, long value) {
        editor(context).putLong(key, value).apply();
    }

    static void commitLong(Context context, String key, long value) {
        editor(context).putLong(key, value).commit();
    }

    static float getFloat(Context context, String key, float defValue) {
        return Utilities.getPrefs(context).getFloat(key, defValue);
    }

    static void applyFloat(Context context, String key, float value) {
        editor(context).putFloat(key, value).apply();
    }

    static void commitFloat(Context context, String key, float value) {
        editor(context).putFloat(key, value).commit();
    }

    static Set<String> getStringSet(Context context, String key, Set<String> defValue) {
        return Utilities.getPrefs(context).getStringSet(key, defValue);
    }

    static void putStringSet(Context context, String key, Set<String> value) {
        editor(context).putStringSet(key, value).apply();
    }

    static void commitStringSet(Context context, String key, Set<String> value) {
        editor(context).putStringSet(key, value).commit();
    }
}

