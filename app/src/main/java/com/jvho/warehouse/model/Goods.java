package com.jvho.warehouse.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by JV on 2018/8/7.
 */
public class Goods extends BmobObject {

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
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

    private String warehouse;
    private String organization;
    private String series;
    private Integer status;
    private Integer amount;
}
