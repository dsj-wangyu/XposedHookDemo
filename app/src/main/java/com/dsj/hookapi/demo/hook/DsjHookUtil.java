package com.dsj.hookapi.demo.hook;

import android.text.TextUtils;
import android.util.Log;
import com.dsj.hookapi.demo.DemoApp;
import com.dsj.hookapi.demo.entity.MethodInfo;
import com.dsj.hookapi.demo.util.LogUtil;
import com.dsj.hookapi.demo.vm.AppinfoVm;
import com.dsj.hookapi.demo.vm.MethodVm;
import com.tencent.mmkv.MMKV;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description: 目前动态设置hook参数还不行，得想办法修改或者设置
 * @Description: 目前动态设置hook参数还不行，得想办法修改或者设置
 * @Description: 目前动态设置hook参数还不行，得想办法修改或者设置
 * @Description: 目前动态设置hook参数还不行，得想办法修改或者设置
 * @Description: 目前动态设置hook参数还不行，得想办法修改或者设置
 * @Description: 目前动态设置hook参数还不行，得想办法修改或者设置
 * @Description: 目前动态设置hook参数还不行，得想办法修改或者设置
 * @Description: 目前动态设置hook参数还不行，得想办法修改或者设置
 * @Description: 目前动态设置hook参数还不行，得想办法修改或者设置
 * @Description: 目前动态设置hook参数还不行，得想办法修改或者设置
 * @Description: 目前动态设置hook参数还不行，得想办法修改或者设置
 * @Author: jx_wy
 * @Date: 2021/10/19 2:50 下午
 */
public class DsjHookUtil implements IXposedHookLoadPackage {

    private List<MethodInfo> mInfos = null;
    private List<String> pkgList = null;

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
//        boolean isHookPkg = judgePkg(lpparam);
//        if (!isHookPkg){
//            return;
//        }
//        List<MethodInfo> hookDatas = DemoApp.hookDatas;
//        for (MethodInfo hookData : hookDatas) {
//            Class mainActClass = lpparam.classLoader.loadClass(hookData.getClassName());
//            final boolean toast = hookData.getMethodName().equals("getToastMsg");
//            XposedHelpers.findAndHookMethod(mainActClass, hookData.getMethodName(), new XC_MethodHook() {
//                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                    super.beforeHookedMethod(param);
//                    Log.e("Tag", LogUtil.getTrackMsg());
//                }
//                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                    if (toast){
//                        param.setResult("this is from Xposed !");
//                    }else {
//                        super.afterHookedMethod(param);
//                    }
//                }
//            });
//        }
        if (lpparam.packageName.equals("com.dsj.hookapi.demo")){
            Class mainActClass = lpparam.classLoader.loadClass(
                    "com.dsj.hookapi.demo.view.MainActivity");
            XposedHelpers.findAndHookMethod(mainActClass, "getToastMsg", new XC_MethodHook() {
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    Log.e("Tag", LogUtil.getTrackMsg());
                }
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    param.setResult("this is from Xposed !");
                }
            });

            Class vmClass = lpparam.classLoader.loadClass(
                    "com.dsj.hookapi.demo.vm.MethodVm");
            XposedHelpers.findAndHookMethod(vmClass, "getMethods", new XC_MethodHook() {
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    Log.e("Tag", LogUtil.getTrackMsg());
                }
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                }
            });
        }
    }

    private boolean judgePkg(XC_LoadPackage.LoadPackageParam lpparam){
        List<String> pkgInfos = DemoApp.pkgInfos;
        if (lpparam == null || pkgInfos.isEmpty()){
            return false;
        }
        String pkg = lpparam.packageName;
        boolean result = false;
        for (String pkgInfo : pkgInfos) {
            if (TextUtils.equals(pkg, pkgInfo)){
                result = true;
                break;
            }
        }
        return result;
    }
}
