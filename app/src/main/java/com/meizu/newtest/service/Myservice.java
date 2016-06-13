package com.meizu.newtest.service;

/**
 * Created by libinhui on 2015/8/10.
 * 统计进程cpu/内存使用率，shell命令重定向到sdcard的txt文件记录,200~300毫秒误差
 * am broadcast -a test.cpustart --es pkgName com.meizu.flyme.launcher --es type mem --es stime 1000
 * am broadcast -a test.cpustop
 */

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.meizu.newtest.CsvUtils.csvUtils.getCpuResultThread;
import com.meizu.newtest.Util.utils;

public class Myservice extends Service {
//    private MyThread myThread;
    private getCpuResultThread gct;
    private utils util;

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        Log.d("LBH", "onBind");
        return null;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        Log.d("LBH", "onCreate");

        super.onCreate();
    }

    //	@SuppressWarnings("deprecation")
    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        //在线程中传参，修改gorun状态
        gct.gorun = false;
        Log.d("benlee", "onDestroy");
        super.onDestroy();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        String packagename = intent.getStringExtra("packagename");
        String infotype = intent.getStringExtra("type");
        String sleeptime = intent.getStringExtra("sleeptime");
        Log.d("LBH-service", packagename);
//		boolean gorun = intent.getBooleanExtra("gorun", true);
        //在onStartCommand运行线程，采集数据
        gct = new getCpuResultThread(packagename,infotype,sleeptime,true);//,gorun);   //gorun参数默认为true
        //启动线程
        gct.start();
        return super.onStartCommand(intent, flags, startId);
    }
}
