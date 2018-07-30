package com.jvho.warehouse.model;

import cn.bmob.v3.BmobObject;

public class Organization extends BmobObject {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;
}
