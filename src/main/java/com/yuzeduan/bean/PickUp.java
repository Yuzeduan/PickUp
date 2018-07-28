package com.yuzeduan.bean;

import android.app.Activity;
import android.support.v4.app.Fragment;

import java.lang.ref.WeakReference;

/**
 *用于外界开启图片选择器的开口类
 */
public final class PickUp {
    private WeakReference<Activity> mActivity;
    private WeakReference<Fragment> mFragment;

    private PickUp(Activity activity){
        this.mActivity = new WeakReference<>(activity);
        mFragment = null;
    }

    private PickUp(Activity activity, Fragment fragment){
        this.mActivity = new WeakReference<>(activity);
        this.mFragment = new WeakReference<>(fragment);
    }

    public static PickUp from(Activity activity){
        return new PickUp(activity);
    }

    public static PickUp from(Fragment fragment){
        return new PickUp(fragment.getActivity(),fragment);
    }

    public SelectionCreator choose(){
        return new SelectionCreator(this);
    }

    public Activity getActivity(){
        return mActivity.get();
    }

    public Fragment getFragment(){
        if(mFragment == null){
            return null;
        }else{
            return mFragment.get();
        }
    }

}
