package com.meizu.newtest.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.meizu.jarutil.ShellUtils;
import com.meizu.newtest.R;

public class main extends AppCompatActivity {

    Button deviceinfo;
    Button webviewButton;
    Button oomButton;
    Button sockectButton;
    Button apkdextestButton;
//    Button apkdextestonstartButton;
    Button dumpapkButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ShellUtils.checkRootPermission();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        deviceinfo = (Button) findViewById(R.id.button);
        webviewButton = (Button) findViewById(R.id.webviewButton);
        oomButton = (Button) findViewById(R.id.oomButton);
        sockectButton = (Button) findViewById(R.id.sockectButton);
        apkdextestButton = (Button) findViewById(R.id.apkdextestButton);
        dumpapkButton = (Button) findViewById(R.id.dumpapkButton);
//        aidlButton = (Button)findViewById(R.id.aidlButton);
// 		  uploadButton = (Button)findViewById(R.id.uploadButton);
//		  dexloadButton = (Button)findViewById(R.id.dexloadButton);
//        apkdextestonstartButton = (Button) findViewById(R.id.apkdextestonstartButton);

//----------------------------------------------------------------------------------------

        deviceinfo.setOnClickListener(deviceinfoC);
        webviewButton.setOnClickListener(webviewtest);
        oomButton.setOnClickListener(ommtest);
        sockectButton.setOnClickListener(sockecttest);
        apkdextestButton.setOnClickListener(apkdextest);
        dumpapkButton.setOnClickListener(dumpapktest);
//        apkdextestonstartButton.setOnClickListener(apkdextestonstart);
//		  uploadButton.setOnClickListener(uploadtest);
// 		  aidlButton.setOnClickListener(aidltest);
//		  dexloadButton.setOnClickListener(dexloadtest);
    }

    public Button.OnClickListener deviceinfoC = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent it = new Intent(main.this, MainActivity.class);
            startActivity(it);
        }
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
            Intent intent = new Intent(main.this, selectTestApk.class);
            intent.putExtra("dumptype",1);
            startActivity(intent);
        }
    };

    public Button.OnClickListener dumpapktest = new Button.OnClickListener() {
        public void onClick(View view) {
            Intent intent = new Intent(main.this, selectTestApk.class);
            intent.putExtra("dumptype",2);
            startActivity(intent);
        }
    };

//    public Button.OnClickListener apkdextestonstart = new Button.OnClickListener() {
//        public void onClick(View view) {
//            Intent intent = new Intent(main.this, ApkDexTestonStart.class);
//            startActivity(intent);
//        }
//    };

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
}