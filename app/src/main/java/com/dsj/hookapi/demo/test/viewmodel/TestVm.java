package com.dsj.hookapi.demo.test.viewmodel;

import com.dsj.hookapi.demo.test.adapter.TestAdapter;
import com.dsj.hookapi.demo.test.entity.TestBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: desc
 * @Author: jx_wy
 * @Date: 2021/10/12 2:28 下午
 */
public class TestVm {

    private TestAdapter adapter;

    public TestVm(TestAdapter adp) {
        adapter = adp;
    }

    public void loadData() {
        List<TestBean> datas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            TestBean test = new TestBean();
            test.setTestImgUrl("https://img1.baidu.com/it/u=338026201,811635179&fm=26&fmt=auto");
            test.setTestTitle("this is title --> " + i);
            test.setTestDesc("this is desc");
            datas.add(test);
        }
        adapter.updateDatas(datas);
    }
}
