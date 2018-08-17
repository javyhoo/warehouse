package com.jvho.warehouse.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by JV on 2018/8/17.
 */
public class WarehouseGoods extends BmobObject {

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

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    private Integer status;
    private Integer amount;
    private String warehouse;
    private String goods;

}
