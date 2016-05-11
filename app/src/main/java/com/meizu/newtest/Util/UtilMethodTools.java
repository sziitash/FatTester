package com.meizu.newtest.Util;

/**
 * Created by libinhui on 2016/4/18.
 */
public class UtilMethodTools {

    public String getPid(String processName){
        String restext = utils.execCommand("ps|grep "+processName,false).successMsg;
//        Log.i("UtilMethodTools",restext);
//        Log.i("UtilMethodTools", String.valueOf(restext.split(" ")));
//        Log.i("UtilMethodTools", String.valueOf(restext.split(" ").length));
        if(restext.split(" ").length > 3){
            String res = restext.split(" ")[3];
            return res;
        }
        return "";
    }
}
