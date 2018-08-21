package com.jvho.warehouse.model;

import cn.bmob.v3.BmobObject;

/**
 * 问题反馈
 * Created by JV on 2018/8/20.
 */
public class Feedback extends BmobObject {

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    private String user;
    private String title;
    private String issue;
    private Integer status;
}
