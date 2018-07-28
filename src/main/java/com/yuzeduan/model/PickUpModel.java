package com.yuzeduan.model;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PickUpModel {
    public void scanPicture(final Context context, final Callback callback) {
        final android.os.Handler mHandler = new android.os.Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                final List<String> childPath = new ArrayList<>();
                Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver mContentResolver = context.getContentResolver();
                //只查询jpeg和png的图片
                Cursor cursor = mContentResolver.query(mImageUri, null,
                        MediaStore.Images.Media.MIME_TYPE + "=? or "
                                + MediaStore.Images.Media.MIME_TYPE + "=?",
                        new String[]{"image/jpeg", "image/png"}, MediaStore.Images.Media.DATE_MODIFIED);
                while (cursor.moveToNext()) {
                    String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    childPath.add(path);
                }
                cursor.close();
                Collections.reverse(childPath);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFinish(childPath);
                    }
                });
            }
        }).start();
    }

    public interface Callback{
        void onFinish(List<String> list);
    }
}

