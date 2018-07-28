package com.yuzeduan.imageLoader;

import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.yuzeduan.bean.Holder;

/**
 * 用于任务分派者和任务执行者通信的handler
 */
public class UIHandler extends Handler {
    private Loader loader;

    UIHandler() {
        loader = new Loader();
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        if (loader == null) {
            return;
        }
        Holder holder = (Holder) msg.obj;
        String path = holder.getmPath();
        ImageView imageView = holder.getmImageView();
        loader.setImage(path, imageView);
    }
}

