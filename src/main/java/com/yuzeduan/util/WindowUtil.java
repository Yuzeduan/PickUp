package com.yuzeduan.util;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class WindowUtil {
    /**
     * 用于获取屏幕的宽高
     * @param context
     * @return
     */
    public static DisplayMetrics getWindowHeightAndWidth(Context context){
        WindowManager windowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics;
    }

    /**
     * 当PopupWindow消失的时候,用于使屏幕变亮
     */
    public static void lightOnWindow(Activity activity) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = 1.0f;
        activity.getWindow().setAttributes(lp);
    }

    /**
     *当Pupop出现的时候,用于使屏幕变暗
     */
    public static void lightOffWindow(Activity activity){
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = 0.3f;
        activity.getWindow().setAttributes(lp);
    }
}
