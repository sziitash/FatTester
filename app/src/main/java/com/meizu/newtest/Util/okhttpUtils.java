package com.meizu.newtest.Util;

import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by libinhui on 2016/6/13.
 */
public class okhttpUtils {
    static String postUtil(OkHttpClient client, String url, HashMap hm, String checkstr) throws IOException, JSONException {
        MultipartBuilder builder = new MultipartBuilder().type(MultipartBuilder.FORM);
        Set<String> setList = hm.keySet();
        //解析hashmap,构造post请求体
        for(String keystr:setList){
            builder.addFormDataPart(keystr, String.valueOf(hm.get(keystr)));
        }
        RequestBody formBody = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            String res = response.body().string();
            JSONObject updatestatobj = new JSONObject(res);
            return updatestatobj.get(checkstr).toString();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    public static String getPostResp(OkHttpClient cl, String url, HashMap map, String checkstr) throws IOException, JSONException {
        return postUtil(cl,url,map,checkstr);
    }
}
