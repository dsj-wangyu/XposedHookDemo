package com.dsj.hookapi.demo.vm;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.View;
import com.dsj.hookapi.demo.DemoApp;
import com.dsj.hookapi.demo.adapter.MethodAdapter;
import com.dsj.hookapi.demo.entity.MethodInfo;
import com.dsj.hookapi.demo.eventEntity.MethodEventy;
import com.dsj.hookapi.demo.listener.MethodClickListener;
import com.tencent.mmkv.MMKV;
import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description: desc
 * @Author: jx_wy
 * @Date: 2021/10/12 6:57 下午
 */
public class MethodVm {

    public static final String MethodInfoKey = "MethodInfoKey";
    public static final String SplitMethod = ".";

    private MethodAdapter mAdapter;

    public void loadMethods(MethodAdapter adapter) {
        List<MethodInfo> infos = getMethods();
        if (infos.isEmpty()) {
            MethodInfo info = new MethodInfo();
            info.setMethodName("暂无数据，请添加");
            info.setClassName("...");
            infos.add(info);
        }
        adapter.setListener(getLongClick());
        adapter.setDatas(infos);
        mAdapter = adapter;
    }

    public void addMethod(MethodInfo info) {
        if (mAdapter != null) {
            mAdapter.addData(info);
        }
        MMKV mmkv = MMKV.defaultMMKV();
        String cache = mmkv.decodeString(MethodVm.MethodInfoKey, "");
        if (TextUtils.isEmpty(cache)) {
            mmkv.encode(MethodVm.MethodInfoKey, info.getCacheStr());
        } else {
            cache += (AppinfoVm.SplitKey + info.getCacheStr());
            mmkv.encode(MethodVm.MethodInfoKey, cache);
        }
    }

    public void deleteMethod(MethodInfo info) {
        if (mAdapter != null) {
            mAdapter.deleteData(info);
        }
        MMKV mmkv = MMKV.defaultMMKV();
        String cache = mmkv.decodeString(MethodVm.MethodInfoKey, "");
        String infoStr = info.getCacheStr();
        if (TextUtils.isEmpty(cache) || !cache.contains(infoStr)) {
            return;
        }
        int index = cache.indexOf(infoStr);
        if (index == 0) {
            cache = cache.substring(infoStr.length());
        } else if ((index + infoStr.length()) == cache.length()) {
            cache = cache.substring(0, cache.length() - infoStr.length());
        } else {
            cache = cache.replace(AppinfoVm.SplitKey + infoStr, "");
        }
        mmkv.encode(MethodVm.MethodInfoKey, cache);
    }

    private List<MethodInfo> getMethods() {
        List<MethodInfo> result = new ArrayList<>();
        MMKV mmkv = MMKV.defaultMMKV();
        String cache = mmkv.decodeString(MethodInfoKey, "");
        if (!TextUtils.isEmpty(cache)) {
            try {
                String[] split = cache.split(AppinfoVm.SplitKey);
                for (String infoStr : split) {
                    MethodInfo info = MethodInfo.parseData(infoStr);
                    if (info != null){
                        result.add(info);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private MethodClickListener longClick;

    private MethodClickListener getLongClick() {
        if (longClick == null) {
            longClick = new MethodClickListener() {
                @Override
                public void clickMethod(MethodInfo info) {
                    MethodEventy eventy = new MethodEventy();
                    eventy.setInfo(info);
                    EventBus.getDefault().post(eventy);
                }
            };
        }
        return longClick;
    }

    public void showDeleteDialog(final MethodInfo info, final Context context) {
        if (info == null) {
            return;
        }
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(context);
        normalDialog.setTitle("请确认删除信息");
        normalDialog.setMessage(info.getMethodName() + "\n" + info.getClassName());
        normalDialog.setPositiveButton("删除",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        deleteMethod(info);
                    }
                });
        normalDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        normalDialog.show();
    }
}
