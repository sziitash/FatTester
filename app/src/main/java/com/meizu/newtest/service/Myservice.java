package com.meizu.newtest.service;

/**
 * Created by libinhui on 2015/8/10.
 * 统计进程cpu/内存使用率，shell命令重定向到sdcard的txt文件记录
 * am broadcast -a test.cpustart --es pkgName com.meizu.flyme.launcher
 * am broadcast -a test.cpustop
 */

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.csvreader.CsvWriter;
import com.meizu.newtest.Util.utils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Myservice extends Service {
    private MyThread myThread;
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
        this.myThread.gorun = false;
        Log.d("benlee", "onDestroy");
        super.onDestroy();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        String packagename = intent.getStringExtra("packagename");
        Log.d("LBH-service", packagename);
//		boolean gorun = intent.getBooleanExtra("gorun", true);
        //在onStartCommand运行线程，采集数据
        this.myThread = new MyThread(packagename,true);//,gorun);   //gorun参数默认为true
        //启动线程
        this.myThread.start();
        return super.onStartCommand(intent, flags, startId);
    }

    private void createResultcsv(CsvWriter wr){
        String[] csvtitle = {"CpuInfo"};
        try {
            wr.writeRecord(csvtitle);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] resultinfo = {"次数","cpu使用率"};
        try {
            wr.writeRecord(resultinfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getNowSysDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    private class MyThread extends Thread {
        private String pkgName; //定义需要传值进来的参数
        private boolean gorun;
        public MyThread(String pkgName, boolean gorun){//,boolean gorun){
            this.pkgName = pkgName;
            this.gorun = gorun;
        }

        @Override
        public void run() {
            CsvWriter wr = new CsvWriter("/sdcard/"+getNowSysDate()+"cpu.csv", ',', Charset.forName("GBK"));
            createResultcsv(wr);
            int x = 1;
            while (gorun) {
                String resText = utils.execCommand("top -n 1 -d 0.1|grep "+pkgName,true).successMsg;
                String res = resText.split("\\%")[0].split(" ")[6];
                String[] result = {String.valueOf(x),res+"%"};
                try {
                    wr.writeRecord(result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                x++;
            }
            wr.close();
        }
    }
}
