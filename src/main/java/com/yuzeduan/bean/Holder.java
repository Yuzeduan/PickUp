package com.yuzeduan.bean;

import android.widget.ImageView;

/**
 * 用于在任务分派者和任务执行者传输任务时封装的图片uri和设置图片控件的对象类
 */
public class Holder {
    private String mPath;
    private ImageView mImageView;

    public Holder(String mPath, ImageView mImageView) {
        this.mPath = mPath;
        this.mImageView = mImageView;
    }

    public String getmPath() {
        return mPath;
    }

    public void setmPath(String mPath) {
        this.mPath = mPath;
    }

    public ImageView getmImageView() {
        return mImageView;
    }

    public void setmImageView(ImageView mImageView) {
        this.mImageView = mImageView;
    }
}
