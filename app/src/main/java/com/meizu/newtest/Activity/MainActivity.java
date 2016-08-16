package com.meizu.newtest.Activity;

import android.app.ActivityManager;
import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.meizu.newtest.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//import android.telephony.TelephonyManager;

public class MainActivity extends ListActivity {
	public String osReleaseText() {
		try {
			Process ps = Runtime.getRuntime().exec("getprop ro.build.inside.id");
			BufferedReader br = new BufferedReader(new InputStreamReader(ps.getInputStream()));
			String reslut = br.readLine();
			String reslutText = reslut.toString();
			return reslutText;
			//osReleaseText.setText(reslutText);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
	}

	public String btNameStr(BluetoothAdapter mBluetoothAdapter) {
		String btname = mBluetoothAdapter.getName();
		return btname;
	}

	public String btMACAddr(BluetoothAdapter mBluetoothAdapter) {
		String btAddrStr = mBluetoothAdapter.getAddress();
		return btAddrStr;
	}

	public String wifInfoSSID(WifiManager wifiManager) {
		String wifiSSID = wifiManager.getConnectionInfo().getSSID();
		return wifiSSID;
	}

	public String wifInfoMACAddr(WifiManager wifiManager) {
		String wifiMACAddr = wifiManager.getConnectionInfo().getMacAddress();
		return wifiMACAddr;
	}

	public String wifInfoBSSID(WifiManager wifiManager) {
		String BssidMAC = wifiManager.getConnectionInfo().getBSSID();
		return BssidMAC;
	}

	public String wifInfoState(WifiManager wifiManager) {
		int wifiState = wifiManager.getWifiState();
		switch (wifiState) {
			case WifiManager.WIFI_STATE_ENABLED:
				return "已开启";
			case WifiManager.WIFI_STATE_ENABLING:
				return "开启中";
			case WifiManager.WIFI_STATE_DISABLED:
				return "已关闭";
			case WifiManager.WIFI_STATE_DISABLING:
				return "关闭中";
			case WifiManager.WIFI_STATE_UNKNOWN:
				return "未知";
			default:
				break;
		}
		return "";
	}

	public String apnName(ConnectivityManager conManager) {
		NetworkInfo ni = conManager.getActiveNetworkInfo();
		if (ni == null) {
			return "未知";
		} else {
			String apn = ni.getExtraInfo();
			return apn;
		}
	}

	public String phoneNumStatus(TelephonyManager telephonyManager) {
		String IMSI = telephonyManager.getSubscriberId();
		try {
			if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
				return "中国移动";
			} else if (IMSI.startsWith("46001")) {
				return "中国联通";
			} else if (IMSI.startsWith("46003")) {
				return "中国电信";
			}
		} catch (Exception localException) {
			return "未知";
		}
		return "";
	}

	public String phoneDisplay() {
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		String str = dm.widthPixels + "x" + dm.heightPixels;
		return str;
	}

	public String actName() {
		ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
		return cn.getClassName();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
		BluetoothAdapter mBluetoothAdapter;
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter(); // 获得本机蓝牙适配器对象引用
		ConnectivityManager conManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		TelephonyManager telephonyManager = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		String NativePhoneNumber=telephonyManager.getLine1Number();
		String deviceID = telephonyManager.getDeviceId();
		String[] mListTitle = {"系统版本","手机分辨率","蓝牙设备名称","蓝牙MAC地址","WiFi-SSID", "WiFi-MAC地址","WiFi-热点地址","WiFi当前状态","当前apn","当前手机号码","手机IMEI号","sim卡运营商","当前Activity"};
		String[] mListStr = {osReleaseText(),phoneDisplay(),btNameStr(mBluetoothAdapter), btMACAddr(mBluetoothAdapter),wifInfoSSID(wifiManager),wifInfoMACAddr(wifiManager),wifInfoBSSID(wifiManager),wifInfoState(wifiManager),
				apnName(conManager),NativePhoneNumber,deviceID,phoneNumStatus(telephonyManager),actName()};
		@SuppressWarnings("unused")
		ListView mListView = null;
		ArrayList<Map<String,Object>> mData= new ArrayList<Map<String,Object>>();

		//mListView = getListView();
		mListView = new ListView(this);

		int lengh = mListTitle.length;
		for(int i =0; i < lengh; i++) {
			Map<String,Object> item = new HashMap<String,Object>();
			item.put("title", mListTitle[i]);
			item.put("text", mListStr[i]);
			mData.add(item);
		}
		Log.i("mdata", String.valueOf(mData));
		SimpleAdapter adapter = new SimpleAdapter(this,mData, R.layout.deviceinfo,
				new String[]{"title","text"},new int[]{R.id.title,R.id.text});
		setListAdapter(adapter);
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onStart() {
		//配合systrace展示方法的运行时间和次数
		String result = osReleaseText();
		Log.i("benlee",result);
		super.onStart();
	}
}
