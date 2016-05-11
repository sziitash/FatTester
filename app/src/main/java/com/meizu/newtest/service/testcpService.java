package com.meizu.newtest.service;

/**
 * Created by libinhui on 2015/9/7.
 * contentprovider获取当前联系人信息，日志展示
 * 调用广播：adb shell am broadcast -a com.example.broadcastservicetest.testcpService
 */

import android.app.Service;
import android.content.ContentProviderOperation;
import android.content.Intent;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import com.meizu.newtest.Util.utils;

import java.util.ArrayList;

public class testcpService extends Service {
    private runThread rt;
    private utils util;

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        Log.d("LBH", "onBind");
        return null;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        Log.d("LBH", "onCreate");

        super.onCreate();
    }

    //	@SuppressWarnings("deprecation")
    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        //在线程中传参，修改gorun状态
        Log.d("LBH-cp", "onDestroy");
        super.onDestroy();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {

//		boolean gorun = intent.getBooleanExtra("gorun", true);
        //在onStartCommand运行线程，采集数据
        String contantname = intent.getStringExtra("name");
        String contantnum = intent.getStringExtra("number");
        rt= new runThread(contantname,contantnum);//,gorun);   //gorun参数默认为true
        //启动线程
        rt.start();
        return super.onStartCommand(intent, flags, startId);
    }

    private class runThread extends Thread {
        private String DisplayName;
        private String MobileNumber;

        public runThread(String name, String number){
            this.DisplayName = name;
            this.MobileNumber = number;
        }

        @Override
        public void run() {
            Log.i("benlee","addcontant_service");
            insertOneContact(DisplayName,MobileNumber);
//            updateContact("benlee");
        }
    }

    //新增一个联系人
    private void insertOneContact(String DisplayName, String MobileNumber){
        ArrayList< ContentProviderOperation > ops = new ArrayList< ContentProviderOperation >();

        ops.add(ContentProviderOperation.newInsert(
                ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build());

        //------------------------------------------------------ Names
        if (DisplayName != null) {
            ops.add(ContentProviderOperation.newInsert(
                    ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                    .withValue(
                            ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                            DisplayName).build());
        }

        //------------------------------------------------------ Mobile Number
        if (MobileNumber != null) {
            ops.add(ContentProviderOperation.
                    newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(ContactsContract.Data.MIMETYPE,
                            ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, MobileNumber)
                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                            ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                    .build());
        }

        // Asking the Contact provider to create a new contact
        try {
            getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


}

