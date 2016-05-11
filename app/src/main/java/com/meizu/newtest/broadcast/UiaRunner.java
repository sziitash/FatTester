package com.meizu.newtest.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by libinhui on 2016/4/18.
 * am broadcast -a test.StartRunnia --es testpkg pkgname --es testclass classname --testcase casename
 * am broadcast -a test.StopRunuia
 */
public class UiaRunner extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String packagename = context.getPackageName();
        if (intent.getAction().equals("test.StartRunuia")){
            String testpkg = intent.getStringExtra("testpkg");
            String testclass = intent.getStringExtra("testclass");
            String testcase = intent.getStringExtra("testcase");
            Intent serviceIntent = new Intent();
            serviceIntent.setAction("test.runuia");
            serviceIntent.setPackage(packagename);
            context.stopService(serviceIntent);
            serviceIntent.putExtra("testpkg", testpkg);
            serviceIntent.putExtra("testclass", testclass);
            serviceIntent.putExtra("testcase", testcase);
            context.startService(serviceIntent);
        }
        else if(intent.getAction().equals("test.StopRunuia")){
            Intent serviceIntent = new Intent();
            serviceIntent.setAction("test.runuia");
            serviceIntent.setPackage(packagename);
            context.stopService(serviceIntent);
        }
    }
}
