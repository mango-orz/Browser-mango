package com.browser.mango.utils;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.mango.seed.Utilities;

/**
 * @author tic
 * created on 18-9-25
 */
public class InputMethods {

    public static void hide(Activity activity) {
        View view = activity.getCurrentFocus();
        if (Utilities.isNotNull(view)) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            if (Utilities.isNotNull(inputMethodManager)) {
                inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

}
