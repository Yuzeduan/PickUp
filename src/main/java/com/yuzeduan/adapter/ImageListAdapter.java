package com.yuzeduan.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import com.yuzeduan.R;
import com.yuzeduan.bean.FolderBean;
import com.yuzeduan.engine.PickUpEngine;
import com.yuzeduan.imageLoader.ImageListPopupWindow;
import com.yuzeduan.model.PickUpModel;
import com.yuzeduan.util.StringConcatUtil;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ImageListAdapter extends CommonAdapter<FolderBean> {
    private List<FolderBean> mList;
    private Context mActivity;
    private RecyclerView mRvList;
    private PickUpEngine mPickUpEngine = new PickUpEngine();
    private List<String> mFirstListPath;
    private ImageListPopupWindow mImageListPopupWindow;

    @Override
    public void onItemViewClick(int position) {
        List<String> imagePaths;
        if(position == 0){
            imagePaths = mFirstListPath;
        }else{
            String dirPath = mList.get(position).getmDir();
            File parentFile = new File(dirPath);
            List<String> paths = Arrays.asList(parentFile.list(new FilenameFilter() {
                @Override
                public boolean accept(File file, String s) {
                    return new PickUpModel().getAccountBoolean(s);
                }
            }));
            List<String> childPaths = new ArrayList<>();
            String childPath;
            for(int i = 0; i < paths.size(); i++){
                childPath = StringConcatUtil.concatString(dirPath, paths.get(i));
                childPaths.add(childPath);
            }
            imagePaths = childPaths;
        }
        GridLayoutManager manager = new GridLayoutManager(mActivity, 4);
        mRvList.setLayoutManager(manager);
        ImageAdapter adapter = new ImageAdapter(mActivity, imagePaths, R.layout.image_item);
        adapter.setmItemId(R.id.pickup_ibn_item);
        mRvList.setAdapter(adapter);
        mImageListPopupWindow.dismiss();
    }

    public ImageListAdapter(Context mContext, List<FolderBean> mDatas, List<String> paths,
                            int mLayoutId, RecyclerView recyclerView, ImageListPopupWindow imageListPopupWindow) {
        super(mContext, mDatas, mLayoutId);
        mList = mDatas;
        mFirstListPath = paths;
        mActivity = mContext;
        mRvList = recyclerView;
        mImageListPopupWindow = imageListPopupWindow;
    }

    @Override
    public void convert(ViewHolder viewHolder, FolderBean item, int position) {
        ImageView imageView = viewHolder.getView(R.id.popup_iv_image);
        imageView.setImageResource(R.drawable.pickup);
        mPickUpEngine.transferImageLoader(item.getmFirstImgPath(), imageView);
        viewHolder.setText(R.id.popup_tv_dir_name, item.getmName());
        viewHolder.setText(R.id.popup_tv_dir_count, item.getmCount()+"");
    }

    @Override
    public void onItemClick(ViewHolder viewHolder, int position) {
    }
}
