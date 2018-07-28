package com.yuzeduan.bean;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.yuzeduan.activity.PickUpActivity;
import com.yuzeduan.engine.ImageEngine;

/**
 *用于设置图片选择器的配置参数
 */
public final class SelectionCreator {
    private PickUp mPickUp;
    private SelectionSpec mSelectionSpec;

    public SelectionCreator(PickUp pickUp){
        mPickUp = pickUp;
        //获取一个默认参数的SelectionSpec对象,用于保存一些配置
        mSelectionSpec = SelectionSpec.getCleanInstance();
    }

    public SelectionCreator maxSelectable(int amount){
        mSelectionSpec.setmMaxSelectable(amount);
        return this;
    }

    public SelectionCreator thumbnailScale(float thumbnailScale){
        mSelectionSpec.setmTumbnailScale(thumbnailScale);
        return this;
    }

    public SelectionCreator imageEngine(ImageEngine engine){
        mSelectionSpec.setmImageEngine(engine);
        return this;
    }

    public SelectionCreator mimeType(SelectionSpec.ImageType type){
        mSelectionSpec.setmType(type);
        return this;
    }

    public void forResult(int requestCode) {
        Activity activity = mPickUp.getActivity();
        if (activity != null) {
            Intent intent = new Intent(activity, PickUpActivity.class);
            Fragment fragment = mPickUp.getFragment();
            if(fragment!=null){
                fragment.startActivityForResult(intent, requestCode);
            }else{
                activity.startActivityForResult(intent, requestCode);
            }
        }
    }
}
