package com.yuzeduan.imageLoader;


import android.graphics.Bitmap;
import android.os.Handler;
import android.widget.ImageView;

/**
 * 执行具体的任务
 */
public class Loader {
    private LruCacheUtil mLruCacheUtil = new LruCacheUtil();
    private Dispatcher mDispatcher = Dispatcher.getInstance();

    Loader(){
    }

    /**
     * 判断是否有缓存,有的话直接设置缓存图片,若没有,调用loadImage方法,去获取图片
     * @param path 图片的uri
     * @param imageView 填充图片的控件
     */
    public void setImage(final String path, final ImageView imageView){
        imageView.setTag(path);
        Bitmap bitmap;
        int reqWidth = ImageViewUtil.getWideSize(imageView);
        int reqHeight = ImageViewUtil.getHeightSize(imageView);
        if((bitmap = mLruCacheUtil.getBitmapFromCache(path)) != null){
            imageView.setImageBitmap(bitmap);
        }else{
            loadImage(path, reqWidth, reqHeight,new ImageCallback(){
                @Override
                public void onFinish(Bitmap bitmap) {
                    if(imageView.getTag().equals(path))
                        imageView.setImageBitmap(bitmap);
                }
            });
        }
    }

    /**
     * 用于开启异步线程加载图片,并把任务添加到任务队列
     * @param path 图片的uri地址
     * @param reqWidth 图片要求的压缩宽度
     * @param reqHeight 图片要求的压缩高度
     * @param imageCallback 用于设置图片的回调接口
     */
    private void loadImage(final String path, final int reqWidth, final int reqHeight, final ImageCallback imageCallback){
        final Handler handler = new Handler();
        mDispatcher.addTask(new Runnable(){
            @Override
            public void run() {
                final Bitmap bitmap = ImageCompressionUtil.decodeSampledBitmapFromPath(path, reqWidth, reqHeight);
                mLruCacheUtil.addBitmapToCache(path, bitmap);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        imageCallback.onFinish(bitmap);
                    }
                });
                mDispatcher.getmSemaphoreThreadPool().release();
            }
        });
    }
}
