package com.browser.mango;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

/**
 * @author tic
 * created on 18-9-21
 */
public class SeedApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        AppModule.init(this);

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                Log.i("Seed", "onActivityCreated");
            }

            @Override
            public void onActivityStarted(Activity activity) {
                Log.i("Seed", "onActivityStarted");
            }

            @Override
            public void onActivityResumed(Activity activity) {
                Log.i("Seed", "onActivityResumed");
            }

            @Override
            public void onActivityPaused(Activity activity) {
                Log.i("Seed", "onActivityPaused");
            }

            @Override
            public void onActivityStopped(Activity activity) {
                Log.i("Seed", "onActivityStopped");
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                Log.i("Seed", "onActivitySaveInstanceState");
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                Log.i("Seed", "onActivityDestroyed");
            }
        });
    }


}
