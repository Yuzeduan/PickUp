package com.yuzeduan.imageLoader;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.ImageView;

import com.yuzeduan.bean.Holder;
import com.yuzeduan.imageLoader.util.CPUUtil;

import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 进行后台线程的轮询,分派任务
 */
public class Dispatcher {
    private static Dispatcher mInstance;
    private ExecutorService mThreadPool;
    private List<Runnable> mTaskQueue;
    private Handler mPoolThreadHandler;
    private UIHandler mUIHandler;
    private Semaphore mSemaphorePoolThread = new Semaphore(0);
    private Semaphore mSemaphoreThreadPool;

    private Dispatcher(){
        init();
    }

    /**
     * 用于变量的初始化
     */
    private void init() {
        new Thread(new Runnable() {
                @Override
                public void run() {
                    Looper.prepare();
                    mPoolThreadHandler = new ThreadHandler(Dispatcher.this);
                    mSemaphorePoolThread.release();
                    Looper.loop();
                }
        }).start();
        mThreadPool = Executors.newFixedThreadPool(CPUUtil.obtainCPUCoreNum() + 1);
        mTaskQueue = Collections.synchronizedList(new LinkedList<Runnable>());
        mSemaphoreThreadPool = new Semaphore(CPUUtil.obtainCPUCoreNum() + 1);
    }

    public static Dispatcher getInstance(){
        if(mInstance == null){
            synchronized (Dispatcher.class){
                if(mInstance == null){
                    mInstance = new Dispatcher();
                }
            }
        }
        return mInstance;
    }

    public static Dispatcher getNewInstance(){
        Dispatcher dispatcher = getInstance();
        dispatcher.mUIHandler = new UIHandler();
        return dispatcher;
    }

    /**
     * 用于后台轮询线程handler的消息处理
     */
    public static class ThreadHandler extends Handler{
        private WeakReference<Dispatcher> dispatcherWeakReference;

        ThreadHandler(Dispatcher dispatcher){
            dispatcherWeakReference= new WeakReference<>(dispatcher);
        }
        @Override
        public void handleMessage(Message msg) {
            Dispatcher dispatcher = dispatcherWeakReference.get();
            dispatcher.mThreadPool.execute(dispatcher.getTask());
            try {
                dispatcher.mSemaphoreThreadPool.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 公开给外界调用图片加载库的方法
     * @param path 图片的路径
     * @param imageView 填充图片的控件
     */
    public void setImage(final String path, final ImageView imageView){
        Holder holder = new Holder(path,imageView);
        Message message = Message.obtain();
        message.obj = holder;
        mUIHandler.sendMessage(message);
    }

    /**
     * 添加任务到任务队列中,并用后台轮询的handler发送消息通知线程池取出任务执行
     * @param runnable 开启的线程任务
     */
    public synchronized void addTask(Runnable runnable){
        mTaskQueue.add(runnable);
        try {
            if(mPoolThreadHandler == null){
                mSemaphorePoolThread.acquire();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mPoolThreadHandler.sendEmptyMessage(0);
    }

    /**
     * 从任务队列里实现后进先出的取出任务
     * @return 返回一个任务
     */
    public Runnable getTask(){
        return mTaskQueue.remove(mTaskQueue.size()-1);
    }

    /**
     * 用于外界获取信号量,进行释放
     * @return 返回信号量
     */
    public Semaphore getmSemaphoreThreadPool() {
        return mSemaphoreThreadPool;
    }
}
