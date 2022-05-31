package com.lbs.training.service;

import android.app.Service;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;

public class MusicPlayService extends Service {
    private String allMusic[]=new String[]{"優河 - 灯火.mp3", "陈奕迅-七百年后.mp3", "陈奕迅-我们万岁.mp3"};
    private int pos=0;
    private MediaPlayer player;
    private AssetManager assetManager;

    public MusicPlayService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        //当执行完了onCreate后，就会执行onBind把操作歌曲的方法返回
        return new MyBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        openAssertMusic();
    }

    public void openAssertMusic(){
        //这里只执行一次，用于准备播放器
        assetManager = getAssets();
        player = new MediaPlayer();
        try {
            //打开音乐文件shot.mp3
            AssetFileDescriptor assetFileDescriptor = assetManager.openFd(allMusic[pos%allMusic.length]);
            player.reset();
            //设置媒体播放器的数据资源
            player.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());

            //准备资源
            player.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public void startPlayer(){
//        player.start();
//    }
//
//    public void stopPlayer(){
//        player.pause();
//    }

    public boolean isPlaying(){
        return player.isPlaying();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    //该方法包含关于歌曲的操作
    public class MyBinder extends Binder {

        //判断是否处于播放状态
        public boolean isPlaying(){
            return player.isPlaying();
        }

        //播放或暂停歌曲
        public void play() {
            if (player.isPlaying()) {
                player.pause();

            } else {
                player.start();
            }
            Log.e("服务", "播放音乐");
        }

        //返回歌曲的长度，单位为毫秒
        public int getDuration(){
            return player.getDuration();
        }

        //返回歌曲目前的进度，单位为毫秒
        public int getCurrenPostion(){
            return player.getCurrentPosition();
        }

        //设置歌曲播放的进度，单位为毫秒
        public void seekTo(int mesc){
            player.seekTo(mesc);
        }

        public void next(){
            pos=(pos+1)%allMusic.length;
            try {
                //打开音乐文件shot.mp3
                AssetFileDescriptor assetFileDescriptor = assetManager.openFd(allMusic[pos%allMusic.length]);
                player.reset();
                //设置媒体播放器的数据资源
                player.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());

                //准备资源
                player.prepare();
                player.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void last(){
            if(pos<=0)pos=allMusic.length-1;
            else pos--;
            try {
                //打开音乐文件shot.mp3
                AssetFileDescriptor assetFileDescriptor = assetManager.openFd(allMusic[pos%allMusic.length]);
                player.reset();
                //设置媒体播放器的数据资源
                player.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());

                //准备资源
                player.prepare();
                player.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public String getCurrentMusic(){
            return allMusic[pos];
        }
    }
}
