package com.jvho.warehouse.model;

import cn.bmob.v3.BmobUser;

public class _User extends BmobUser {

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    private Boolean admin;
    private String warehouse;
    private Integer status;
}
