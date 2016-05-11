package com.meizu.newtest.Activity;

/**
 * Created by libinhui on 2016/2/15.
 */

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.meizu.newtest.Util.utils;
import com.meizu.newtest.R;

import java.net.ServerSocket;


public class wifiAdb extends Activity {
    private TextView tv;

    // Public variables
    public ServerSocket m_serverSocket      = null;

    // Private Controls
    private ToggleButton m_toggleButtonServerOnOff;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.socketserver);

        // Initialize control variables
        m_toggleButtonServerOnOff = (ToggleButton)findViewById(R.id.toggleButtonServerOnOff);

        // Set listener functions
        m_toggleButtonServerOnOff.setOnClickListener(onToggleServerOnOff);


        String isTurn = utils.execCommand("getprop service.adb.tcp.port", false).successMsg;
        if (isTurn.equals("5555")){
            m_toggleButtonServerOnOff.setChecked(true);
        }
        else{
            m_toggleButtonServerOnOff.setChecked(false);
        }

        String ipaddr = utils.execCommand("getprop dhcp.wlan0.ipaddress",false).successMsg;
        tv = (TextView) findViewById(R.id.textView2);
        if (utils.checkRootPermission()) {
            tv.setText("输入命令“adb connect "+ipaddr+":5555 连接设备！");
        }
        else{
            tv.setText("当前设备没有root，请连接设备并输入‘adb tcpip 5555’!");
        }

    }

    private Button.OnClickListener onToggleServerOnOff = new Button.OnClickListener(){
        public void onClick(View v)
        {
            if (m_toggleButtonServerOnOff.isChecked() && utils.checkRootPermission())
            {
                utils.execCommand("setprop service.adb.tcp.port 5555",true);
                utils.execCommand("stop adbd",true);
                utils.execCommand("start adbd",true);
            }
            else if (!m_toggleButtonServerOnOff.isChecked() && utils.checkRootPermission()){
                utils.execCommand("setprop service.adb.tcp.port -1",true);
                utils.execCommand("stop adbd",true);
                utils.execCommand("start adbd",true);
            }
            else{
                Toast.makeText(wifiAdb.this,"Root device please!", Toast.LENGTH_SHORT).show();
            }
        }
    };
}