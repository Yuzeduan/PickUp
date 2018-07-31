package com.yuzeduan.bean;



import com.yuzeduan.engine.ImageEngine;
import com.yuzeduan.engine.PickUpEngine;

/**
 *用于保存图片选择器的配置信息
 */
public final class SelectionSpec {
    private static final int MAX_SELECTABLE = 9;
    private static final float THUMBNAIL_SCALE = 0.5f;

    private int mMaxSelectable;
    private float mTumbnailScale;
    private ImageType mType;
    private ImageEngine mImageEngine;

    private static SelectionSpec mInstance;

    private SelectionSpec(){}

    public static SelectionSpec getInstence(){
        if(mInstance == null){
            mInstance = new SelectionSpec();
        }
        return mInstance;
    }

    public static SelectionSpec getCleanInstance(){
        SelectionSpec selectionSpec = getInstence();
        selectionSpec.reset();
        return selectionSpec;
    }

    /**
     * 将SelectionSpec对象的配置设为默认配置
     */
    private void reset(){
        mMaxSelectable = MAX_SELECTABLE;
        mTumbnailScale = THUMBNAIL_SCALE;
        mType = ImageType.ALL;
        mImageEngine = new PickUpEngine();
    }

    public int getmMaxSelectable() {
        return mMaxSelectable;
    }

    public void setmMaxSelectable(int mMaxSelectable) {
        this.mMaxSelectable = mMaxSelectable;
    }

    public float getmTumbnailScale() {
        return mTumbnailScale;
    }

    public void setmTumbnailScale(float mTumbnailScale) {
        this.mTumbnailScale = mTumbnailScale;
    }

    public ImageType getmType() {
        return mType;
    }

    public void setmType(ImageType mType) {
        this.mType = mType;
    }

    public ImageEngine getmImageEngine() {
        return mImageEngine;
    }

    public void setmImageEngine(ImageEngine mImageEngine) {
        this.mImageEngine = mImageEngine;
    }

    public enum ImageType {
        PNG, JPEG, ALL
    }
}
