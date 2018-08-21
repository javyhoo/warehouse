package com.jvho.warehouse.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jvho.core.base.BaseFragment;
import com.jvho.warehouse.R;
import com.jvho.warehouse.model._User;
import com.jvho.warehouse.ui.FeedbackActivity;
import com.jvho.warehouse.ui.widget.LabelView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.bmob.v3.BmobUser;

public class MyFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.rl_info)
    RelativeLayout userInfo;
    @BindView(R.id.iv_user_icon)
    ImageView userIcon;
    @BindView(R.id.tv_user_name)
    TextView userName;
    @BindView(R.id.tv_user_admin)
    TextView userAdmin;
    @BindView(R.id.tv_user_warehouse)
    TextView userWarehouse;
    @BindView(R.id.lv_feedback)
    LabelView feedback;
    @BindView(R.id.lv_about)
    LabelView about;
    @BindView(R.id.tv_logout_btn)
    TextView logout;

    private Unbinder unbinder;
    private _User curUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if (rootview == null) {
            rootview = inflater.inflate(R.layout.fragment_my, container, false);
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
        feedback.editMode(isEdit);
        about.editMode(isEdit);
    }

    private void setValue() {
        feedback.setLabel("问题反馈");
        about.setLabel("关于仓库");

//        feedback.setValue("账户管理");
        about.setValue("V1.0.0");

        curUser = BmobUser.getCurrentUser(_User.class);
        setUser(curUser);
    }

    private void setUser(_User user) {
        userName.setText(user.getUsername());
        userAdmin.setText(user.getAdmin() ? "管理员" : "一般使用者");
        userWarehouse.setText(user.getWarehouse());
    }

    private void setListener() {
        feedback.setOnClickListener(this);
        about.setOnClickListener(this);
        userInfo.setOnClickListener(this);
        logout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_info:
                // change pwd
                break;
            case R.id.lv_feedback:
                FeedbackActivity.gotoFeedbackActivity(getContext());
                break;
            case R.id.lv_about:
                break;
            case R.id.tv_logout_btn:
                BmobUser.logOut();
                activity.finish();
                break;
        }
    }
}
