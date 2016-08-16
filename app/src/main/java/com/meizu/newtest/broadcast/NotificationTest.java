package com.meizu.newtest.broadcast;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.meizu.newtest.R;


/**
 * Created by libinhui on 2016/4/19.
 */
public class NotificationTest extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("test.noti")) {
            for(int x = 1;x < 11;x++){
                NotificationCompat.Builder noti = new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.good)
                        .setWhen(System.currentTimeMillis())
                        .setContentTitle("测试"+ String.valueOf(x))
                        .setContentText("test");
                NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(x, noti.build());
            }

//            String ver = GetInfoUtils.getAppVersion(context,"com.meizu.media.music");
//            String res = ShellTools.getCurrentActivity();
//            Toast.makeText(context,res,Toast.LENGTH_SHORT).show();

//            runThread rt = new runThread();
//            rt.start();
//            Toast.makeText(context,"hello",Toast.LENGTH_LONG).show();
//            updateSystemLanguage("zh","CN");
        }
    }




//    public class runThread extends Thread{
//        @Override
//        public void run() {
//            SystemClock.sleep(5000);
//            ShellUtils.execCommand("am start -W -n com.android.settings/.Settings",true);
//        }
//    }
}
