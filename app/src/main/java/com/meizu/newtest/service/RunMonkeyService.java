package com.meizu.newtest.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.meizu.newtest.Util.utils;
import com.meizu.newtest.Util.UtilMethodTools;

/**
 * Created by libinhui on 2016/4/18.
 */
public class RunMonkeyService extends Service {

    private UtilMethodTools umt = new UtilMethodTools();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String pkgname = intent.getStringExtra("pkgname");
        runMonkey rmonkey = new runMonkey(pkgname);
        rmonkey.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        String pid = umt.getPid("monkey");
        utils.execCommand("kill "+pid,false);
    }

    public class runMonkey extends Thread {
        private String pkgname;
        public runMonkey(String pkgname){
            this.pkgname = pkgname;
        }
        @Override
        public void run() {
            utils.execCommand("monkey --throttle 500  -p "+pkgname+" --ignore-crashes --ignore-timeouts -s 500 -v -v -v 999999999  >  /sdcard/monkey.txt &",false);
        }
    }
}
