package com.meizu.newtest.Activity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.csvreader.CsvWriter;
import com.meizu.newtest.R;
import com.meizu.newtest.Util.DumpTools;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

import dalvik.system.PathClassLoader;

/**
 * Created by libinhui on 2016/7/20.
 */
public class DumpApk extends AppCompatActivity{
    private Button classbt;
//    private Button methodsbt;
    private Bundle bd;
    private CsvWriter wr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dumpapk);
        bd = this.getIntent().getExtras();
        final String apkname = bd.getString("testpkg");
        classbt = (Button) findViewById(R.id.button2);
//        methodsbt = (Button) findViewById(R.id.button3);

        classbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Toast.makeText(getApplicationContext(),"正在开始分析",Toast.LENGTH_LONG).show();
                    ArrayList<String> apkclass;
                    apkclass = DumpTools.getApkClass(getApplicationContext(),apkname);
//                    ArrayList<String> apkclass = getApkClass(apkname);
                    PathClassLoader pathClassLoader = new dalvik.system.PathClassLoader(getPackageManager().getApplicationInfo(apkname, 0).sourceDir, ClassLoader.getSystemClassLoader());
                    wr = new CsvWriter("/sdcard/" + apkname + ".csv", ',', Charset.forName("GBK"));
                    String[] titlename = {apkname};
                    wr.writeRecord(titlename);
                    for(String classname:apkclass){
//                        wr.writeRecord(new String[]{classname});
                        ArrayList<String> classMethods = DumpTools.getApkMethods(classname,pathClassLoader);
                        String[] res = {classname, String.valueOf(classMethods)};
                        wr.writeRecord(res);
                    }

                    Toast.makeText(getApplicationContext(),"分析完成，查看csv文件",Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    wr.close();
                }
            }
        });
    }
}
