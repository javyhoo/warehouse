package com.jvho.warehouse.service;

import com.jvho.warehouse.model._User;
import com.orhanobut.logger.Logger;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class UserService {

    public void saveUser(String username, String password) {
        _User user = new _User();
        user.setUsername(username);
        user.setPassword(password);
        user.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
//                    toast("添加数据成功，返回objectId为："+objectId);
                    Logger.d("UserService", "添加数据成功，返回objectId为：" + objectId);
                } else {
//                    toast("创建数据失败：" + e.getMessage());
                    Logger.d("UserService", "创建数据失败：" + e.getMessage());
                }
            }
        });
    }

    public void findUsers(final List<_User> users, String username) {
        BmobQuery<_User> query = new BmobQuery<>();
        query.addWhereEqualTo("username", username);
        query.findObjects(new FindListener<_User>() {
            @Override
            public void done(List<_User> list, BmobException e) {
                if (null == e) {
                    users.addAll(list);
                } else {
                    Logger.d("UserService", "查询数据失败：" + e.getMessage());
                }
            }
        });
    }
}
