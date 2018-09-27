package com.browser.mango.model;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.browser.mango.R;
import com.mango.seed.Utilities;

/**
 * @author tic
 * created on 18-9-26
 */
public class ActionModel {

    private MaterialDialog mMenuDialog;

    public View inflate(LayoutInflater inflater, ViewGroup parent) {
        return inflater.inflate(R.layout.view_bottom_action_bar, parent, false);
    }

    public void showMenuDialog(Activity activity) {
        mMenuDialog = new MaterialDialog
                .Builder(activity)
                .customView(R.layout.dialog_menu, false)
                .show();
    }

    public void disMenu() {
        if (Utilities.isNotNull(mMenuDialog) && mMenuDialog.isShowing()) {
            mMenuDialog.dismiss();
        }
    }

}
