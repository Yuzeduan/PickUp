package com.yuzeduan.model;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.yuzeduan.R;
import com.yuzeduan.bean.FolderBean;
import com.yuzeduan.bean.SelectionSpec;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PickUpModel {
    //表示外界调用图片选择器的时候,选择的图片类型
    private SelectionSpec.ImageType mType = SelectionSpec.getInstence().getmType();

    public void scanPicture(final Context context, final Callback callback) {
        final android.os.Handler mHandler = new android.os.Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                final List<String> childPath = new ArrayList<>();
                final List<FolderBean> folderBeans = new ArrayList<>();
                FolderBean firstFolder = new FolderBean();
                firstFolder.setmName(context.getResources().getString(R.string.list_name));
                folderBeans.add(firstFolder);
                Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver mContentResolver = context.getContentResolver();
                String selection = getSelection();
                String[] selectionArgs = getSelectionArgs();
                Cursor cursor = mContentResolver.query(mImageUri, null,
                        selection, selectionArgs, MediaStore.Images.Media.DATE_MODIFIED);
                Set<String> dirPaths = new HashSet<>();
                if(cursor != null) {
                    while (cursor.moveToNext()) {
                        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                        childPath.add(path);
                        File parentFile = new File(path).getParentFile();
                        FolderBean folderBean;
                        if(parentFile == null) {
                            continue;
                        }
                        String dirPath = parentFile.getAbsolutePath();
                        if(dirPaths.contains(dirPath)){
                            continue;
                        }else{
                            folderBean  = new FolderBean();
                            dirPaths.add(dirPath);
                            folderBean.setmDir(dirPath);
                            folderBean.setmFirstImgPath(path);
                        }
                        if(parentFile.list() == null){
                            continue;
                        }
                        int picSize = parentFile.list(new FilenameFilter() {
                            @Override
                            public boolean accept(File file, String s) {
                                return getAccountBoolean(s);
                            }
                        }).length;
                        folderBean.setmCount(picSize);
                        folderBeans.add(folderBean);
                    }
                    cursor.close();
                    folderBeans.get(0).setmFirstImgPath(childPath.get(childPath.size()-1));
                    folderBeans.get(0).setmCount(childPath.size());
                }
                Collections.reverse(childPath);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onFinish(childPath);
                        callback.onDirDataFinish(folderBeans, childPath);
                    }
                });
            }
        }).start();
    }

    /**
     * 通过判断外界调用选择器选择的图片类型,返回查询时候的Selection
     * @return
     */
    private String getSelection(){
        String selection;
        if(mType == SelectionSpec.ImageType.ALL){
            selection = MediaStore.Images.Media.MIME_TYPE + "=? or "
                    + MediaStore.Images.Media.MIME_TYPE + "=?";
        }else{
            selection = MediaStore.Images.Media.MIME_TYPE + "=?";
        }
        return selection;
    }

    /**
     * 通过判断外界调用选择器选择的图片类型,返回查询时候的SelectionArgs
     * @return
     */
    private String[] getSelectionArgs(){
        String[] selectionArgs;
        if(mType == SelectionSpec.ImageType.ALL){
            selectionArgs = new String[]{"image/jpeg", "image/png"};
        }else if(mType == SelectionSpec.ImageType.JPEG){
            selectionArgs = new String[]{"image/jpeg"};
        }else{
            selectionArgs = new String[]{"image/png"};
        }
        return selectionArgs;
    }

    /**
     * 通过判断外界调用选择器选择的图片类型,选择判断过滤照片的方式
     * @param fileName 文件的名字
     * @return 返回具体的过滤方式条件
     */
    public boolean getAccountBoolean(String fileName){
        if(mType == SelectionSpec.ImageType.ALL){
            return (fileName.endsWith(".jpeg") || fileName.endsWith(".png") || fileName.endsWith(".jpg"));
        }else if(mType == SelectionSpec.ImageType.JPEG){
            return (fileName.endsWith(".jpeg") || fileName.endsWith(".jpg"));
        }else {
            return (fileName.endsWith(".png"));
        }
    }

    public interface Callback{
        void onFinish(List<String> list);
        void onDirDataFinish(List<FolderBean> list, List<String> paths);
    }
}

