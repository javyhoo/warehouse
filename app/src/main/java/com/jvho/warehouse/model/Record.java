package com.jvho.warehouse.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by JV on 2018/8/17.
 */
public class Record extends BmobObject {

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

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

    private String warehouse;    // 仓库名
    private String user;    // 账户名
    private String organization;    // 机构名
    private String goods;   // 货物名
    private Integer amount; // 出入货数量，负数为出货；正数为入货


}
