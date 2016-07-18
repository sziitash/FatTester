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
    private Spinner testrunnersp;
    private TextView tv;
    private TextView tv2;
    private Button bt;
    private ArrayAdapter<String> adapter;
    private ArrayAdapter<String> testRuneradapter;
    private String[] u2testapks;
    private String [] testrunner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        u2testapks = getTestApks();
        testrunner = new String[]{"com.meizu.u2.runner.BaseRunner", "android.support.test.runner.AndroidJUnitRunner"};
        setContentView(R.layout.spinnertest);
        spinner = (Spinner) findViewById(R.id.spinner);
        testrunnersp = (Spinner) findViewById(R.id.spinner2);
        tv = (TextView) findViewById(R.id.textView4);
        tv2 = (TextView) findViewById(R.id.textView6);
        bt = (Button) findViewById(R.id.button1);
        bt.setOnClickListener(selectClassButton);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,u2testapks);
        testRuneradapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,testrunner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        testrunnersp.setAdapter(testRuneradapter);

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

        class testRunner implements AdapterView.OnItemSelectedListener {

            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
                                       long arg3) {
                tv2.setText(testrunner[arg2]);
            }

            public void onNothingSelected(AdapterView<?> arg0) {
            }
        }


        testrunnersp.setOnItemSelectedListener(new testRunner());

        //设置默认值
        testrunnersp.setVisibility(View.VISIBLE);
    }

    public Button.OnClickListener selectClassButton = new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            String res = spinner.getSelectedItem().toString();
            String testrunertype = testrunnersp.getSelectedItem().toString();
            Intent in = new Intent(selectTestApk.this,ApkDexTest.class);;
            in.putExtra("testpkg",res);
            in.putExtra("runtype",testrunertype);
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
