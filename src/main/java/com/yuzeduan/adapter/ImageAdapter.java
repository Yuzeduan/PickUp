package com.yuzeduan.adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

import com.yuzeduan.R;
import com.yuzeduan.engine.PickUpEngine;

import java.util.List;

public class ImageAdapter extends CommonAdapter<String>{
    PickUpEngine pickUpEngine = new PickUpEngine();

    @Override
    public void convert(ViewHolder viewHolder, String item) {
        ImageView imageView = viewHolder.getView(R.id.pickup_iv_image);
        imageView.setImageResource(R.drawable.pickup);
        pickUpEngine.transferImageLoader(item, imageView);
    }

    public ImageAdapter(Context mContext, List<String> mDatas, int mLayoutId) {
        super(mContext, mDatas, mLayoutId);
    }
}
