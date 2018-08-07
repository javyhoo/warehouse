package com.jvho.warehouse.model;

import cn.bmob.v3.BmobObject;

public class Warehouse extends BmobObject {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    private Integer status;
    private String name;
}
