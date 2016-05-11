package com.meizu.newtest.Activity;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by libinhui on 2016/2/23.
 */
public class LeakCanaryTest extends Application {

    public static RefWatcher getRefWatcher(Context context) {
        LeakCanaryTest application = (LeakCanaryTest) context.getApplicationContext();
        return application.refWatcher;
    }

    private RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        refWatcher = LeakCanary.install(this);
    }

}
