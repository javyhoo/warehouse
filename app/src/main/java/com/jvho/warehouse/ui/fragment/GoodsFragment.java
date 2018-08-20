package com.jvho.warehouse.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.jdsjlzx.ItemDecoration.DividerDecoration;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnNetWorkErrorListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.jvho.core.base.BaseFragment;
import com.jvho.warehouse.R;
import com.jvho.warehouse.model.Organization;
import com.jvho.warehouse.ui.GoodsActivity;
import com.jvho.warehouse.ui.adapter.OrganizationAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class GoodsFragment extends BaseFragment {

    @BindView(R.id.search_organization)
    SearchView orgSearch;
    @BindView(R.id.list_organization)
    LRecyclerView listView;
//    @BindView(R.id.empty_view)
//    EmptyView emptyView;

    private Unbinder unbinder;
    private OrganizationAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if (rootview == null) {
            rootview = inflater.inflate(R.layout.fragment_goods, container, false);
            unbinder = ButterKnife.bind(this, rootview);

            orgSearch.setQueryHint("请输入您要搜索的机构名称");
            setListView();
        }

        return rootview;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        unbinder.unbind();
    }

    private void setListView() {
        adapter = new OrganizationAdapter(getContext());
        queryData();

        LRecyclerViewAdapter lAdapter = new LRecyclerViewAdapter(adapter);
        listView.setAdapter(lAdapter);

        DividerDecoration divider = new DividerDecoration.Builder(getContext())
                .setHeight(R.dimen.default_divider_height)
//                .setPadding(R.dimen.default_divider_padding)
                .setColorResource(R.color.list_line)
                .build();

        listView.addItemDecoration(divider);
        listView.setLayoutManager(new LinearLayoutManager(getContext()));

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
                    GoodsActivity.gotoGoodsActivity(getContext(), organization.getName(), "货物");
                }
            }
        });
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
                    Toast.makeText(getContext(), "机构表查询失败", Toast.LENGTH_SHORT).show();
//                    emptyView.setVisibility(View.VISIBLE);
//                    listView.setVisibility(View.GONE);
                }
            }
        });
    }

}
