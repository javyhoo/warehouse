package com.jvho.warehouse.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.jvho.core.base.BaseActivity;
import com.jvho.core.navigator.NavigationView;
import com.jvho.warehouse.R;
import com.jvho.warehouse.model.Warehouse;
import com.jvho.warehouse.model._User;
import com.jvho.warehouse.ui.widget.LabelView;
import com.jvho.warehouse.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by JV on 2018/8/15.
 */
public class UserAddActivity extends BaseActivity {

    public static final String TAG_TITLE = "tag_title";

    private static final String[] admin = {"管理员", "一般使用者"};
    private List<String> arrWarehouse = new ArrayList<>();
    private String title, warehouse;
    private Boolean isAdmin;

    @BindView(R.id.add_user_name)
    LabelView userName;
    @BindView(R.id.add_user_password)
    LabelView userPassword;
    @BindView(R.id.add_user_warehouse)
    Spinner userWarehouse;
    @BindView(R.id.add_user_admin)
    Spinner userAdmin;

    public static void gotoUserAddActivity(Context context, String title) {
        Intent intent = new Intent(context, UserAddActivity.class);
        intent.putExtra(TAG_TITLE, title);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_user_add;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        title = getIntent().getStringExtra(TAG_TITLE);

        setNavigation();
        queryWarehouse();
        initView();
    }

    private void setNavigation() {
        navigation.setTitle(title);
        navigation.setRightButton("新增", new NavigationView.RightBtnClickListener() {
            @Override
            public void onClick(View view) {
                saveUser();
            }
        });
    }

    private void queryWarehouse() {
        BmobQuery<Warehouse> query = new BmobQuery<>();
        query.addWhereEqualTo("status", 1);
        query.order("name");
        query.findObjects(new FindListener<Warehouse>() {
            @Override
            public void done(List<Warehouse> list, BmobException e) {
                arrWarehouse.clear();

                arrWarehouse.add("无");
                if (null == e) {
                    for (Warehouse w : list) {
                        arrWarehouse.add(w.getName());
                    }
                }

                initWarehouseSpinner();
            }
        });
    }

    private void initView() {
        userName.setLabel("账户名称：");
        userName.setHint("请输入账户名称");
        userName.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

        userPassword.setLabel("账户密码：");
        userPassword.setHint("请输入账户密码");
        userPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);

        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, admin);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userAdmin.setAdapter(adapter);
        userAdmin.setOnItemSelectedListener(new SpinnerSelectedListener());
        userAdmin.setVisibility(View.VISIBLE);
    }

    private void initWarehouseSpinner() {
        ArrayAdapter warehouseAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrWarehouse);
        warehouseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userWarehouse.setAdapter(warehouseAdapter);
        userWarehouse.setOnItemSelectedListener(new WarehouseSpinnerSelectedListener());
        userWarehouse.setVisibility(View.VISIBLE);
    }

    class WarehouseSpinnerSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            if (arg2 < 1) {
                warehouse = "";
            } else {
                warehouse = arrWarehouse.get(arg2 - 1);
            }

        }

        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }

    class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            String strAdmin = admin[arg2];
            isAdmin = "管理员".equals(strAdmin);
        }

        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }

    private void saveUser() {
        final String name = userName.getEdtTextValue();
        String password = userPassword.getEdtTextValue();

        if (TextUtils.isEmpty(name)) {
            new ToastUtil().showTipToast(UserAddActivity.this, "请输入账户名称", null);
            return;
        }

        if (TextUtils.isEmpty(password)) {
            password = "88888888";
        }

        _User user = new _User();
        user.setUsername(name);
        user.setPassword(password);
        user.setStatus(1);
        user.setAdmin(isAdmin);
        user.setWarehouse(warehouse);
        user.signUp(new SaveListener<_User>() {
            @Override
            public void done(_User user, BmobException e) {
                if (e == null) {
                    new ToastUtil().showTipToast(UserAddActivity.this, "新增成功", new ToastUtil.OnTipsListener() {
                        @Override
                        public void onClick() {
                            finish();
                        }
                    });
                } else if ((e.toString()).contains("already taken")) {
                    user.setStatus(1);
                    user.update(new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                new ToastUtil().showTipToast(UserAddActivity.this, "新增成功", new ToastUtil.OnTipsListener() {
                                    @Override
                                    public void onClick() {
                                        finish();
                                    }
                                });
                            }
                        }
                    });
                } else if ((e.toString()).contains("has duplicate value")) {
                    new ToastUtil().showTipToast(UserAddActivity.this, name + "已存在，请检查！", null);
                } else {
                    saveUser();
                }
            }
        });

    }

}
