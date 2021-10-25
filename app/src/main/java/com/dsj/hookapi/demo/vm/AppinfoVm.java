package com.dsj.hookapi.demo.vm;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import com.dsj.hookapi.demo.adapter.AppinfoAdapter;
import com.dsj.hookapi.demo.entity.AppInfo;
import com.dsj.hookapi.demo.listener.AppClickListener;
import com.tencent.mmkv.MMKV;

import java.util.*;

/**
 * @Description: desc
 * @Author: jx_wy
 * @Date: 2021/10/12 5:25 下午
 */
public class AppinfoVm {

    public static final String ApkInfoKey = "ApkInfoKey";
    public static final String SplitKey = "#";

    public void loadInfos(Context context, AppinfoAdapter adapter) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<String> selectInfos = getSelectInfos();
                Intent intent = new Intent(Intent.ACTION_MAIN, null);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                PackageManager packageManager = context.getPackageManager();
                List<ResolveInfo> apps = packageManager.queryIntentActivities(intent, 0);
                List<AppInfo> infos = new ArrayList<>();
                //for循环遍历ResolveInfo对象获取包名和类名
                for (int i = 0; i < apps.size(); i++) {
                    ResolveInfo info = apps.get(i);
                    String packageName = info.activityInfo.packageName;
                    if (TextUtils.isEmpty(packageName)) {
                        continue;
                    }
                    boolean select = false;
                    if (selectInfos != null && !selectInfos.isEmpty()) {
                        for (String pkgName : selectInfos) {
                            if (TextUtils.equals(pkgName, packageName)) {
                                select = true;
                                break;
                            }
                        }
                    }
                    AppInfo appInfo = new AppInfo();
                    appInfo.setAppName(info.activityInfo.loadLabel(packageManager).toString());
                    appInfo.setPkgName(packageName);
                    appInfo.setDrawable(info.activityInfo.loadIcon(packageManager));
                    appInfo.setSelect(select);
                    try {
                        PackageInfo pkgInfo = packageManager.getPackageInfo(appInfo.getPkgName(), 0);
                        String version = pkgInfo.versionName; //获取应用packagename的版本号
                        appInfo.setVerCode(version);
                    } catch (Exception e) {
                        appInfo.setVerCode("0");
                    }

                    infos.add(appInfo);
                }
                Collections.sort(infos, new Comparator<AppInfo>() {
                    @Override
                    public int compare(AppInfo o1, AppInfo o2) {
                        return o1.getSelectStatu() - o2.getSelectStatu();
                    }
                });
                update(adapter, infos);
            }
        }).start();
    }

    private synchronized List<String> getSelectInfos() {
        List<String> result = null;
        MMKV mmkv = MMKV.defaultMMKV();
        String cache = mmkv.decodeString(ApkInfoKey, "");
        if (!TextUtils.isEmpty(cache)) {
            try {
                String[] split = cache.split(SplitKey);
                result = new ArrayList<>(Arrays.asList(split));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private void update(AppinfoAdapter adapter, List<AppInfo> infos) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                adapter.setListener(getClick(adapter));
                adapter.setData(infos);
            }
        });
    }

    private void updateCache(AppInfo info) {
        if (info == null || TextUtils.isEmpty(info.getPkgName())) {
            return;
        }
        String pkgName = info.getPkgName();
        List<String> selectInfos = getSelectInfos();
        if (selectInfos == null || selectInfos.isEmpty()) {
            MMKV.defaultMMKV().encode(ApkInfoKey, pkgName);
            return;
        }
        if (info.isSelect()) {
            if (selectInfos.contains(pkgName)) {
                return;
            }
            selectInfos.add(pkgName);
        } else {
            if (!selectInfos.contains(pkgName)) {
                return;
            }
            selectInfos.remove(pkgName);
        }
        StringBuilder sb = new StringBuilder();
        for (String pkgStr : selectInfos) {
            sb.append(pkgStr).append(SplitKey);
        }
        String cache = sb.toString();
        while (cache.endsWith(SplitKey)) {
            cache = cache.substring(0, cache.length() - 1);
        }
        MMKV.defaultMMKV().encode(ApkInfoKey, cache);
    }

    private AppClickListener clickListener = null;

    private AppClickListener getClick(AppinfoAdapter adapter) {
        if (clickListener == null) {
            clickListener = new AppClickListener() {
                @Override
                public void clickItem(AppInfo info) {
                    adapter.updateData(info);
                    updateCache(info);
                }
            };
        }
        return clickListener;
    }
}
