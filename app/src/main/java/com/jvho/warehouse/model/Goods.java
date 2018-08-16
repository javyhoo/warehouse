package com.jvho.warehouse.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by JV on 2018/8/7.
 */
public class Goods extends BmobObject {

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    private String name;
    private String organization;    //机构ID
    private Integer status;
    private Integer amount;     //数量

}
