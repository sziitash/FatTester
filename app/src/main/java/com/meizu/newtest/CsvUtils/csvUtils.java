package com.meizu.newtest.CsvUtils;

import android.os.SystemClock;

import com.csvreader.CsvWriter;
import com.meizu.newtest.Util.utils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by libinhui on 2016/6/13.
 */
public class csvUtils {
    public static void createResultCsvTitle(CsvWriter wr,String type) throws IOException {
        if(type.equals("cpu")){
            String[] csvtitle = {"CpuInfo"};
            wr.writeRecord(csvtitle);
            String[] resultinfo = {"次数","统计时间","cpu使用率"};
            wr.writeRecord(resultinfo);
        }
        else{
            String[] csvtitle = {"MemInfo"};
            wr.writeRecord(csvtitle);
            String[] resultinfo = {"次数","统计时间","pss使用率"};
            wr.writeRecord(resultinfo);
        }
    }

    public static class getCpuResultThread extends Thread {
        private String pkgName; //定义需要传值进来的参数
        private String type;
        private String sleeptime;
        public boolean gorun;

        public getCpuResultThread(String pkgName, String type, String sleeptime, boolean gorun) {//,boolean gorun){
            this.pkgName = pkgName;
            this.type = type;
            this.sleeptime = sleeptime;
            this.gorun = gorun;
        }

        @Override
        public void run() {
            List<String[]> resstrs = new LinkedList<String[]>();
            int x = 1;
            if(type.equals("cpu")){
                while (gorun) {
                    String resText = utils.execCommand("top -n 1 -d 0.1|grep " + pkgName, false).successMsg;
                    String[] ress = resText.split("\\%")[0].split(" ");
                    String res = ress[ress.length - 1];
                    String[] result = {String.valueOf(x), String.valueOf(System.currentTimeMillis()), res + "%"};
                    resstrs.add(result);
                    x++;
                    SystemClock.sleep(Integer.valueOf(sleeptime));
                }
            }
            else if(type.equals("mem")){
                while (gorun){
                    String resText = utils.execCommand("dumpsys meminfo " + pkgName +" |grep TOTAL", true).successMsg;
                    String[] ress = resText.split("    ");
                    String res = ress[3];
                    String[] result = {String.valueOf(x), String.valueOf(System.currentTimeMillis()), res+" kb"};
                    resstrs.add(result);
                    x++;
                    SystemClock.sleep(Integer.valueOf(sleeptime));
                }
            }
            CsvWriter wr = new CsvWriter("/sdcard/" + getNowSysDate() + type + ".csv", ',', Charset.forName("GBK"));
            try {
                createResultCsvTitle(wr, type);
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (String[] res : resstrs) {
                try {
                    wr.writeRecord(res);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            wr.close();
        }
    }

    public static String getNowSysDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }
}
