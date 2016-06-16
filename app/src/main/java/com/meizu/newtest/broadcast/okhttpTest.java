package com.meizu.newtest.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


import com.squareup.okhttp.OkHttpClient;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import static com.meizu.jarutil.okhttpUtils.getResponObj;

/**
 * Created by libinhui on 2016/6/7.
 */
public class okhttpTest extends BroadcastReceiver{
    OkHttpClient cl = new OkHttpClient();
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("test.bc.okhttptest")){
            HashMap hm = new HashMap();
            HashMap hm2 = new HashMap();
            hm.put("sn","850ABLNFSWWK");
            hm2.put("sn","850ABLNFSWWK");
            hm.put("phonestatus","1");
            String url = "http://172.17.132.13:8000/mzts/updatephonestatus/";
            String url2 = "http://172.17.132.13:8000/mzts/getphonestatus/";
            try {
                JSONObject res = getResponObj(cl,url,hm);
                JSONObject res2 = getResponObj(cl,url2,hm2);
                Log.i("benlee",res.toString()+"|"+res2.toString());
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
