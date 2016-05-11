package com.meizu.newtest.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by libinhui on 2016/5/9.
 */
public class addContant extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("test.bc.addcontant")){
            Intent serviceintent = new Intent();
            String name = intent.getStringExtra("name");
            String number = intent.getStringExtra("number");
            serviceintent.setAction("test.addcontant");
            serviceintent.putExtra("name",name);
            serviceintent.putExtra("number",number);
            serviceintent.setPackage(context.getPackageName());
            context.startService(serviceintent);
        }
    }
}
