package com.lbs.day3_jetpack.view_model;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lbs.day3_jetpack.custom.User;

/**
 * MVVM框架下每个activity都对应一个ViewModel
 * */
public class MainViewModel extends ViewModel {
/*
    //普通
    public String account;
    public String pwd;*/
    //LiveData 可感知其他应用组件的生命周期。可确保LiveData仅更新处于活跃生命周期的应用组件观察者
    //这里使用MutableLiveData表示值的内容可变动，LiveData不可变
    public MutableLiveData<String> account=new MutableLiveData<>();
    public MutableLiveData<String> pwd=new MutableLiveData<>();

/*    //dataBinding
    public MutableLiveData<User> user;
    public MutableLiveData<User> getUser(){
        if(user==null){
            user=new MutableLiveData<>();
        }
        return user;
    }*/
}
