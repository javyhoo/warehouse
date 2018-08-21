package com.jvho.warehouse.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.github.jdsjlzx.interfaces.OnNetWorkErrorListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.jvho.core.base.BaseActivity;
import com.jvho.core.navigator.NavigationView;
import com.jvho.warehouse.R;
import com.jvho.warehouse.model._User;
import com.jvho.warehouse.ui.adapter.UserManageAdapter;

import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by JV on 2018/8/15.
 */
public class UserManageActivity extends BaseActivity {

    public static final String TAG_MANAGE_TITLE = "tag_manage_title";

    @BindView(R.id.list_manage)
    LRecyclerView listView;

    private View emptyView;
    private String title;
    private UserManageAdapter adapter;
    private LRecyclerViewAdapter lAdapter;

    public static void gotoUserManageActivity(Context context, String title) {
        Intent intent = new Intent(context, UserManageActivity.class);
        intent.putExtra(TAG_MANAGE_TITLE, title);
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

        title = getIntent().getStringExtra(TAG_MANAGE_TITLE);

        setNavigation();
        setListView();
    }

    @Override
    protected void onResume() {
        super.onResume();

        listView.refresh();
    }

    private void setNavigation() {
        navigation.setTitle(title);
        navigation.setRightButton(R.drawable.actionbar_add, new NavigationView.RightBtnClickListener() {
            @Override
            public void onClick(View view) {
                UserAddActivity.gotoUserAddActivity(UserManageActivity.this, "新增账户");
            }
        });
    }

    private void queryData() {
        final BmobQuery<_User> query = new BmobQuery<>();
        query.addWhereEqualTo("status", 1);
        query.order("name");
        query.findObjects(new FindListener<_User>() {
            @Override
            public void done(List<_User> list, BmobException e) {
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
        adapter = new UserManageAdapter(this);

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
    }

}
