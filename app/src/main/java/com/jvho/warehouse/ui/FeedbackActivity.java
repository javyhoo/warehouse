package com.jvho.warehouse.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.jvho.core.base.BaseActivity;
import com.jvho.core.navigator.NavigationView;
import com.jvho.warehouse.R;
import com.jvho.warehouse.model.Feedback;
import com.jvho.warehouse.model._User;
import com.jvho.warehouse.utils.ToastUtil;

import butterknife.BindView;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by JV on 2018/8/21.
 */
public class FeedbackActivity extends BaseActivity {

    @BindView(R.id.et_title)
    EditText etTitle;
    @BindView(R.id.et_issue)
    EditText etIssue;

    public static void gotoFeedbackActivity(Context context) {
        Intent intent = new Intent(context, FeedbackActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setNavigation();
    }

    private void setNavigation() {
        navigation.setTitle("问题反馈");
        navigation.setRightButton("确定", new NavigationView.RightBtnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });
    }

    private void submit() {
        String issueTitle = etTitle.getText().toString().trim();
        String issueDes = etIssue.getText().toString().trim();

        Feedback feedback = new Feedback();
        feedback.setTitle(issueTitle);
        feedback.setIssue(issueDes);
        feedback.setStatus(1);
        feedback.setUser((BmobUser.getCurrentUser(_User.class)).getUsername());
        feedback.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    new ToastUtil().showTipToast(FeedbackActivity.this, "提交成功", new ToastUtil.OnTipsListener() {
                        @Override
                        public void onClick() {
                            finish();
                        }
                    });
                } else {
                    new ToastUtil().showTipToast(FeedbackActivity.this, "提交失败", null);
                }
            }
        });
    }

}
