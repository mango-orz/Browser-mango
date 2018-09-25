package com.browser.mango;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.browser.mango.utils.PermissionManager;

/**
 * @author tic
 * created on 18-9-21
 */
public abstract class BaseCompatActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new PermissionManager().requestPermission(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * for override
     */
    public void onPermissionRefuse(@NonNull String permissions) {

    }
}
