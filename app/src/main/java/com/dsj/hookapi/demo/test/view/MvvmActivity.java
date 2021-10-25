package com.dsj.hookapi.demo.test.view;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.dsj.hookapi.demo.R;
import com.dsj.hookapi.demo.databinding.ActivityTestBinding;
import com.dsj.hookapi.demo.test.adapter.TestAdapter;
import com.dsj.hookapi.demo.test.viewmodel.TestVm;

/**
 * @Description: desc
 * @Author: jx_wy
 * @Date: 2021/10/11 5:07 下午
 */
public class MvvmActivity extends AppCompatActivity {

    private ActivityTestBinding binding;
    private TestAdapter adapter;
    private TestVm viewMolder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_test);
        binding.testRv.setLayoutManager(new LinearLayoutManager(MvvmActivity.this));
        adapter = new TestAdapter();
        binding.testRv.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter == null){
            return;
        }
        if (viewMolder == null){
            viewMolder = new TestVm(adapter);
            viewMolder.loadData();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (binding != null){
            binding.unbind();
            binding = null;
        }
    }
}
