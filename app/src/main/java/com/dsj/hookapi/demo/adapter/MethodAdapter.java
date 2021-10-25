package com.dsj.hookapi.demo.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.dsj.hookapi.demo.BR;
import com.dsj.hookapi.demo.R;
import com.dsj.hookapi.demo.entity.MethodInfo;
import com.dsj.hookapi.demo.listener.MethodClickListener;
import com.dsj.hookapi.demo.vm.AppinfoVm;
import com.dsj.hookapi.demo.vm.MethodVm;
import com.tencent.mmkv.MMKV;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: desc
 * @Author: jx_wy
 * @Date: 2021/10/12 6:52 下午
 */
public class MethodAdapter extends RecyclerView.Adapter<MethodAdapter.Holder> {

    private final List<MethodInfo> infos = new ArrayList<>();

    private MethodClickListener listener;

    public void setListener(MethodClickListener listener) {
        this.listener = listener;
    }

    public void setDatas(List<MethodInfo> datas) {
        if (datas == null || datas.isEmpty()) {
            return;
        }
        infos.clear();
        infos.addAll(datas);
        notifyDataSetChanged();
    }

    public void deleteData(MethodInfo info) {
        if (info == null || infos.isEmpty()) {
            return;
        }
        if (!infos.contains(info)) {
            return;
        }
        infos.remove(info);
        notifyDataSetChanged();
    }

    public void addData(MethodInfo info) {
        if (info == null) {
            return;
        }
        if (infos.size() == 1 && infos.get(0).getClassName().equals("...")) {
            infos.clear();//删除为空的填充数据
        }
        boolean contains = false;
        for (MethodInfo methodInfo : infos) {
            if (methodInfo.compare(info)) {
                contains = true;
                break;
            }
        }
        if (contains) {
            return;
        }
        infos.add(info);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_method, parent, false);
        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        ViewDataBinding binding = holder.getBinding();
        MethodInfo methodInfo = infos.get(position);
        binding.setVariable(BR.Method, methodInfo);
        binding.setVariable(BR.methodName, methodInfo.getMethodName());
        binding.setVariable(BR.className, methodInfo.getClassName());
//        binding.setVariable(BR.longClick, listener);
        binding.getRoot().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.clickMethod(methodInfo);
                return false;
            }
        });
        binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return infos.size();
    }


    protected static class Holder extends RecyclerView.ViewHolder {

        private ViewDataBinding binding;

        public Holder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public ViewDataBinding getBinding() {
            return binding;
        }

        public Holder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
