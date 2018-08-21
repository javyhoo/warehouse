package com.jvho.warehouse.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.github.jdsjlzx.interfaces.OnNetWorkErrorListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.jvho.core.base.BaseActivity;
import com.jvho.core.base.SweetAlertDialog;
import com.jvho.core.navigator.NavigationView;
import com.jvho.warehouse.R;
import com.jvho.warehouse.model.Goods;
import com.jvho.warehouse.ui.adapter.GoodsManageAdapter;
import com.jvho.warehouse.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by JV on 2018/8/15.
 */
public class GoodsManageActivity extends BaseActivity {

    private static final String TAG_ORG_ID = "tag_org_id";

    @BindView(R.id.list_manage)
    LRecyclerView listView;

    private View emptyView;
    private GoodsManageAdapter adapter;
    private String orgName;

    public static void gotoGoodsManageActivity(Context context, String orgName) {
        Intent intent = new Intent(context, GoodsManageActivity.class);
        intent.putExtra(TAG_ORG_ID, orgName);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_manage;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        emptyView = findViewById(R.id.empty_view);
        emptyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listView.refresh();
            }
        });

        orgName = getIntent().getStringExtra(TAG_ORG_ID);

        setNavigation();
        setListView();
    }

    private void setNavigation() {
        navigation.setTitle("货物管理");
        navigation.setRightButton(R.drawable.actionbar_add, new NavigationView.RightBtnClickListener() {
            @Override
            public void onClick(View view) {
                new SweetAlertDialog.Builder(GoodsManageActivity.this)
                        .setType(SweetAlertDialog.INPUT_TYPE)
                        .setTitle("新增货物")
                        .setCancelable(true)
                        .setPositiveButton("新增", new SweetAlertDialog.OnDialogClickListener() {
                            @Override
                            public void onClick(Dialog dialog, int which, @Nullable String inputMsg) {
                                if (!TextUtils.isEmpty(inputMsg)) {
                                    saveGoods(inputMsg);

                                    // 隐藏键盘
                                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                                }
                            }
                        })
                        .show();
            }
        });
    }

    private void saveGoods(final String name) {
        Goods goods = new Goods();
        goods.setName(name);
        goods.setOrganization(orgName);
        goods.setStatus(1);
        goods.save(new SaveListener<String>() {
            @Override
            public void done(String objectId, BmobException e) {
                if (e == null) {
                    Toast.makeText(GoodsManageActivity.this, "新增成功", Toast.LENGTH_SHORT).show();
                    queryData();
                } else if ((e.toString()).contains("has duplicate value")) {
                    new ToastUtil().showTipToast(GoodsManageActivity.this, name + "已存在，请检查！", null);
                } else {
                    saveGoods(name);
                }
            }
        });
    }

    private void queryData() {
        final BmobQuery<Goods> query = new BmobQuery<>();
        query.addWhereEqualTo("status", 1);
        query.addWhereEqualTo("organization", orgName);
        query.order("name");
        query.findObjects(new FindListener<Goods>() {
            @Override
            public void done(List<Goods> list, BmobException e) {
                listView.refreshComplete(100);

                if (null == e || list.size() > 0) {
                    adapter.setDataList(list);
                    emptyView.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                } else {
                    emptyView.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.GONE);
                }
            }
        });
    }

    private void setListView() {
        adapter = new GoodsManageAdapter(this);
        queryData();

        LRecyclerViewAdapter lAdapter = new LRecyclerViewAdapter(adapter);
        listView.setAdapter(lAdapter);

        DividerDecoration divider = new DividerDecoration.Builder(this)
                .setHeight(R.dimen.default_divider_height)
//                .setPadding(R.dimen.default_divider_padding)
                .setColorResource(R.color.list_line)
                .build();

        listView.addItemDecoration(divider);
        listView.setLayoutManager(new LinearLayoutManager(this));

        listView.setRefreshProgressStyle(ProgressStyle.LineSpinFadeLoader);
        listView.setArrowImageView(R.drawable.ic_pulltorefresh_arrow);
        listView.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);

        listView.setLoadMoreEnabled(false);
        listView.setPullRefreshEnabled(true);
        listView.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                queryData();
            }
        });

        listView.setOnNetWorkErrorListener(new OnNetWorkErrorListener() {
            @Override
            public void reload() {
                queryData();
            }
        });
    }

}
