package com.dsj.hookapi.demo.test.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.dsj.hookapi.demo.BR;
import com.dsj.hookapi.demo.R;
import com.dsj.hookapi.demo.databinding.ItemHallBinding;
import com.dsj.hookapi.demo.test.entity.TestBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: desc
 * @Author: jx_wy
 * @Date: 2021/10/12 2:32 下午
 */
public class TestAdapter extends RecyclerView.Adapter<TestAdapter.TestHolder> {

    private final List<TestBean> datas = new ArrayList<>();

    public void updateDatas(List<TestBean> data) {
        if (data == null || data.isEmpty()) {
            return;
        }
        datas.clear();
        datas.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TestHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewDataBinding bind = DataBindingUtil.inflate(inflater, R.layout.item_hall, parent, false);
        return new TestHolder(bind);
    }

    @Override
    public void onBindViewHolder(@NonNull TestHolder holder, int position) {
        ViewDataBinding binding = holder.getViewDataBinding();
        TestBean bean = datas.get(position);
        Log.e("Tag", "bindHolder --> " + bean.testTitle);
        binding.setVariable(BR.testData, bean);
        binding.setVariable(BR.testImgUrl, bean.testImgUrl);
        binding.setVariable(BR.testTitle, bean.testTitle);
        binding.setVariable(BR.testDesc, bean.testDesc);
        binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    protected static class TestHolder extends RecyclerView.ViewHolder {
        private ViewDataBinding viewDataBinding;

        public TestHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            viewDataBinding = binding;
        }

        public ViewDataBinding getViewDataBinding() {
            return viewDataBinding;
        }

        public TestHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
