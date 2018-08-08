package com.jvho.warehouse.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.jvho.core.base.BaseActivity;
import com.jvho.core.base.SweetAlertDialog;
import com.jvho.core.navigator.NavigationView;
import com.jvho.warehouse.R;
import com.jvho.warehouse.model.Warehouse;
import com.jvho.warehouse.ui.adapter.WarehouseAdapter;
import com.jvho.warehouse.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by JV on 2018/8/7.
 */
public class WarehouseManageActivity extends BaseActivity {

    public static final String TAG_MANAGEWAREHOUSETITLE = "tag_manage_warehouse_title";

    private String title;
    private List<Warehouse> data = new ArrayList<>();
    private WarehouseAdapter adapter;

    @BindView(R.id.list_manage)
    RecyclerView listView;

    public static void gotoWarehouseManageActivity(Context context, String title) {
        Intent intent = new Intent(context, WarehouseManageActivity.class);
        intent.putExtra(TAG_MANAGEWAREHOUSETITLE, title);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_manage;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        title = getIntent().getStringExtra(TAG_MANAGEWAREHOUSETITLE);

        setNavigation();
        setListView();
    }

    private void setNavigation() {
        navigation.setTitle(title);
        navigation.setRightButton(R.drawable.actionbar_add, new NavigationView.RightBtnClickListener() {
            @Override
            public void onClick(View view) {
                new SweetAlertDialog.Builder(WarehouseManageActivity.this)
                        .setType(SweetAlertDialog.INPUT_TYPE)
                        .setTitle("新增仓库")
                        .setCancelable(true)
                        .setPositiveButton("新增", new SweetAlertDialog.OnDialogClickListener() {
                            @Override
                            public void onClick(Dialog dialog, int which, @Nullable String inputMsg) {
                                if (!TextUtils.isEmpty(inputMsg)) {
                                    isExist(inputMsg);
                                }
                            }
                        })
                        .show();
            }
        });
    }

    private void isExist(final String name) {
        BmobQuery<Warehouse> query = new BmobQuery<>();
        query.addWhereEqualTo("name", name);
        query.findObjects(new FindListener<Warehouse>() {
            @Override
            public void done(List<Warehouse> list, BmobException e) {
                if (null == e) {
                    new ToastUtil().showTipToast(WarehouseManageActivity.this, name + "已存在，请检查再新增！");
                } else {
                    saveWarehouse(name);
                }
            }
        });
    }

    private void saveWarehouse(final String name) {
        Warehouse warehouse = new Warehouse();
        warehouse.setName(name);
        warehouse.setStatus(1);
        warehouse.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    new ToastUtil().showTipToast(WarehouseManageActivity.this,
                            "新增成功，返回objectId为：" + objectId);
                    adapter.queryData();
                } else {
                    saveWarehouse(name);
                }
            }
        });
    }

    private void setListView() {
        adapter = new WarehouseAdapter(this, data);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        listView.setLayoutManager(manager);
        //添加Android自带的分割线
        listView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        listView.setAdapter(adapter);

        adapter.queryData();
    }

}
