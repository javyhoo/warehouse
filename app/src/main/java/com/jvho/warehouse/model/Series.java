package com.jvho.warehouse.model;

import cn.bmob.v3.BmobObject;

public class Series extends BmobObject {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    private String name;
    private String organization;
}
