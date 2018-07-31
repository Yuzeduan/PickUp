package com.yuzeduan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 构建公共的适配器
 * @param <T> 表示子项的类型,
 */
public abstract class CommonAdapter<T> extends RecyclerView.Adapter<ViewHolder>{
    private Context mContext;
    private List<T> mDatas;
    private int mLayoutId;
    private int mItemId;

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                onItemViewClick(holder.getLayoutPosition());
            }
        });
        if(mItemId != 0){
            holder.itemView.findViewById(mItemId).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClick(holder, holder.getLayoutPosition());
                }
            });
        }
        convert(holder, mDatas.get(position),position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = ViewHolder.getViewHolder(parent, mLayoutId);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }


    CommonAdapter(Context mContext, List<T> mDatas, int mLayoutId) {
        this.mContext = mContext;
        this.mLayoutId = mLayoutId;
        this.mDatas = mDatas;
    }

    /**
     * 抽象出来的填充控件的方法
     * @param viewHolder
     * @param item
     */
    public abstract void convert(ViewHolder viewHolder, T item,int position);

    /**
     * 抽象出来的点击整个itemView的后调用的方法
     * @param position
     */
    public abstract void onItemViewClick(int position);

    /**
     * 抽象出来的点击itemView的控件后调用的方法
     * @param position
     */
    public abstract void onItemClick(ViewHolder viewHolder, int position);

    /**
     * 用于给需要设置监听的itemView的控件赋值
     * @param mItemId
     */
    public void setmItemId(int mItemId) {
        this.mItemId = mItemId;
    }
}
