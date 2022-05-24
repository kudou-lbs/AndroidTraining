package com.lbs.day2_recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<AppInfo> appInfoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init(){
        RecyclerView recyclerView=findViewById(R.id.recycler_view);
        initAppInfoList();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        AppInfoAdapter adapter=new AppInfoAdapter(appInfoList,this);
        recyclerView.setAdapter(adapter);
    }

    private void initAppInfoList(){
        appInfoList=new ArrayList<>();

        PackageManager pm=getPackageManager();
        @SuppressLint("WrongConstant")
        List<ApplicationInfo> installList=
                pm.getInstalledApplications(PackageManager.PERMISSION_GRANTED);
        for(ApplicationInfo singleApp:installList){
            Drawable tempAppIcon=singleApp.loadIcon(pm);
            String tempAppName= (String) singleApp.loadLabel(pm);
            AppInfo appInfo=new AppInfo();
            appInfo.icon=tempAppIcon;
            appInfo.name=tempAppName;
            appInfoList.add(appInfo);
            Log.d("fuckkk","add");
        }
    }
}