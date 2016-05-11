package com.meizu.newtest.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.meizu.newtest.Util.utils;

/**
 * Created by libinhui on 2016/4/18.
 * adb shell am broadcast --user 0 -a com.example.broadcast.installapp --es apkname MzPhoneLocationClient.apk
 */
public class InstallApp extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("com.example.broadcast.installapp")){
            String apkname = intent.getStringExtra("apkname");
            Log.i("benlee",apkname);
            utils.execCommand("pm install /sdcard/"+apkname,false);
        }
    }
}
