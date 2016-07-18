package com.meizu.newtest.Activity;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.meizu.newtest.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class main extends AppCompatActivity {

    Button deviceinfo;
    Button otheractButton;
    Button httprequestsButton;
    Button webviewButton;
    Button oomButton;
    Button sockectButton;
    Button apkdextestButton;
    Button apkdextestonstartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        getSupportActionBar().setDisplayUseLogoEnabled(true);
//        getSupportActionBar().setLogo(R.mipmap.good);
        deviceinfo = (Button) findViewById(R.id.button);
        otheractButton = (Button) findViewById(R.id.otheractButton);
        httprequestsButton = (Button) findViewById(R.id.httpButton);
        webviewButton = (Button) findViewById(R.id.webviewButton);
        oomButton = (Button) findViewById(R.id.oomButton);
//		aidlButton = (Button)findViewById(R.id.aidlButton);
        sockectButton = (Button) findViewById(R.id.sockectButton);
//		uploadButton = (Button)findViewById(R.id.uploadButton);
//		dexloadButton = (Button)findViewById(R.id.dexloadButton);
        apkdextestButton = (Button) findViewById(R.id.apkdextestButton);
        apkdextestonstartButton = (Button) findViewById(R.id.apkdextestonstartButton);

        deviceinfo.setOnClickListener(deviceinfoC);
        otheractButton.setOnClickListener(otheract);
        httprequestsButton.setOnClickListener(httpButton);
        webviewButton.setOnClickListener(webviewtest);
        oomButton.setOnClickListener(ommtest);
//		aidlButton.setOnClickListener(aidltest);
        sockectButton.setOnClickListener(sockecttest);
        apkdextestButton.setOnClickListener(apkdextest);
        apkdextestonstartButton.setOnClickListener(apkdextestonstart);
//		uploadButton.setOnClickListener(uploadtest);
//		dexloadButton.setOnClickListener(dexloadtest);
    }

    public Button.OnClickListener deviceinfoC = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent it = new Intent(main.this, MainActivity.class);
            startActivity(it);
        }
    };

    public Button.OnClickListener otheract = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent();
            ComponentName cn = new ComponentName("com.android.settings", "com.android.settings.TestingSettings");
            i.setComponent(cn);
            boolean a = timesleep();
            if (a == true) {
                i.setAction("android.intent.action.MAIN");
                startActivity(i);
            }
        }
    };

    public Button.OnClickListener httpButton = new Button.OnClickListener() {
        public void onClick(View view) {
            OkHttpClient client = new OkHttpClient();
            //根据请求URL创建一个Request对象
            final Request request = new Request.Builder()
//					.url("http://172.16.11.126:8000/football/player/")
                    .url("http://m.zhibo8.cc/json/paihang/zhongchao_player_goal.htm")
                    .build();
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Toast.makeText(getApplicationContext(), "请求失败", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    //请求成功，此处对请求结果进行处理
                    String result = response.body().string();
                    Log.i("benlee", result);
                    Intent intent = new Intent(main.this, httptest.class);
                    //用Bundle携带数据
                    Bundle bundle = new Bundle();
                    //定义Bundle的key
                    bundle.putString("respstr", result);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
        }
//
//				OkHttpClient mOkHttpClient = new OkHttpClient();
//				final Request request = new Request.Builder().url("http://172.16.11.126:8000/football/player/").build();
//				mOkHttpClient.newCall(request).enqueue(new Callback() {
//					@Override
//					public void onResponse(final Response response) throws IOException {
//						//请求成功，此处对请求结果进行处理
//						String result = response.body().string();
//						Log.i("httpreponse", "result = " + result);//获取服务器响应内容
//						Intent intent =new Intent(main.this,httptest.class);
//						//用Bundle携带数据
//						Bundle bundle=new Bundle();
//						//传递name参数为tinyphp
//						bundle.putString("respstr", result);
//						intent.putExtras(bundle);
//						startActivity(intent);
//						//InputStream is = response.body().byteStream();
//						//byte[] bytes = response.body().bytes();
//					}
//
//					@Override
//					public void onFailure(Request request, IOException e) {
//						//请求失败
//					}
//				});
//	    	}
    };

    public Button.OnClickListener webviewtest = new Button.OnClickListener() {
        public void onClick(View view) {
            Intent intent = new Intent(main.this, webviewtest.class);
            startActivity(intent);
        }
    };

    public Button.OnClickListener ommtest = new Button.OnClickListener() {
        public void onClick(View view) {
            Intent intent = new Intent(main.this, OtherActivity.class);
            startActivity(intent);
            ActivityManager.instance().registActivity(main.this);
        }
    };

//	public Button.OnClickListener aidltest = new Button.OnClickListener(){
//		public void onClick(View view){
//			Intent intent = new Intent(main.this,clientActivity.class);
//			startActivity(intent);
//		}
//	};

    public Button.OnClickListener sockecttest = new Button.OnClickListener() {
        public void onClick(View view) {
            Intent intent = new Intent(main.this, wifiAdb.class);
            startActivity(intent);
        }
    };

    public Button.OnClickListener apkdextest = new Button.OnClickListener() {
        public void onClick(View view) {
            Intent intent = new Intent(main.this, ApkDexTest.class);
            startActivity(intent);
        }
    };

    public Button.OnClickListener apkdextestonstart = new Button.OnClickListener() {
        public void onClick(View view) {
            Intent intent = new Intent(main.this, selectTestApk.class);
            startActivity(intent);
        }
    };

//	public Button.OnClickListener dexloadtest = new Button.OnClickListener(){
//		public void onClick(View view){
//			Intent intent = new Intent(main.this,dexloadertest.class);
//			startActivity(intent);
//		}
//	};

//	public Button.OnClickListener uploadtest = new Button.OnClickListener(){
//		public void onClick(View view){
//			uploadFile();
//		}
//	};

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("hello", "main_onDestroy");
        ActivityManager.instance().registActivity(main.this);
    }

    public boolean timesleep() {
        try {
            Thread.sleep(2000);
            return true;
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }
}