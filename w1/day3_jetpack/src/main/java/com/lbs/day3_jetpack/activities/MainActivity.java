package com.lbs.day3_jetpack.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.lbs.day3_jetpack.R;
import com.lbs.day3_jetpack.custom.User;
import com.lbs.day3_jetpack.databinding.ActivityMainBinding;
import com.lbs.day3_jetpack.view_model.MainViewModel;

public class MainActivity extends AppCompatActivity {

    private MainViewModel mainViewModel;
    private TextInputEditText etAccount, etPwd;
    private TextView tvAccount, tvPwd;
    private ProgressBar pb_loading;

    private ActivityMainBinding dataBinding;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //normalInit();
        dataBindingInit();
    }
    
    //原初始化函数，无dataBinding
    /*private void normalInit(){
        setContentView(R.layout.activity_main);

        mainViewModel=new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(MainViewModel.class);
        etAccount=findViewById(R.id.et_account);
        etPwd=findViewById(R.id.et_pwd);
        tvAccount=findViewById(R.id.tv_account);
        tvPwd=findViewById(R.id.tv_pwd);

        findViewById(R.id.btn_login).setOnClickListener(v -> {
            *//*
            //普通
            mainViewModel.account=etAccount.getText().toString().trim();
            mainViewModel.pwd=etPwd.getText().toString().trim();*//*

            //LiveData
            //使用setValue（只能在主线程调用）进行赋值，也可以使用postValue（可在任何线程调用）
            mainViewModel.account.setValue(etAccount.getText().toString().trim());
            mainViewModel.pwd.setValue(etPwd.getText().toString().trim());

            if(mainViewModel.account.getValue().isEmpty()){
                if(mainViewModel.pwd.getValue().isEmpty()){
                    myToast("请输入账号和密码");
                }else{
                    myToast("请输入账号");
                }
            }else if(mainViewModel.pwd.getValue().isEmpty()){
                myToast("请输入密码");
            }else{
                myToast("登录成功");
            }
        });
        //自从有了LiveData（用于监听对象的更新），就可以使用观察者模式更新需要更新的TextView啦
        mainViewModel.account.observe(this, account->tvAccount.setText("账号："+account));
        mainViewModel.pwd.observe(this, pwd->tvPwd.setText("密码："+pwd));
        //简化写法，原来的是需要new Observer对象并重写onChanged函数，那样比较清晰，但lambda表达式更简单
    }*/

    private void dataBindingInit(){
        //用于数据绑定
        //方法返回ViewDataBinding对象，对象的生成取决于Activity，如MainActivity则生成ActivityMainBinding
        dataBinding= DataBindingUtil.setContentView(this, R.layout.activity_main);
        user=new User("","");
        dataBinding.setUser(user);
        pb_loading=findViewById(R.id.pb_loading);

        //通过生成的dataBinding可直接访问xml里的项
        //单向绑定，数据只会通过textView流向user
        dataBinding.btnLogin.setOnClickListener(v -> {
            String tmpAccount=dataBinding.etAccount.getText().toString().trim();
            String tmpPwd=dataBinding.etPwd.getText().toString().trim();
            if(tmpAccount.isEmpty()){
                if(tmpPwd.isEmpty()){
                    myToast("请输入账号和密码");
                }else{
                    myToast("请输入账号");
                }
            }else if(tmpPwd.isEmpty()){
                myToast("请输入密码");
            }else{
                pb_loading.setVisibility(View.VISIBLE);
                mHandler.postDelayed(mRunnale,3000);
            }
        });
    }

    //创建账号成功
    private void buildSucceed(){
        myToast("账号创建成功");
        user.setIcon(getDrawable(R.drawable.kong));
        user.setAccount(dataBinding.etAccount.getText().toString().trim());
        user.setPwd(dataBinding.etPwd.getText().toString().trim());

        pb_loading.setVisibility(View.GONE);
    }

    Handler mHandler=new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1:
                    buildSucceed();
                    break;
                default:
                    break;
            }
        }
    };

    Runnable mRunnale=new Runnable() {
        @Override
        public void run() {
            Message msg=new Message();
            msg.what=1;
            mHandler.sendMessage(msg);
        }
    };

    private void myToast(String output){
        Toast.makeText(this,output,Toast.LENGTH_SHORT).show();
    }
}