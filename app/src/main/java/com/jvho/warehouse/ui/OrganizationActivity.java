package com.jvho.warehouse.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnNetWorkErrorListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.jvho.core.base.BaseActivity;
import com.jvho.warehouse.R;
import com.jvho.warehouse.model.Organization;
import com.jvho.warehouse.ui.adapter.OrganizationAdapter;

import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by JV on 2018/8/16.
 */
public class OrganizationActivity extends BaseActivity {

    private static final String TAG_TITLE = "tag_title";

    @BindView(R.id.list_manage)
    LRecyclerView listView;
    //    @BindView(R.id.empty_view)
//    EmptyView emptyView;

    private String title;
    private OrganizationAdapter adapter;
    private LRecyclerViewAdapter lAdapter;

    public static void gotoOrganizationActivity(Context context, String title) {
        Intent intent = new Intent(context, OrganizationActivity.class);
        intent.putExtra(TAG_TITLE, title);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_manage;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        title = getIntent().getStringExtra(TAG_TITLE);

        setNavigation();
        setListView();

//        emptyView.setRefreshListener(new EmptyView.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                listView.refresh();
//            }
//        });
    }

    private void setNavigation() {
        navigation.setTitle(title);
        navigation.hideRightButton();
    }

    private void queryData() {
        final BmobQuery<Organization> query = new BmobQuery<>();
        query.addWhereEqualTo("status", 1);
        query.order("name");
        query.findObjects(new FindListener<Organization>() {
            @Override
            public void done(List<Organization> list, BmobException e) {
                listView.refreshComplete(100);

                if (null == e || list.size() > 0) {
                    adapter.setDataList(list);
//                    emptyView.setVisibility(View.GONE);
//                    listView.setVisibility(View.VISIBLE);
                } else {
//                    emptyView.setVisibility(View.VISIBLE);
//                    listView.setVisibility(View.GONE);
                }
            }
        });
    }

    private void setListView() {
        adapter = new OrganizationAdapter(this);
        queryData();

        lAdapter = new LRecyclerViewAdapter(adapter);
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

        lAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Organization organization = adapter.getDataList().get(position);
                if (null != organization) {
                    GoodsManageActivity.gotoGoodsManageActivity(OrganizationActivity.this, organization.getName());
                }
            }
        });
    }

}
