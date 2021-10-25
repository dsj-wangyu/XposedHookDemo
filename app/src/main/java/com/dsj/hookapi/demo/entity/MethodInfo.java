package com.dsj.hookapi.demo.entity;

import android.text.TextUtils;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import com.dsj.hookapi.demo.BR;
import com.dsj.hookapi.demo.vm.MethodVm;

import java.io.Serializable;

/**
 * @Description: desc
 * @Author: jx_wy
 * @Date: 2021/10/12 6:12 下午
 */
public class MethodInfo extends BaseObservable implements Serializable {

    private String className;
    private String methodName;

    @Bindable
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
        notifyPropertyChanged(BR.className);
    }

    @Bindable
    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
        notifyPropertyChanged(BR.methodName);
    }

    public static MethodInfo parseData(String dataStr) {
        if (TextUtils.isEmpty(dataStr)) {
            return null;
        }
        if (!dataStr.contains(MethodVm.SplitMethod)) {
            return null;
        }
        try {
            int index = dataStr.lastIndexOf(MethodVm.SplitMethod);
            String className = dataStr.substring(0, index);
            String methodName = dataStr.substring(index + 1);
            if (TextUtils.isEmpty(className) || TextUtils.isEmpty(methodName)) {
                return null;
            }
            MethodInfo info = new MethodInfo();
            info.setClassName(className);
            info.setMethodName(methodName);
            return info;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean compare(MethodInfo info) {
        if (info == null) {
            return false;
        }
        return TextUtils.equals(getClassName(), info.getClassName()) && TextUtils.equals(getMethodName(), info.getMethodName());
    }

    public String getCacheStr(){
        return getClassName() + MethodVm.SplitMethod + getMethodName();
    }
}
