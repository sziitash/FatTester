package com.meizu.newtest.CsvUtils;

import android.os.SystemClock;
import android.util.Log;

import com.csvreader.CsvWriter;
import com.meizu.jarutil.ShellTools;
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
//        if(type.equals("cpu")){
//            String[] csvtitle = {"CpuInfo"};
//            wr.writeRecord(csvtitle);
//            String[] resultinfo = {"次数","统计时间","cpu使用率"};
//            wr.writeRecord(resultinfo);
//        }
//        else{
//            String[] csvtitle = {"MemInfo"};
//            wr.writeRecord(csvtitle);
//            String[] resultinfo = {"次数","统计时间","pss使用率"};
//            wr.writeRecord(resultinfo);
//        }
        switch (type){
            case "cpu":
                String[] csvtitle = {"CpuInfo"};
                wr.writeRecord(csvtitle);
                String[] resultinfo = {"次数","统计时间","cpu使用率"};
                wr.writeRecord(resultinfo);
                break;
            case "mem":
                String[] csvtitlemem = {"MemInfo"};
                wr.writeRecord(csvtitlemem);
                String[] resultinfomem = {"次数","统计时间","pss使用率"};
                wr.writeRecord(resultinfomem);
                break;
            case "fps":
                String[] csvtitlefps = {"FPSInfo"};
                wr.writeRecord(csvtitlefps);
                String[] resultinfofps = {"次数","FPS平均值","结果列表","最小值"};
                wr.writeRecord(resultinfofps);
                break;
            default:
                Log.i("benlee","请输入统计类型");
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
//            if(type.equals("cpu")){
//                while (gorun) {
//                    String resText = utils.execCommand("top -n 1 -d 0.1|grep " + pkgName, false).successMsg;
//                    String[] ress = resText.split("\\%")[0].split(" ");
//                    String res = ress[ress.length - 1];
//                    String[] result = {String.valueOf(x), String.valueOf(System.currentTimeMillis()), res + "%"};
//                    resstrs.add(result);
//                    x++;
//                    SystemClock.sleep(Integer.valueOf(sleeptime));
//                }
//            }
//            else if(type.equals("mem")){
//                while (gorun){
//                    String resText = utils.execCommand("dumpsys meminfo " + pkgName +" |grep TOTAL", true).successMsg;
//                    String[] ress = resText.split("    ");
//                    String res = ress[3];
//                    String[] result = {String.valueOf(x), String.valueOf(System.currentTimeMillis()), res+" kb"};
//                    resstrs.add(result);
//                    x++;
//                    SystemClock.sleep(Integer.valueOf(sleeptime));
//                }
//            }

            switch (type){
                case "cpu":
                    while (gorun) {
                        String resText = utils.execCommand("top -n 1 -d 0.1|grep " + pkgName, false).successMsg;
                        String[] ress = resText.split("\\%")[0].split(" ");
                        String res = ress[ress.length - 1];
                        String[] result = {String.valueOf(x), String.valueOf(System.currentTimeMillis()), res + "%"};
                        resstrs.add(result);
                        x++;
                        SystemClock.sleep(Integer.valueOf(sleeptime));
                    }
                    break;
                case "mem":
                    while (gorun) {
                        String resText = utils.execCommand("dumpsys meminfo " + pkgName + " |grep TOTAL", true).successMsg;
                        String[] ress = resText.split("    ");
                        String res = ress[3];
                        String[] result = {String.valueOf(x), String.valueOf(System.currentTimeMillis()), res + " kb"};
                        resstrs.add(result);
                        x++;
                        SystemClock.sleep(Integer.valueOf(sleeptime));
                    }
                    break;
                case "fps":
                    String Currentpkgname = ShellTools.getCurrentPkgName();
                    String acnText = ShellTools.getCurrentActivity();
                    String acn;
                    if(acnText.startsWith(".")){
                        acn = Currentpkgname+"/"+Currentpkgname+acnText;
                    }
                    else {
                        acn = Currentpkgname+"/"+acnText;
                    }
                    while (gorun){
                        //获取帧率信息
                        String resText = utils.execCommand("dumpsys SurfaceFlinger --latency " + acn, true).successMsg;
                    }
                    break;
                default:
                    Log.i("benlee","请输入统计类型");
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
