package com.global.training.optimize;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.os.Debug;
import android.os.Trace;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adjust.sdk.Adjust;
import com.adjust.sdk.AdjustConfig;
import com.adjust.sdk.OnDeviceIdsRead;
import com.adjust.sdk.SharedPreferencesManager;
import com.global.network.wrapper.RetrofitServiceManager;
import com.global.training.business.ArticleInfo;
import com.global.training.business.ArticlesResult;
import com.global.training.business.MpArticleList;
import com.global.training.business.MpArticleService;
import com.global.training.utils.EncryptUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private List<ArticleInfo> mDatas;
    private ArticleAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle bundleData =  getIntent().getExtras();
        if(null != bundleData){
            MpArticleList mpArticleList = (MpArticleList) bundleData.getSerializable("articlesData");
            mDatas = mpArticleList.getDatas();
        }

        //初始化界面布局
        initView();
        Trace.beginSection("prepareLoadAESKey_init");
        //准备网络请求AES key,与后台通信进行加密校验信息
        String aesKey = prepareLoadAESKey();
        Trace.endSection();
        //加载公众号文章列表
        Trace.beginSection("MpListData_init");
        initMpListData();
        Trace.endSection();

    }

    private void initView(){
        mRecyclerView = (RecyclerView) findViewById(R.id.mp_articlelist_container);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter = new ArticleAdapter());
    }


    private void initMpListData(){
        mDatas = new ArrayList<>();
        MpArticleService  articleService = RetrofitServiceManager.getInstance().create(MpArticleService.class);
        Call<ArticlesResult> call = articleService.getArticleList("408","1");
        call.enqueue(new Callback<ArticlesResult>() {
            @Override
            public void onResponse(Call<ArticlesResult> call, Response<ArticlesResult> response) {
                Log.d(TAG, "onResponse---->"+response.body().getErrorCode());
                MpArticleList articleList = response.body().getData();
                Log.d(TAG, "onResponse---->"+articleList.getCurPage());
                Log.d(TAG, "onResponse---->"+articleList.getTotal());

                mDatas = response.body().getData().getDatas();
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArticlesResult> call, Throwable t) {

            }
        });
    }


    private String prepareLoadAESKey(){
        String encryptData = loadEncryptFileFromAsset("encrypt_data.json");
        try {
            JSONArray encArray = new JSONArray(encryptData);
            long timestamp = System.currentTimeMillis();
            while(timestamp > encArray.length()){
                timestamp = timestamp / 100;
            }
            JSONObject encJson = encArray.getJSONObject((int)timestamp);
            String source = encJson.getString("source");
            return  EncryptUtils.md5(source);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String loadEncryptFileFromAsset(String fileName){
        try {
            InputStreamReader inputReader = new InputStreamReader(getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line="";
            String Result="";
            while((line = bufReader.readLine()) != null)
                Result += line;
            return Result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }




    class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleHolder>
    {

        @Override
        public ArticleHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            ArticleHolder holder = new ArticleHolder(LayoutInflater.from(
                    MainActivity.this).inflate(R.layout.article_item, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(ArticleHolder holder, int position)
        {
            holder.tv.setText(mDatas.get(position).getTitle());
        }

        @Override
        public int getItemCount()
        {
            return mDatas.size();
        }

        class ArticleHolder extends RecyclerView.ViewHolder
        {

            TextView tv;

            public ArticleHolder(View view)
            {
                super(view);
                tv = (TextView) view.findViewById(R.id.tv_title);
            }
        }
    }


}