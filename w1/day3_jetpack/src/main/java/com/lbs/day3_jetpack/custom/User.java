package com.lbs.day3_jetpack.custom;

import android.graphics.drawable.Drawable;

import androidx.databinding.BaseObservable;


/**
 * 继承自BaseObservable
 * */
public class User extends BaseObservable {
    private Drawable icon;
    private String account;
    private String pwd;

    public Drawable getIcon() {
        return icon;
    }

    public String getAccount() {
        return account;
    }

    public String getPwd() {
        return pwd;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public void setAccount(String account) {
        this.account = account;
        //通知全部属性更改
        notifyChange();

        //dataBinding
        //notifyPropertyChanged(BR.account);
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
        //notifyPropertyChanged(BR.pwd);
    }

    public User(String account, String pwd){
        this.account=account;
        this.pwd=pwd;
    }
}
