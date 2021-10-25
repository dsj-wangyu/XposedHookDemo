package com.dsj.hookapi.demo.view;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.dsj.hookapi.demo.DemoApp;
import com.dsj.hookapi.demo.R;
import com.dsj.hookapi.demo.adapter.AppinfoAdapter;
import com.dsj.hookapi.demo.adapter.MethodAdapter;
import com.dsj.hookapi.demo.databinding.ActivityMainBinding;
import com.dsj.hookapi.demo.entity.MethodInfo;
import com.dsj.hookapi.demo.eventEntity.MethodEventy;
import com.dsj.hookapi.demo.test.view.MvvmActivity;
import com.dsj.hookapi.demo.util.LogUtil;
import com.dsj.hookapi.demo.vm.AppinfoVm;
import com.dsj.hookapi.demo.vm.MethodVm;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * @Description: desc
 * @Author: jx_wy
 * @Date: 2021/10/11 4:34 下午
 */
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private AppinfoVm appVm;
    private AppinfoAdapter appAdapter;

    private MethodVm methodVm;
    private MethodAdapter methodAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.mainRvApplication.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        appAdapter = new AppinfoAdapter();
        appVm = new AppinfoVm();
        binding.mainRvApplication.setAdapter(appAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        binding.mainRvMethod.setLayoutManager(layoutManager);
        methodAdapter = new MethodAdapter();
        methodVm = new MethodVm();
        binding.mainRvMethod.setAdapter(methodAdapter);
        binding.mainTabMethod.requestFocus();
        binding.mainTabMethod.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND){
                    parseMethod(binding.mainTabMethod.getText().toString());
                }
                return false;
            }
        });
        binding.mainTabMethod.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String source = s.toString();
                if (source.endsWith("\n")){
                    parseMethod(source.substring(0, source.length() - 1));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        String name = MethodVm.class.getName();
        Log.e("Tag", "className ---> " + name);
    }

    private void parseMethod(String msg){
        if (methodVm == null){
            return;
        }
        if (msg.contains(MethodVm.SplitMethod)){
            MethodInfo info = MethodInfo.parseData(msg);
            if (info != null){
                methodVm.addMethod(info);
            }
        }
        binding.mainTabMethod.setText("");
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        return super.dispatchKeyEvent(event);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MethodEventy eventy){
        if (methodVm != null && eventy != null){
            methodVm.showDeleteDialog(eventy.getInfo(), MainActivity.this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        appVm.loadInfos(MainActivity.this, appAdapter);
        methodVm.loadMethods(methodAdapter);
    }

    public void goTest(View view){
//        startActivity(new Intent(MainActivity.this, MvvmActivity.class));
//        try {
//            WifiInfo wifiInfo = ((WifiManager) MainActivity.this.getApplicationContext().getSystemService(Context.WIFI_SERVICE)).getConnectionInfo();
//            String macWlan = wifiInfo.getMacAddress();
//            Log.e("getMac", macWlan);
//        } catch (Exception e) {
//            Log.e("getMac", Log.getStackTraceString(e));
//        }
//        Log.e("Tag", LogUtil.getTrackMsg());
        Toast.makeText(this, getToastMsg(), Toast.LENGTH_SHORT).show();
    }

    private String getToastMsg(){
        return "this is toast msg";
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (binding != null) {
            binding.unbind();
            binding = null;
        }
    }
}
