package com.yuzeduan.engine;

import android.widget.ImageView;

import com.yuzeduan.imageLoader.Dispatcher;

/**
 * 用于配置调用所需的图片选择库的引擎类
 */
public class PickUpEngine extends ImageEngine{

    @Override
    public void transferImageLoader(String path, ImageView imageView) {
        Dispatcher.getNewInstance().setImage(path, imageView);
    }
}
