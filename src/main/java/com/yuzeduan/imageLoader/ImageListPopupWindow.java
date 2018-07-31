package com.yuzeduan.imageLoader;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;

import com.yuzeduan.R;
import com.yuzeduan.adapter.ImageListAdapter;
import com.yuzeduan.bean.FolderBean;
import com.yuzeduan.util.WindowUtil;

import java.util.List;

public class ImageListPopupWindow extends PopupWindow{
    private int mWidth;
    private int mHeight;
    private Context mContext;
    private View mCovertView;
    private RecyclerView mRecyclerView, mRvList;
    private List<String> mFirstListPaths;

    private List<FolderBean> mDatas;

    public ImageListPopupWindow(Context context, List<FolderBean> list, List<String> paths, RecyclerView recyclerView){
        mContext = context;
        mCovertView = LayoutInflater.from(context).inflate(R.layout.popup_pickup, null);
        mDatas = list;
        mFirstListPaths = paths;
        mRvList = recyclerView;
        mWidth = WindowUtil.getWindowHeightAndWidth(context).widthPixels;
        mHeight = (int)(WindowUtil.getWindowHeightAndWidth(context).heightPixels * 0.7);
        setContentView(mCovertView);
        setWidth(mWidth);
        setHeight(mHeight);

        setFocusable(true);
        setTouchable(true);
        setOutsideTouchable(true);
        setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_OUTSIDE){
                    dismiss();
                    return true;
                }
                return false;
            }
        });
        initViews();
    }

    private void initViews(){
        mRecyclerView = mCovertView.findViewById(R.id.pickup_rv_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        ImageListAdapter adapter = new ImageListAdapter(mContext, mDatas, mFirstListPaths, R.layout.popup_item, mRvList, this);
        mRecyclerView.setAdapter(adapter);
    }
}
