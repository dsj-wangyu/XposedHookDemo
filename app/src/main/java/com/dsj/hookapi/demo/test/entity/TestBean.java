package com.dsj.hookapi.demo.test.entity;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import com.dsj.hookapi.demo.BR;

/**
 * @Description: desc
 * @Author: jx_wy
 * @Date: 2021/10/12 2:29 下午
 */
public class TestBean extends BaseObservable {

    @Bindable
    public String testImgUrl;
    @Bindable
    public String testTitle;
    @Bindable
    public String testDesc;

    public String getTestImgUrl() {
        return testImgUrl;
    }

    public void setTestImgUrl(String testImgUrl) {
        this.testImgUrl = testImgUrl;
        notifyPropertyChanged(BR.testImgUrl);
    }

    public String getTestTitle() {
        return testTitle;
    }

    public void setTestTitle(String testTitle) {
        this.testTitle = testTitle;
        notifyPropertyChanged(BR.testTitle);
    }

    public String getTestDesc() {
        return testDesc;
    }

    public void setTestDesc(String testDesc) {
        this.testDesc = testDesc;
        notifyPropertyChanged(BR.testDesc);
    }
}
