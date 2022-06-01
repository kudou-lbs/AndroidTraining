package com.global.training.optimize;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Trace;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.global.network.wrapper.RetrofitServiceManager;
import com.global.training.business.ArticlesResult;
import com.global.training.business.MpArticleList;
import com.global.training.business.MpArticleService;
import com.global.training.utils.DevicesUtil;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {
    public static final String TAG = SplashActivity.class.getSimpleName();

    private MpArticleList articlesList = null;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideNavigationBar(getWindow());
        setContentView(R.layout.activity_splash);
        adaptDisplayCutoutMode(this);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //修改了预加载文章数据函数，当获取到文章数据时即打开MainActivity并finish当前活动
                preLoadArticleData();
            }
        }, 0);
         Trace.beginSection("logSession_init");
         //startLogSession();
         //使用线程处理该函数
         new MyThread(this).run();
         Trace.endSection();

    }

    private void preLoadArticleData(){
        MpArticleService articleService = RetrofitServiceManager.getInstance().create(MpArticleService.class);
        Call<ArticlesResult> call = articleService.getArticleList("408","1");
        call.enqueue(new Callback<ArticlesResult>() {
            @Override
            public void onResponse(Call<ArticlesResult> call, Response<ArticlesResult> response) {
                Log.d(TAG, "onResponse---->"+response.body().getErrorCode());
                articlesList = response.body().getData();
                //获取成功则将获取到的数据发送至MainActivity
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                Bundle  bundle = new Bundle();
                bundle.putSerializable("articlesData",articlesList);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure(Call<ArticlesResult> call, Throwable t) {
                //获取失败也需要打开主活动，但提示故障原因如网络原因
                Toast.makeText(SplashActivity.this,"当前网络不稳定，数据初始化失败",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                Bundle  bundle = new Bundle();
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
    }

    private static class MyThread extends Thread{

         SoftReference<SplashActivity> softReference;
         public MyThread(SplashActivity activity){
             softReference=new SoftReference<>(activity);
         }

         @Override
        public void run() {
             if(null != softReference){
                 softReference.get().startLogSession();
             }
        }
    }

    private void startLogSession(){
        MpArticleService articleService = RetrofitServiceManager.getInstance().create(MpArticleService.class);

        Map<String,String> requestData = new HashMap<>();
        requestData.put("deviceId", DevicesUtil.getAndroidID(this));
        requestData.put("Language",DevicesUtil.getAppLanguage());
        requestData.put("Country",DevicesUtil.getAppCountry());
        requestData.put("Battery",DevicesUtil.getBatteryStatus(this));
        requestData.put("Model",DevicesUtil.getDeviceModel());
        requestData.put("Type",DevicesUtil.getDeviceType());
        requestData.put("TimeZone",DevicesUtil.getCurrentTimeZone());
        requestData.put("OSVersion",DevicesUtil.getOSVersion());

        //这个函数作用不明确，onResponse()也无实际意义
        Call<ResponseBody> call = articleService.startSession(requestData);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(!response.isSuccessful()){
                    try {
                        Log.e(TAG,"--response Code:"+ response.code());
                        Log.e(TAG,"--startLogSession:"+response.message());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }


    /**
     * 隐藏导航栏
     *
     * @param window
     */
    public static void hideNavigationBar(Window window) {
        if (Build.VERSION.SDK_INT >= 19) {
            View decorView = window.getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    /**
     * 设置适配刘海屏的模式
     */
    public static void setDisplayCutoutMode(Window window, int mode) {
        if (window != null && Build.VERSION.SDK_INT >= 28 && mode >= 0) {
            WindowManager.LayoutParams params = window.getAttributes();
            params.layoutInDisplayCutoutMode = mode;
            window.setAttributes(params);
        }
    }

    /**
     * 将应用程序的内容延伸到刘海区域
     *
     * @param activity
     */
    public static void adaptDisplayCutoutMode(Activity activity) {
        setDisplayCutoutMode(activity.getWindow(), WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES);
    }

}
