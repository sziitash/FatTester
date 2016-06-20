package com.meizu.newtest.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import okhttp3.OkHttpClient;

import static com.meizu.jarutil.okhttpUtils.newUtils.getResponObj;

/**
 * Created by libinhui on 2016/6/7.
 */
public class okhttpTest extends BroadcastReceiver{
    OkHttpClient cl = new OkHttpClient();
//    OkHttpClient cl = new OkHttpClient.Builder().cookieJar(new CookieJar() {
//        private final HashMap<HttpUrl, List<Cookie>> cookieStore = new HashMap<>();
//        @Override
//        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
//            cookieStore.put(HttpUrl.parse(url.host()), cookies);
//        }
//
//        @Override
//        public List<Cookie> loadForRequest(HttpUrl url) {
//            List<Cookie> cookies = cookieStore.get(url.host());
//            return cookies != null ? cookies : new ArrayList<Cookie>();
//        }
//    }).build();

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("test.bc.okhttptest")){
            HashMap hm = new HashMap();
            HashMap hm2 = new HashMap();
            hm.put("sn","850ABLNFSWWK");
            hm.put("phonestatus","2");
            hm2.put("sn","850ABLNFSWWK");
            String url = "http://172.29.232.46:8000/mzts/updatephonestatus/";
            String url2 = "http://172.29.232.46:8000/mzts/getphonestatus/";
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
