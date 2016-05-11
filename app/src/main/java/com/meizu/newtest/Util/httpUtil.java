package com.meizu.newtest.Util;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by libinhui on 2016/4/26.
 */
public class httpUtil {
    OkHttpClient client = new OkHttpClient();

    //post请求demo
    String postUtil(String url, String sn) throws IOException, JSONException {

        RequestBody formBody = new FormEncodingBuilder()
                .add("sn", sn)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            String res = response.body().string();
            JSONObject resobj = new JSONObject(res);
            return resobj.get("status").toString();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    String postUtil(String url, String sn, String status) throws IOException, JSONException {

        RequestBody formBody = new FormEncodingBuilder()
                .add("sn", sn)
                .add("phonestatus", status)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(formBody)
                .build();

        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            String res = response.body().string();
            JSONObject updatestatobj = new JSONObject(res);
            return updatestatobj.get("result").toString();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    //上传
//    public void uploadFile(String url, String filePath) throws Exception {
//        // Use the imgur image upload API as documented at https://api.imgur.com/endpoints/image
//        MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
//
//        File file = new File(filePath);
//
//        RequestBody requestBody = new MultipartBuilder()
//                .type(MultipartBuilder.FORM)
//                .addFormDataPart("title", "Square Logo")
//                .addFormDataPart("image", file.getName(),
//                        RequestBody.create(MEDIA_TYPE_PNG, file))
//                .build();
//
//        Request request = new Request.Builder()
//                .url(url)
//                .post(requestBody)
//                .build();
//
//        Response response = client.newCall(request).execute();
//        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
//
//        System.out.println(response.body().string());
//    }
}
