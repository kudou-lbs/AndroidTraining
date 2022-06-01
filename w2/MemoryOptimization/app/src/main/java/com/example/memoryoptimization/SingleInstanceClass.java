package com.example.memoryoptimization;

import android.content.Context;
import android.widget.Toast;

public class SingleInstanceClass {

    private static SingleInstanceClass instance;
    private Context context;

    /**
     * 获取实例
     *
     * @param context
     */
    public static SingleInstanceClass getInstance(Context context) {

        if (instance == null) {
            synchronized (SingleInstanceClass.class) {
                if (instance == null) {
                    instance = new SingleInstanceClass(context);
                }
            }
        }

        return instance;
    }

    /**
     * 构造方法
     *
     * @param context
     * */
    private SingleInstanceClass(Context context) {
        this.context = context;
    }

    /**
     * Toast显示信息
     */
    public void showMessage(String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
