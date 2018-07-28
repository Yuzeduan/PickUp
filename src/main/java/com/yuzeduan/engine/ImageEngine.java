package com.yuzeduan.engine;

import android.widget.ImageView;

/**
 * 用于配置所用的图片加载库
 */
public abstract class ImageEngine {
    public abstract void transferImageLoader(String path, ImageView imageView);
}
