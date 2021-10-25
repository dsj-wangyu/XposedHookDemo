package com.dsj.hookapi.demo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;
import com.dsj.hookapi.demo.BR;
import com.dsj.hookapi.demo.R;
import com.dsj.hookapi.demo.entity.AppInfo;
import com.dsj.hookapi.demo.listener.AppClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: desc
 * @Author: jx_wy
 * @Date: 2021/10/12 5:03 下午
 */
public class AppinfoAdapter extends RecyclerView.Adapter<AppinfoAdapter.ViewHolder> {

    private final List<AppInfo> datas = new ArrayList<>();

    private AppClickListener listener = null;

    public void setListener(AppClickListener listener) {
        this.listener = listener;
    }

    public void setData(List<AppInfo> dt) {
        if (dt == null || dt.isEmpty()) {
            return;
        }
        datas.clear();
        datas.addAll(dt);
        notifyDataSetChanged();
    }

    public void updateData(AppInfo info){
        if (info == null || !datas.contains(info)){
            return;
        }
        int index = datas.indexOf(info);
        info.setSelect(!info.isSelect());
        notifyItemChanged(index);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) parent.getContext().getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewDataBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_app, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ViewDataBinding binding = holder.getBinding();
        AppInfo info = datas.get(position);
        binding.setVariable(BR.AppData, info);
        binding.setVariable(BR.appName, info.getAppName());
        binding.setVariable(BR.pkgName, info.getPkgName());
        binding.setVariable(BR.drawable, info.getDrawable());
        binding.setVariable(BR.itemClick, listener);
        binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {

        private ViewDataBinding binding;

        public ViewHolder(ViewDataBinding dataBinding) {
            super(dataBinding.getRoot());
            binding = dataBinding;
        }

        public ViewDataBinding getBinding() {
            return binding;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
