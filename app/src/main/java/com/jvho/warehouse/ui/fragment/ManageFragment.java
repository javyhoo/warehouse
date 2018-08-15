package com.jvho.warehouse.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jvho.core.base.BaseFragment;
import com.jvho.warehouse.R;
import com.jvho.warehouse.ui.OrgManageActivity;
import com.jvho.warehouse.ui.UserManageActivity;
import com.jvho.warehouse.ui.WarehouseManageActivity;
import com.jvho.warehouse.ui.widget.LabelView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ManageFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.label_account)
    LabelView manageAccount;
    @BindView(R.id.label_storage)
    LabelView manageStorage;
    @BindView(R.id.label_department)
    LabelView manageDepartment;
    @BindView(R.id.label_goods)
    LabelView manageGoods;

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if (rootview == null) {
            rootview = inflater.inflate(R.layout.fragment_manage, container, false);
            unbinder = ButterKnife.bind(this, rootview);

            labelViewIsEditMode(false);
            setValue();
            setListener();
        }

        return rootview;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        unbinder.unbind();
    }

    private void labelViewIsEditMode(boolean isEdit) {
        manageAccount.editMode(isEdit);
        manageStorage.editMode(isEdit);
        manageDepartment.editMode(isEdit);
        manageGoods.editMode(isEdit);
    }

    private void setValue() {
        manageAccount.setLabel(R.mipmap.ic_launcher_round);
        manageStorage.setLabel(R.mipmap.ic_launcher);
        manageDepartment.setLabel(R.mipmap.ic_launcher_round);
        manageGoods.setLabel(R.mipmap.ic_launcher);

        manageAccount.setValue("账户管理");
        manageStorage.setValue("仓库管理");
        manageDepartment.setValue("机构管理");
        manageGoods.setValue("货物管理");

    }

    private void setListener() {
        manageAccount.setOnClickListener(this);
        manageStorage.setOnClickListener(this);
        manageDepartment.setOnClickListener(this);
        manageGoods.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.label_account:
                UserManageActivity.gotoUserManageActivity(getContext(), "账户管理");
                break;
            case R.id.label_storage:
                WarehouseManageActivity.gotoWarehouseManageActivity(getContext(), "仓库管理");
                break;
            case R.id.label_department:
                OrgManageActivity.gotoOrgManageActivity(getContext(), "机构管理");
                break;
            case R.id.label_goods:
                break;
        }
    }
}
