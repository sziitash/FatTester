package com.meizu.newtest.service;

/**
 * Created by libinhui on 2015/8/10.
 * 发送广播运行uiautomator
 */

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.meizu.newtest.R;
import com.meizu.newtest.Util.UtilMethodTools;
import com.meizu.newtest.Util.utils;

public class RunUiaService extends Service {
    private MyThread myThread;
    private UtilMethodTools umt = new UtilMethodTools();

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        Log.d("LBH", "onBind");
        return null;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onCreate() {
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, RunUiaService.class), 0);
        Notification noti = new Notification.Builder(getApplicationContext())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setWhen(System.currentTimeMillis())
                .setContentTitle("点我没惊喜")
                .setContentText("Uiautomator2测试运行中")
                .setContentIntent(contentIntent)
                .build();
        startForeground(1,noti);
    }

    //	@SuppressWarnings("deprecation")
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("LBH", "onDestroy");
        stopForeground(true);
//        String pid = umt.getPid("nltemtbf");
//        Log.i("benlee",pid);
//        utils.execCommand("kill "+pid,false);
        utils.execCommand("am force-stop com.meizu.nltemtbf",true);
    }


    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("benlee","onStartCommand");
        String testPkg = intent.getStringExtra("testpkg");
        String testClass = intent.getStringExtra("testclass");
        String[] testCase = intent.getStringArrayExtra("testcase");
        myThread = new MyThread(testPkg,testClass,testCase);
        myThread.start();
        return super.onStartCommand(intent, flags, startId);
    }

    private class MyThread extends Thread {
        private String testpkg;
        private String testclass; //定义需要传值进来的参数
        private String[] testcase;

        //        private boolean gorun;
        public MyThread(String tpkg, String tclass, String[] tcase){//,boolean gorun){
            this.testpkg = tpkg;
            this.testclass = tclass;
            this.testcase = tcase;
        }

        @Override
        public void run() {
            if(testcase.equals("0")){
                String commandstr = "am instrument -w -r -e debug false -e class "+testpkg+"."+testclass+" "+testpkg+".test/android.support.test.runner.AndroidJUnitRunner";
                Log.i("benlee",commandstr);
                utils.execCommand(commandstr,true);
            }
            else {
//                String commandstr = "am instrument -w -r -e debug false -e class "+testpkg+"."+testclass+"#"+testcase+" "+testpkg+".test/android.support.test.runner.AndroidJUnitRunner";
                for(String tc:testcase){
                    String commandstr = "am instrument -w -e class "+testclass+"#"+tc+" "+testpkg+"/com.meizu.u2.runner.BaseRunner";
//                    String commandstr = "am instrument -w -e class "+testclass+"#"+tc+" "+testpkg+"/android.support.test.runner.AndroidJUnitRunner";
                    String res = utils.execCommand(commandstr,true).successMsg;
                    if(!res.contains("FAILURES!!!")){
                        Log.i("testResult","测试用例"+tc+"--->true");
                        toastThread tt = new toastThread(tc,true);
                        tt.start();
                    }
                    else {
                        Log.i("testResult","测试用例"+tc+"--->false");
                        toastThread tt = new toastThread(tc,false);
                        tt.start();
                    }
                }

            }
        }
    }

    class toastThread extends Thread{
        private String tcname;
        private boolean testres;
        public toastThread(String tcname, boolean testres) {
            this.tcname = tcname;
            this.testres = testres;
        }
        @Override
        public void run() {
            Looper.prepare();
            if(testres){
                Toast.makeText(getApplicationContext(), "测试用例"+tcname+": true", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(getApplicationContext(), "测试用例"+tcname+": false", Toast.LENGTH_LONG).show();
            }

            Looper.loop();
        }
    }
}
