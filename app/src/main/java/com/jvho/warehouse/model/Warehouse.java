package com.jvho.warehouse.model;

import cn.bmob.v3.BmobObject;

public class Warehouse extends BmobObject {

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    private String series;
    private String name;
    private Integer amount;
}
