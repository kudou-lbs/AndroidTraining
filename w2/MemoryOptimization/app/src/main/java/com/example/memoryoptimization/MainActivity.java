package com.example.memoryoptimization;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_memory_leak).setOnClickListener(v -> memoryLeak());
        findViewById(R.id.btn_memory_churn).setOnClickListener(v -> memoryChurn());
    }

    /**
     * 内存泄露
     * */
    private void memoryLeak() {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, MemoryLeakActivity.class);
        startActivity(intent);
    }

    /**
     * 内存抖动
     * */
    public void memoryChurn() {
        startActivity(new Intent(this, MemoryChurnActivity.class));
    }
}