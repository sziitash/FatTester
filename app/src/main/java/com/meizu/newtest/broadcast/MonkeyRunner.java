package com.meizu.newtest.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by libinhui on 2016/4/18.
 * 启动:adb shell am broadcast -a com.example.broadcast.runmonkey --es pkgname com.meizu.flyme.launcher
 * 停止:adb shell am broadcast -a com.example.broadcast.stoprunmonkey
 */
public class MonkeyRunner extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("com.example.broadcast.runmonkey")){
            String pkgname = intent.getStringExtra("pkgname");
            Intent it = new Intent();
            it.setAction("com.example.runservice.runmonkey");
            it.putExtra("pkgname",pkgname);
            it.setPackage(context.getPackageName());
            context.startService(it);
        }
        else if(intent.getAction().equals("com.example.broadcast.stopmonkey")){
            Intent it = new Intent();
            it.setAction("com.example.runservice.runmonkey");
            it.setPackage(context.getPackageName());
            context.stopService(it);
        }
    }
}
