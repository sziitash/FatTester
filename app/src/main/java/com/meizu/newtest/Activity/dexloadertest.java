package com.meizu.newtest.Activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.meizu.newtest.R;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import dalvik.system.DexClassLoader;

/**
 * Created by libinhui on 2016/2/17.
 */
public class dexloadertest extends Activity {
    /** Called when the activity is first created. */
//    private Context mcontext;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.dexload);

        Context mcontext;
        mcontext = this.getApplicationContext();

        try {
            List<String> caselist = getTestClassesFromJars(mcontext,"/storage/emulated/0/flymesync.jar","com.meizu.sync.datamaker.test.SyncSanityTestCase");
            Log.i("benlee", String.valueOf(caselist.size()));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static List<String> getTestClassesFromJars(Context context, String jarPath, String testCase) throws ClassNotFoundException {
        String dexPath = jarPath + File.pathSeparator + "/system/framework/android.test.runner.jar" + File.pathSeparator + "/system/framework/uiautomator.jar";
        Log.i("dexPath",dexPath);
        String dexOutputDir = context.getApplicationInfo().dataDir;
        Log.i("dexOutputDir",dexOutputDir);
        DexClassLoader classLoader = new DexClassLoader(dexPath, dexOutputDir, null, context.getClass().getClassLoader());
        List<String> caseList = new ArrayList<String>();
        Class cls = classLoader.loadClass(testCase);
        Method[] methods = cls.getMethods();
        for (Method m : methods) {
            if(m.getName().startsWith("test")){
                caseList.add(m.getName());
            }
        }
        return caseList;
    }
}
