package com.dsj.hookapi.demo.test.helper;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;
import androidx.databinding.BindingAdapter;
import com.bumptech.glide.Glide;
import com.dsj.hookapi.demo.R;

/**
 * @Description: desc
 * @Author: jx_wy
 * @Date: 2021/10/12 3:06 下午
 */
public class ImgHelper {

    @BindingAdapter({"itemUrl"})
    public static void loadImg(ImageView imageView, String url){
        Log.e("Tag", "loadImg ---> " + url);
        Context context = imageView.getContext();
        Glide.with(context)
                .load(url)
                .into(imageView);
    }

    @BindingAdapter({"itemDrawable"})
    public static void loadImgDrawable(ImageView imageView, Drawable drawable){
        imageView.setImageDrawable(drawable);
    }

    @BindingAdapter({"AppSelect"})
    public static void select(ImageView imageView, boolean select){
        imageView.setImageResource(select ? R.drawable.select : R.drawable.unselect);
    }
}
