package com.meizu.newtest.Activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.meizu.newtest.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by libinhui on 2016/7/14.
 */
public class selectTestApk extends AppCompatActivity{
    private Spinner spinner;
    private TextView tv;
    private Button bt;
    private ArrayAdapter<String> adapter;
    private String[] u2testapks;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        u2testapks = getTestApks();
        setContentView(R.layout.spinnertest);
        spinner = (Spinner) findViewById(R.id.spinner);
        tv = (TextView) findViewById(R.id.textView4);
        bt = (Button) findViewById(R.id.button1);
        bt.setOnClickListener(selectClassButton);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,u2testapks);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener {

            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                       long arg3) {
                tv.setText(u2testapks[arg2]);
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        }


        spinner.setOnItemSelectedListener(new SpinnerSelectedListener());

        //设置默认值
        spinner.setVisibility(View.VISIBLE);
    }

    public Button.OnClickListener selectClassButton = new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            String res = spinner.getSelectedItem().toString();
            Intent in = new Intent(selectTestApk.this,ApkDexTest.class);;
            in.putExtra("testpkg",res);
            startActivity(in);
        }
    };

    public String[] getTestApks(){
        ArrayList<String> testapks = new ArrayList<String>();
        List<PackageInfo> packages = this.getPackageManager().getInstalledPackages(0);
        for(int i=0;i<packages.size();i++) {
            PackageInfo packageInfo = packages.get(i);
            if(packageInfo.packageName.contains("test")){
                testapks.add(packageInfo.packageName);
            }
        }
        return testapks.toArray(new String[]{});
    }
}
