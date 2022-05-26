package com.lbs.day3_jetpack.custom;

import androidx.databinding.BaseObservable;


/**
 * 继承自BaseObservable
 * */
public class User extends BaseObservable {
    private String account;
    private String pwd;

    public String getAccount() {
        return account;
    }

    public String getPwd() {
        return pwd;
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
