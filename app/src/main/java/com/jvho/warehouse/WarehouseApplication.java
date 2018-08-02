package com.jvho.warehouse;

import android.app.Application;

import cn.bmob.v3.Bmob;

public class WarehouseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //第一：默认初始化
        Bmob.initialize(this, "");
    }
}
