package com.jvho.warehouse.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.SearchView;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.jvho.core.base.BaseActivity;
import com.jvho.warehouse.R;
import com.jvho.warehouse.model.Series;
import com.jvho.warehouse.model.Warehouse;
import com.jvho.warehouse.ui.adapter.GoodsAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SQLQueryListener;

public class GoodsActivity extends BaseActivity {

    public static final String TAG_DEPARTMENTID = "tag_department_id";

    private SearchView searchView;
    private ExpandableListView listView;
    private GoodsAdapter adapter;
    private List<Series> seriesList = new ArrayList<>();
    private List<List<Warehouse>> warehouseList = new ArrayList<>();
    private String organizationId;

    public static void gotoGoodsActivity(Context context, String organizationId) {
        Intent intent = new Intent(context, GoodsActivity.class);
        intent.putExtra(TAG_DEPARTMENTID, organizationId);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_goods;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        organizationId = getIntent().getStringExtra(TAG_DEPARTMENTID);

        organizationId = "kWm2555I";

        setNavigation();
        getParentData();
        initView();
    }

    private void setNavigation() {
        navigation.setTitle("货物");
        navigation.hideRightButton();
    }

    private void initView() {
        searchView = findViewById(R.id.search_series);
        listView = findViewById(R.id.list_series);

        searchView.setQueryHint("请输入您要搜索的系列名称");

        adapter = new GoodsAdapter(this, seriesList, warehouseList);
        listView.setAdapter(adapter);
    }

    private void getParentData() {
        String bql = "select * from series where organization like '" + organizationId + "'";
        BmobQuery<Series> query = new BmobQuery<>();
        query.setSQL(bql);
        query.doSQLQuery(new SQLQueryListener<Series>() {
            @Override
            public void done(BmobQueryResult<Series> bmobQueryResult, BmobException e) {
                if (e == null) {
                    seriesList.addAll(bmobQueryResult.getResults());

                    getChildrenData();
                } else {
                    Toast.makeText(GoodsActivity.this, "机构表查询失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getChildrenData() {
        for (int i = 0; i < seriesList.size(); i++) {
            final String seriesId = seriesList.get(i).getObjectId();

            String bql = "select * from warehouse where series like '" + seriesId + "'";
            BmobQuery<Warehouse> query = new BmobQuery<>();
            query.setSQL(bql);
            query.doSQLQuery(bql, new SQLQueryListener<Warehouse>() {
                @Override
                public void done(BmobQueryResult<Warehouse> bmobQueryResult, BmobException e) {
                    if (e == null) {
                        warehouseList.add(bmobQueryResult.getResults());

                        adapter.refresh(seriesList, warehouseList);
                    } else {
                        Toast.makeText(GoodsActivity.this, "系列表查询失败", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
