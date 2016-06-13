package com.meizu.newtest.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.meizu.newtest.Util.okhttpUtils;
import com.squareup.okhttp.OkHttpClient;

import org.json.JSONException;

import java.io.IOException;
import java.util.HashMap;


/**
 * Created by libinhui on 2016/6/7.
 */
public class okhttpTest extends BroadcastReceiver{
    OkHttpClient cl = new OkHttpClient();
//    runThread rt = new runThread(cl,"http://172.17.132.13:8000/mzts/updatephonestatus/",);
    runThread rt = new runThread();

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("test.bc.okhttptest")){
            rt.start();
        }
    }

    private class runThread extends Thread{
        @Override
        public void run() {
            HashMap hm = new HashMap();
            hm.put("sn","850ABLNFSWWK");
            hm.put("phonestatus","1");
            try {
                String res = okhttpUtils.getPostResp(cl,"http://172.17.132.13:8000/mzts/updatephonestatus/",hm,"result");
                Log.i("benlee",res);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
