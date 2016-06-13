package com.meizu.newtest.broadcast;

/**
 * Created by libinhui on 2015/8/10.
 * 广播调用service汇总
 */
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class Mybroadcast extends BroadcastReceiver {

    private static final String STATICACTION = "test.cpustart";
    private static final String STATICACTION2 = "test.cpustop";
    @Override
    public void onReceive(Context context, Intent intent)  {
        String packagename = context.getPackageName();
//		Log.d("LBH-broadcast","1");
        if (intent.getAction().equals(STATICACTION)){
            String pkgname = intent.getStringExtra("pkgName");
            String type = intent.getStringExtra("type");
            String sleeptime = intent.getStringExtra("stime");
            //创建一个新的intent对象，并设置相关参数等启动service
            Intent serviceIntent = new Intent();
            //设置intent的action
            serviceIntent.setAction("test.cpuservice");
            //设置intent的参数
            serviceIntent.putExtra("packagename", pkgname);
            serviceIntent.putExtra("type", type);
            serviceIntent.putExtra("sleeptime", sleeptime);
            serviceIntent.setPackage(packagename);
            //通过context启动service
            context.startService(serviceIntent);
        }
        else if (intent.getAction().equals(STATICACTION2)){
            Intent serviceIntent = new Intent();
            serviceIntent.setAction("test.cpuservice");
            serviceIntent.setPackage(packagename);
            context.stopService(serviceIntent);
        }
    }
}
