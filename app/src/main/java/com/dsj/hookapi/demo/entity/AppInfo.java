package com.dsj.hookapi.demo.entity;

import android.graphics.drawable.Drawable;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;
import com.dsj.hookapi.demo.BR;

import java.io.Serializable;

/**
 * @Description: desc
 * @Author: jx_wy
 * @Date: 2021/10/11 2:33 下午
 */
public class AppInfo extends BaseObservable implements Serializable {

    private String pkgName;
    @Bindable
    public String appName;
    @Bindable
    public String numbers;
    @Bindable
    public Drawable drawable;

    private boolean select = false;

    private ObservableField<String> verCode = new ObservableField<>();

    public ObservableField<String> getVerCode() {
        return verCode;
    }

    public void setVerCode(String verCode) {
        this.verCode.set(verCode);
    }
    public String getPkgName() {
        return pkgName;
    }

    @Bindable
    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
        notifyPropertyChanged(BR.pkgName);
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
        notifyPropertyChanged(BR.appName);
    }

    public String getMsg(){
        return appName + "\n" + pkgName;
    }

    public String getNumbers() {
        return numbers;
    }

    public void setNumbers(String numbers) {
        this.numbers = numbers;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public boolean isSelect() {
        return select;
    }

    @Bindable
    public void setSelect(boolean select) {
        this.select = select;
        notifyPropertyChanged(BR.select);
    }

    public int getSelectStatu(){
        return select ? 0 : 1;
    }
}
