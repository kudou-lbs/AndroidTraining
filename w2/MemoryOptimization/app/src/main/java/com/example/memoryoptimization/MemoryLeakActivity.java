package com.example.memoryoptimization;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 内存泄露示例
 * */
public class MemoryLeakActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MemoryLeakActivity.class.getSimpleName();

    private Context context;
    private List<Object> objectList;
    private MyThread myThread;
    private Handler handler;
    private Bitmap bitmap;
    private ScreenReceiver screenReceiver;

    private Button btnSingleInstance;
    private Button btnMultiThread;
    private Button btnHandler;
    private Button btnRegister;
    private Button btnCollection;
    private Button btnCollectionRelease;
    private Button btnBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_leak);

        context = this;

        btnSingleInstance = findViewById(R.id.btn_single_instance);
        btnSingleInstance.setOnClickListener(this);

        btnMultiThread = findViewById(R.id.btn_multi_thread);
        btnMultiThread.setOnClickListener(this);

        btnHandler = findViewById(R.id.btn_handler);
        btnHandler.setOnClickListener(this);

        btnRegister = findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(this);

        btnCollection = findViewById(R.id.btn_collection);
        btnCollection.setOnClickListener(this);

        btnCollectionRelease = findViewById(R.id.btn_collection_release);
        btnCollectionRelease.setOnClickListener(this);

        btnBitmap = findViewById(R.id.btn_bitmap);
        btnBitmap.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_single_instance:
                singleInstanceML();
                break;
            case R.id.btn_multi_thread:
                multiThreadMl();
                break;
            case R.id.btn_handler:
                handlerMl();
                break;
            case R.id.btn_register:
                registerMl();
                break;
            case R.id.btn_collection:
                collectionMl();
                break;
            case R.id.btn_collection_release:
                collectionRelease();
                break;
            case R.id.btn_bitmap:
                bitmapMl();
                break;
        }
    }

    /**
     * 单例模式示例
     */
    private void singleInstanceML() {
/*
        //原代码，静态类存活时间长，引用当前活动作为context导致内存无法释放
        SingleInstanceClass.getInstance(context).showMessage("MLActivity使用单例模式");*/
        //修改后，该成application（在应用创建时便onCreate()，不必担心为空）
        //lbs解决单例模式内存泄漏
        SingleInstanceClass.getInstance(BaseApplication.getApplication()).showMessage("MLActivity使用单例模式");
        finish();
    }

    /**
     * 多线程示例
     */
    private void multiThreadMl() {
        myThread = new MyThread(this);
        myThread.start();
    }

    /**
     * Handler示例
     */
    private void handlerMl() {
        //解决方法：活动销毁时removeCallBacksAndMessage();
        handler = new Handler(getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Log.v(TAG, "Handler ==> handleMessage()，收到消息：" + msg);
            }
        };

        // 延时2分钟发送消息，模拟延时处理消息的场景
        handler.sendEmptyMessageDelayed(1, 2 * 60 * 1000);

        finish();
    }

    private static class MyHandler extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Log.v(TAG, "Handler ==> handleMessage()，收到消息：" + msg);
        }
    };

    /**
     * 注册示例
     */
    private void registerMl() {

        // 动态注册监听屏幕锁屏的广播
        screenReceiver = new ScreenReceiver();
        // 创建intent-filter对象
        IntentFilter filter = new IntentFilter();
        // 添加要注册的action
        filter.addAction("android.intent.action.SCREEN_OFF");
        // 注册广播接收者
        this.registerReceiver(screenReceiver, filter);

        //lbs解决
        this.unregisterReceiver(screenReceiver);
        // 关闭页面
        finish();
    }

    /**
     * 集合示例 - 内存泄露
     */
    private void collectionMl() {
        objectList = new ArrayList<>();

        for (int i = 0; i < 1000000; i++) {
            Object obj = new Object();
            objectList.add(obj);
            obj = null;
        }
    }

    /**
     * 集合示例 - 释放内存
     */
    private void collectionRelease() {
        // TODO 学生完成该方法
        //lbs解决集合内存泄漏
        if(objectList!=null) {
            objectList.clear();
            objectList=null;
        }
    }

    /**
     * Bitmap - 示例
     * */
    private void bitmapMl() {
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.picture);

        // TODO 学生解决Bitmap的内存问题。
        //  提示：1、可用软引用持有Bitmap；
        //       2、Bitmap使用完要及时回收。

        //lbs解决bitmap内存泄漏问题
        if(bitmap!=null){
            bitmap.recycle();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //lbs解决handler内存泄漏
        if(handler!=null){
            handler.removeCallbacksAndMessages(null);
        }
    }

    /**
     * 子线程
     * */
    private static class MyThread extends Thread {

        SoftReference<MemoryLeakActivity> softReference;

        public MyThread(MemoryLeakActivity memoryLeakActivity){
            softReference=new SoftReference<>(memoryLeakActivity);
        }

        @Override
        public void run() {
            Log.v(TAG, "多线程 ==> MyThread，开始执行");
            /*
            // 内存泄漏2：多线程
            // 1优化方法为设置为静态内部类并使用软引用
            // 关闭页面
            runOnUiThread(() -> {
                if (!isFinishing()) {
                    finish();
                }
            }); */
            //lbs解决多线程内存泄漏
            if(softReference!=null){
                softReference.get().runOnUiThread(()->{
                    if(!softReference.get().isFinishing()){
                        softReference.get().finish();
                    }
                });
            }

            try {
                // 模拟耗时任务
                Thread.sleep(2 * 60 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.v(TAG, "多线程 ==> MyThread，执行完毕");
        }
    }
}