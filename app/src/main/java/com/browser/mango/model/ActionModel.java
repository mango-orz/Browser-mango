package com.browser.mango.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.browser.mango.R;

/**
 * @author tic
 * created on 18-9-26
 */
public class ActionModel {

    public View inflate(LayoutInflater inflater, ViewGroup parent) {
        return inflater.inflate(R.layout.view_bottom_action_bar, parent, false);
    }

}
