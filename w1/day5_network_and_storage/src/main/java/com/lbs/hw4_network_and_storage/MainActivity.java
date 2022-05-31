package com.lbs.hw4_network_and_storage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button getInfoButton;
    Button saveInfoButton;
    RecyclerView recyclerView;
    String TAG="MainActivity";
    List<Info> allTitles;
    MyAdapter myAdapter;
    String fileName="dataTitles";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    //初始化
    private void init() {
        getInfoButton=findViewById(R.id.bt_getInfo);
        saveInfoButton=findViewById(R.id.bt_saveInfo);
        recyclerView=findViewById(R.id.rv_infoList);

        getInfoButton.setOnClickListener(this);
        saveInfoButton.setOnClickListener(this);
        recyclerView.setOnClickListener(this);

        requestStoragePermission();
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_getInfo:
                asyncGetRequestByOkHttp();
                break;
            case R.id.bt_saveInfo:
                saveInfoFromOkHttp();
                break;
            default:
                break;
        }
    }

    //申请外部读写权限
    private void requestStoragePermission(){
        String[] checkList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        for (int i = 0; i < checkList.length; i++) {
            if(PackageManager.PERMISSION_GRANTED!= ActivityCompat.checkSelfPermission(this,checkList[i])){
                ActivityCompat.requestPermissions(this,checkList,100);
                break;
            }
        }
    }

    //异步请求公众号列表
    private void asyncGetRequestByOkHttp(){
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder()
                .url("https://wanandroid.com/wxarticle/list/408/1/json")
                .build();
        Call call=client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG,"异步请求失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    String responseMessage=response.body().string();
                    //Log.i(TAG,"请求返回数据如下：\n"+responseMessage);
                    Log.i(TAG,"调用显示");
                    //利用返回的数据进行展示
                    showInfoTitle(responseMessage);
                }else {
                    throw new IOException("Unexpected code "+ response);
                }
            }
        });
    }

    //信息展示函数
    private void showInfoTitle(String s){
        try {
            JSONObject jsonObject= JSON.parseObject(s);
            JSONObject data=jsonObject.getJSONObject("data");
            JSONArray datas=data.getJSONArray("datas");
            //Log.d(TAG,datas.toString());

            allTitles=new ArrayList<>();
            for (int i = 0; i < datas.size(); i++) {
                JSONObject tmpObject=datas.getJSONObject(i);
                String tmpTitle=tmpObject.getString("title");
                Info tmpInfo=new Info();
                tmpInfo.title=tmpTitle;
                allTitles.add(tmpInfo);
            }
            Message message=new Message();
            message.what=1;
            mHandler.sendMessage(message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //用于处理异步消息更改recyclerView
    Handler mHandler=new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1:
                    myAdapter=new MyAdapter(MainActivity.this, allTitles);
                    recyclerView.setAdapter(myAdapter);
                    break;
                default:
                    break;
            }
        }
    };

    //保存题目列表
    private void saveInfoFromOkHttp(){
//        List<Info> infoList=new ArrayList<>();
//        recyclerView.setAdapter(new MyAdapter(this,infoList));
        BufferedWriter writer = null;
        try {
            JSONArray array=new JSONArray();
            for (int i = 0; i < allTitles.size(); i++) {
                Info data=allTitles.get(i);
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("title",data.title);
                array.add(jsonObject);
            }
            FileOutputStream out=openFileOutput("data",Context.MODE_PRIVATE);
            writer=new BufferedWriter(new OutputStreamWriter(out));
            writer.write(array.toString());
            Log.d("TAG",array.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if(writer!=null){
                    writer.close();
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

}