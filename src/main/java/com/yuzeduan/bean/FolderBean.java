package com.yuzeduan.bean;

/**
 * 用于扫描手机图片时记录手机相册文件夹的信息
 */
public class FolderBean {
    private String mDir;  //当前文件夹的路径
    private String mFirstImgPath;  //当前文件夹的第一张图片路径
    private String mName; //当前文件夹的名字
    private int mCount; //当前文件夹照片的数量

    public String getmDir() {
        return mDir;
    }

    public void setmDir(String mDir) {
        this.mDir = mDir;
        int lastIndexOf = this.mDir.lastIndexOf("/");
        this.mName = this.mDir.substring(lastIndexOf+1);
    }

    public String getmFirstImgPath(){
        return mFirstImgPath;
    }

    public void setmFirstImgPath(String mFirstImgPath) {
        this.mFirstImgPath = mFirstImgPath;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public int getmCount() {
        return mCount;
    }

    public void setmCount(int mCount) {
        this.mCount = mCount;
    }
}
