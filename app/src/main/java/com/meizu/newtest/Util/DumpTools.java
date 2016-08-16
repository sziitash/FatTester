package com.meizu.newtest.Util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import dalvik.system.DexFile;
import dalvik.system.PathClassLoader;

/**
 * Created by libinhui on 2016/7/20.
 */
public class DumpTools {

    private static String apkNamedir;

    public static String[] getMzApks(Context cn){
        ArrayList<String> testapks = new ArrayList<String>();
        List<PackageInfo> packages = cn.getPackageManager().getInstalledPackages(0);
        for(int i=0;i<packages.size();i++) {
            PackageInfo packageInfo = packages.get(i);
            if(packageInfo.packageName.contains("meizu")){
                testapks.add(packageInfo.packageName);
            }
        }
        return testapks.toArray(new String[]{});
    }


    public static ArrayList<String> getApkClass(Context cn, String pkgName) throws IOException, PackageManager.NameNotFoundException {
        //获取测试的类名
        apkNamedir = cn.getPackageManager().getApplicationInfo(pkgName, 0).sourceDir;
        ArrayList<String> allClass = new ArrayList<String>();
        DexFile dx = DexFile.loadDex(apkNamedir, File.createTempFile("opt", "dex", cn.getCacheDir()).getPath(), 0);
        for (Enumeration<String> classNames = dx.entries(); classNames.hasMoreElements(); ) {
            String cname = classNames.nextElement();
            int longnum = cname.split("\\.").length;
            if(cname.contains("meizu")||cname.contains("flyme")){
                if(!cname.contains("$") && cname.split("\\.")[longnum-1].length() > 4){
                    allClass.add(cname);
                }
            }
        }
        return allClass;
    }

    public static ArrayList<String> getApkMethods(String apkclass,PathClassLoader pathClassLoader) throws ClassNotFoundException {
        ArrayList<String> testcases = new ArrayList<String>();
        //获取方法
        Class<?> handler = Class.forName(apkclass, true, pathClassLoader);
        Method[] mts = handler.getMethods();
        for (Method mt : mts) {
            String testcasename = mt.getName();
            testcases.add(testcasename);
        }
        return testcases;
    }

}
