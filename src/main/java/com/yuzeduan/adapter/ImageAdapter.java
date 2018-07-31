package com.yuzeduan.adapter;

import android.content.Context;
import android.graphics.Color;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.yuzeduan.R;
import com.yuzeduan.bean.SelectionSpec;
import com.yuzeduan.engine.PickUpEngine;
import com.yuzeduan.util.ViewExpandUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ImageAdapter extends CommonAdapter<String>{
    private PickUpEngine pickUpEngine = new PickUpEngine();
    // 图片的最大选择数量
    private int mMaxSelectable = SelectionSpec.getInstence().getmMaxSelectable();
    private Context mActivity;
    private List<String> mList;
    //记录imageView的选中状态
    private static Set<String> mSelectPic = new HashSet<>();
    private static int mSelectImages;

    @Override
    public void onItemClick(ViewHolder viewHolder, int position) {
        ImageView imageView = viewHolder.getView(R.id.pickup_iv_image);
        ImageButton imageButton = viewHolder.getView(R.id.pickup_ibn_item);
        if(mSelectPic.contains(mList.get(position))){
            imageView.setColorFilter(null);
            imageButton.setImageResource(R.drawable.no_select);
            mSelectPic.remove(mList.get(position));
            mSelectImages--;
            return;
        }
        if(mSelectImages < mMaxSelectable) {
            mSelectPic.add(mList.get(position));
            imageView.setColorFilter(Color.parseColor("#77000000"));
            imageButton.setImageResource(R.drawable.select);
            mSelectImages++;
        }else{
            Toast.makeText(mActivity, "最多选择数量为"+mMaxSelectable, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemViewClick(int position) {
    }

    @Override
    public void convert(ViewHolder viewHolder, String item, int position) {
        ImageView imageView = viewHolder.getView(R.id.pickup_iv_image);
        ImageButton imageButton = viewHolder.getView(R.id.pickup_ibn_item);
        ViewExpandUtil.expandViewTouchDelegate(imageButton, 60, 60, 60, 60);
        imageView.setImageResource(R.drawable.pickup);
        pickUpEngine.transferImageLoader(item, imageView);
        imageView.setColorFilter(null);
        imageButton.setImageResource(R.drawable.no_select);
        if(mSelectPic.contains(mList.get(position))){
            imageView.setColorFilter(Color.parseColor("#77000000"));
            imageButton.setImageResource(R.drawable.select);
        }
    }

    public ImageAdapter(Context mContext, List<String> mDatas, int mLayoutId) {
        super(mContext, mDatas, mLayoutId);
        mActivity = mContext;
        mList = mDatas;
    }
}
