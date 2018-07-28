package com.yuzeduan.imageLoader;

import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * 根据ImageView的大小获取图片的压缩宽高
 */
public class ImageViewUtil {

    public static int getWideSize(ImageView imageView){
        DisplayMetrics displayMetrics = imageView.getContext().getResources().getDisplayMetrics();
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        int width = imageView.getWidth();
        if(width <= 0){
            width = layoutParams.width;
        }
        if(width <= 0 ){
            width = imageView.getMaxWidth();
        }
        if(width <= 0){
            width = displayMetrics.widthPixels;
        }
        return width;
    }

    public static int getHeightSize(ImageView imageView){
        DisplayMetrics displayMetrics = imageView.getContext().getResources().getDisplayMetrics();
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        int height = imageView.getHeight();
        if(height <= 0){
            height = layoutParams.height;
        }
        if(height <= 0 ){
            height = imageView.getMaxHeight();
        }
        if(height <= 0){
            height = displayMetrics.heightPixels;
        }
        return height;
    }
}
