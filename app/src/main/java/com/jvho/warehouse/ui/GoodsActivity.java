package com.jvho.warehouse.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.jvho.core.base.BaseActivity;
import com.jvho.warehouse.R;
import com.jvho.warehouse.model.Goods;
import com.jvho.warehouse.model.WarehouseGoods;
import com.jvho.warehouse.ui.adapter.GoodsAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class GoodsActivity extends BaseActivity {

    public static final String TAG_DEPARTMENTID = "tag_department_id";
    public static final String TAG_DEPARTMENTTITLE = "tag_department_title";

    private SearchView searchView;
    private ExpandableListView listView;
    private GoodsAdapter adapter;
    private List<Goods> goodses = new ArrayList<>();
    private List<List<WarehouseGoods>> warehouses = new ArrayList<>();
    private String organizationName, title;

    public static void gotoGoodsActivity(Context context, String organizationName, String title) {
        Intent intent = new Intent(context, GoodsActivity.class);
        intent.putExtra(TAG_DEPARTMENTID, organizationName);
        intent.putExtra(TAG_DEPARTMENTTITLE, title);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_goods;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        organizationName = getIntent().getStringExtra(TAG_DEPARTMENTID);
        title = getIntent().getStringExtra(TAG_DEPARTMENTTITLE);

        if (TextUtils.isEmpty(organizationName))
            finish();

        setNavigation();
        getParentData();
        initView();
    }

    private void setNavigation() {
        navigation.setTitle(title);
        navigation.hideRightButton();
    }

    private void initView() {
        searchView = findViewById(R.id.search_series);
        listView = findViewById(R.id.list_series);

        searchView.setQueryHint("请输入您要搜索的系列名称");

        adapter = new GoodsAdapter(this, goodses, warehouses);
        listView.setAdapter(adapter);
    }

    private void getParentData() {
        BmobQuery<Goods> query = new BmobQuery<>();
        query.addWhereEqualTo("status", 1);
        query.addWhereEqualTo("organization", organizationName);
        query.order("name");
        query.findObjects(new FindListener<Goods>() {
            @Override
            public void done(List<Goods> list, BmobException e) {
                if (e == null) {
                    goodses.clear();

                    if (list.size() > 0) {
                        goodses.addAll(list);

                        getChildrenData();
                    } else {
                        Toast.makeText(GoodsActivity.this, "结果为空", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(GoodsActivity.this, "货物查询失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getChildrenData() {
        warehouses.clear();

        for (int i = 0; i < goodses.size(); i++) {
            String goodsName = goodses.get(i).getName();

            BmobQuery<WarehouseGoods> query = new BmobQuery<>();
            query.addWhereEqualTo("status", 1);
            query.addWhereEqualTo("goods", goodsName);
            query.order("warehouse");
            query.findObjects(new FindListener<WarehouseGoods>() {
                @Override
                public void done(List<WarehouseGoods> list, BmobException e) {
                    if (e == null) {
                        warehouses.add(list);

                        if (goodses.size() == warehouses.size()) {
                            adapter.refresh(goodses, warehouses);
                        }
                    }
                }
            });

        }
    }

}
