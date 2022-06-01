package com.example.memoryoptimization;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 内存抖动示例
 */
public class MemoryChurnActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MemoryChurnActivity.class.getSimpleName();

    private Button btnStringSplit;
    private Button btnResource;
    private Button btnLocalVariable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_churn);

        btnStringSplit = findViewById(R.id.btn_string_split);
        btnStringSplit.setOnClickListener(this);

        btnResource = findViewById(R.id.btn_resource);
        btnResource.setOnClickListener(this);

        btnLocalVariable = findViewById(R.id.btn_local_variable);
        btnLocalVariable.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_string_split:
                stringSplitMc();
                break;
            case R.id.btn_resource:
                resourceMc();
                break;
            case R.id.btn_local_variable:
                localVariableMc();
                break;
        }
    }

    /**
     * 字符串拼接
     */
    private void stringSplitMc() {
        //String str = "";
        //lbs解决内存抖动
        StringBuilder str=new StringBuilder("");
        for (int i = 0; i < 30000; i++) {
            //str += i + "-";
            str.append(i+"-");
        }
        Log.v(TAG, "内存抖动 ==> stringSplitMc()，str=" + str);
    }

    /**
     * 资源复用
     */
    private void resourceMc() {
        //Thread thread;
        //lbs解决内存抖动
        ExecutorService executorService=Executors.newSingleThreadExecutor();

        for (int i = 0; i < 10000; i++) {
            int number = i;
//            thread = new Thread(() -> Log.v(TAG, "内存抖动 ==> resourceMc()，运行线程：" + number));
//            thread.start();
            executorService.execute(new Thread(()->
                    Log.v(TAG, "内存抖动 ==> resourceMc()，运行线程：" + number)));
        }
    }

    /**
     * 循环创建大量局部变量
     */
    private void localVariableMc() {
        //复用对象
        //lbs解决内存抖动
        BookInfo bookInfo = new BookInfo();
        bookInfo.setPrice(30.0f);
        for (int i = 0; i < 30000000; i++) {
            bookInfo.setNumber(i);
        }
    }
}