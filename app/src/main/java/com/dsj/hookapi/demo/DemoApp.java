package com.dsj.hookapi.demo;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.dsj.hookapi.demo.entity.MethodInfo;
import com.dsj.hookapi.demo.vm.AppinfoVm;
import com.dsj.hookapi.demo.vm.MethodVm;
import com.tencent.mmkv.MMKV;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description: desc
 * @Author: jx_wy
 * @Date: 2021/10/12 7:12 下午
 */
public class DemoApp extends Application {

    private static Context app;
    public static List<MethodInfo> hookDatas = new ArrayList<>();
    public static List<String> pkgInfos = new ArrayList<>();

    @Override
    public void onCreate() {
        resetHooks();
        super.onCreate();
        MMKV.initialize(this);
        app = this;
    }

    public static Context getApp() {
        return app;
    }

    private void resetHooks() {
        pkgInfos.clear();
        pkgInfos.add("com.dsj.hookapi.demo");
        hookDatas.clear();
        MethodInfo toastInfo = new MethodInfo();
        toastInfo.setClassName("com.dsj.hookapi.demo.view.MainActivity");
        toastInfo.setMethodName("getToastMsg");
        hookDatas.add(toastInfo);
        MethodInfo vmInfo = new MethodInfo();
        vmInfo.setClassName("com.dsj.hookapi.demo.vm.MethodVm");
        vmInfo.setMethodName("getMethods");
        hookDatas.add(vmInfo);
//        MMKV mmkv = MMKV.defaultMMKV();
//        String pkgCache = mmkv.decodeString(AppinfoVm.ApkInfoKey, "");
//        if (!TextUtils.isEmpty(pkgCache)) {
//            try {
//                String[] split = pkgCache.split(AppinfoVm.SplitKey);
//                ArrayList<String> strings = new ArrayList<>(Arrays.asList(split));
//                if (!strings.isEmpty()) {
//                    pkgInfos.addAll(strings);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        String methodCache = mmkv.decodeString(MethodVm.MethodInfoKey, "");
//        Log.e("Tag", "Methods : " + methodCache);
//        if (!TextUtils.isEmpty(methodCache)) {
//            try {
//                String[] split = methodCache.split(AppinfoVm.SplitKey);
//                for (String infoStr : split) {
//                    MethodInfo info = MethodInfo.parseData(infoStr);
//                    if (info != null) {
//                        hookDatas.add(info);
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
    }
}
