package com.lbs.training.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lbs.training.R;
import com.lbs.training.service.MusicPlayService;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private MyConnection conn;
    private MusicPlayService.MyBinder musicControl;
    boolean isPlaying=false;

    private SeekBar seekBar;    //进度条
    private Button mPlayMenu;   //音乐列表
    private Button playBtn;  //播放按钮
    private Button mPlayPattern;  //播放模式按钮
    private Button mPlayLast;  //播放上一首按钮
    private Button mPlayNext;  //播放下一首按钮

    private boolean isUserTouchProgressBar = false;   //判断手是否触摸进度条的状态

    public TextView mMusicName;  //显示歌曲名称
    public TextView mMusicArtist;  //显示演唱者

    //广播接收器
    private IntentFilter intentFilter;
    private NetworkChangeReceiver networkChangeReceiver;
    class NetworkChangeReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            Network network=connectivityManager.getActiveNetwork();
            if(network==null){
                Toast.makeText(context,"无网络", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context,"当前网络可用", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null)actionBar.hide();

        initView();
        initBroadcast();
    }

    private void initView(){
        playBtn = (Button) findViewById(R.id.stop_continue);
        mPlayLast=findViewById(R.id.last_music);
        mPlayNext=findViewById(R.id.next_music);
        seekBar = (SeekBar) findViewById(R.id.seek_bar);
        mMusicArtist=findViewById(R.id.singer_name);
        mMusicName=findViewById(R.id.music_name);

        Intent intent3 = new Intent(this, MusicPlayService.class);
        conn = new MyConnection();
        //使用混合的方法开启服务，
        startService(intent3);
        bindService(intent3, conn, BIND_AUTO_CREATE);

        playBtn.setOnClickListener(this);
        mPlayLast.setOnClickListener(this);
        mPlayNext.setOnClickListener(this);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //进度条改变
                if (fromUser){
                    musicControl.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //开始触摸进度条
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //停止触摸进度条
            }
        });
    }

    private void initBroadcast(){
        intentFilter=new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeReceiver=new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver,intentFilter);
    }

    private void setTextShow(){
        String info=musicControl.getCurrentMusic();
        info.replaceAll(" ","");
        int s_p=info.indexOf('-');
        mMusicName.setText(info.substring(s_p+1,info.length()-4));
        mMusicArtist.setText(info.substring(0,s_p));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.stop_continue:
                play();
                break;
            case R.id.last_music:
                musicControl.last();
                break;
            case R.id.next_music:
                musicControl.next();
                break;
            default:
                break;
        }
        setTextShow();
    }

    private static final int UPDATE_PROGRESS = 0;
    //使用handler定时更新进度条
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_PROGRESS:
                    updateProgress();
                    break;
            }
        }
    };

    private class MyConnection implements ServiceConnection {

        //服务启动完成后会进入到这个方法
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //获得service中的MyBinder
            musicControl = (MusicPlayService.MyBinder) service;
            //更新按钮的文字
            updatePlayText();
            //设置进度条的最大值
            seekBar.setMax(musicControl.getDuration());
            //设置进度条的进度
            seekBar.setProgress(musicControl.getCurrenPostion());

            setTextShow();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //进入到界面后开始更新进度条
        if (musicControl != null){
            handler.sendEmptyMessage(UPDATE_PROGRESS);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //退出应用后与service解除绑定
        unbindService(conn);

        unregisterReceiver(networkChangeReceiver);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //停止更新进度条的进度
        handler.removeCallbacksAndMessages(null);
    }

    //更新进度条
    private void updateProgress() {
        int currenPostion = musicControl.getCurrenPostion();
        seekBar.setProgress(currenPostion);
        //使用Handler每500毫秒更新一次进度条
        handler.sendEmptyMessageDelayed(UPDATE_PROGRESS, 500);
    }


    //更新按钮的文字
    public void updatePlayText() {
        if (musicControl.isPlaying()) {
            //playBtn.setText("播放");
            playBtn.setBackground(getResources().getDrawable(R.drawable.go_on1,null));
            handler.sendEmptyMessage(UPDATE_PROGRESS);
        } else {
            //playBtn.setText("暂停");
            playBtn.setBackground(getResources().getDrawable(R.drawable.stop1,null));
        }
    }

    //调用MyBinder中的play()方法
    public void play() {
        musicControl.play();
        updatePlayText();
    }

}