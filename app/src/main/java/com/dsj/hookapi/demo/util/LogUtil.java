package com.dsj.hookapi.demo.util;

import android.util.Log;

/**
 * @Description: 日志操作工具类
 * @Author: jx_wy
 * @Date: 2021/10/19 3:19 下午
 */
public class LogUtil {

    public static String getTrackMsg(){
        StringBuilder sb = new StringBuilder();
        try {
            StackTraceElement[] ste = new Throwable().getStackTrace();
            if (ste.length >= 1) {
                for (int i = 1; i < ste.length; i++) {
                    sb.append(ste[i].getClassName()).append(".").append(ste[i].getMethodName()).append(" --> LineNum: ").append(ste[i].getLineNumber());
                    sb.append("\n");
                }
            }
        } catch (Exception e) {
            sb.append(Log.getStackTraceString(e));
        }
        return sb.toString();
    }
}
