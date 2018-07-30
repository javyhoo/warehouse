package com.jvho.warehouse.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jvho.core.base.BaseFragment;
import com.jvho.warehouse.R;
import com.jvho.warehouse.model.Organization;
import com.jvho.warehouse.ui.adapter.OrganizationAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobQueryResult;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SQLQueryListener;

public class GoodsFragment extends BaseFragment {

    @BindView(R.id.search_organization)
    SearchView orgSearch;
    @BindView(R.id.list_organization)
    RecyclerView orgListView;

    private Unbinder unbinder;
    private OrganizationAdapter adapter;
    private List<Organization> orgs = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if (rootview == null) {
            rootview = inflater.inflate(R.layout.fragment_goods, container, false);
            unbinder = ButterKnife.bind(this, rootview);

            orgSearch.setQueryHint("请输入您要搜索的机构名称");
            queryOrganization();
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
        adapter = new OrganizationAdapter(getContext(), orgs);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        orgListView.setLayoutManager(manager);
        //添加Android自带的分割线
        orgListView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));

        orgListView.setAdapter(adapter);
    }

    private void queryOrganization() {
        String bql = "select * from organization";
        BmobQuery<Organization> query = new BmobQuery<>();
        query.setSQL(bql);
        query.doSQLQuery(new SQLQueryListener<Organization>() {
            @Override
            public void done(BmobQueryResult<Organization> bmobQueryResult, BmobException e) {
                if (e == null) {
                    adapter.refresh(bmobQueryResult.getResults());
                } else {
                    Toast.makeText(getContext(), "机构表查询失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
