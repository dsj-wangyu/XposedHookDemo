package com.dsj.hookapi.demo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.*;
import com.dsj.hookapi.demo.databinding.ActivityDemoBinding;
import com.dsj.hookapi.demo.entity.AppInfo;

import java.util.Random;

/**
 * 4个view，分别对应entity、map、list、edittext双向绑定
 */
public class DataBindActivity extends AppCompatActivity {

    private AppInfo info;
    private ActivityDemoBinding binding;

    private final ObservableMap<String, String> map = new ObservableArrayMap<>();
    private final ObservableList<String> list = new ObservableArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_demo);
    }

    private synchronized boolean initAppInfo() {
        boolean result = info == null;
        if (result) {
            info = new AppInfo();
            info.setAppName("demo");
            info.setPkgName("com.demo.xxx");
            info.setNumbers("0");
            info.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
                @Override
                public void onPropertyChanged(Observable sender, int propertyId) {
                    if (propertyId == BR.appName) {
                        Log.e("Tag", "changeAppName ---> ");
                    } else if (propertyId == BR.pkgName) {
                        Log.e("Tag", "changePkgName ---> ");
                    }
                }
            });
            binding.setAppData(info);
            map.put("key", "mapDefault");
            for (int i = 0; i < 99; i++) {
                String v = String.valueOf(i + 1);
                map.put(v, v);
                list.add(v);
            }
            binding.setDefaultMap(map);
            binding.setKey("key");
            list.add(0, "listDefault");
            binding.setDefaultList(list);
            binding.setIndex(0);
        }
        return result;
    }

    public void clickApp(View view) {
        if (initAppInfo()) {
            return;
        }
        info.setAppName(getNextString());
    }

    public void clickMap(View view){
        if (initAppInfo()) {
            return;
        }
        map.put("key", String.valueOf(System.currentTimeMillis()));
    }

    public void clickList(View view){
        if (initAppInfo()) {
            return;
        }
        list.set(0, getNextString());
        Log.e("Tag", "info.num = " + info.getNumbers());
    }

    private String getNextString() {
        return String.valueOf(getNextInt());
    }

    private int getNextInt() {
        return new Random().nextInt(100);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding.unbind();
    }
}