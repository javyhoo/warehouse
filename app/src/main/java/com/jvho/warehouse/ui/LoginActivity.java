package com.jvho.warehouse.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.jvho.warehouse.R;
import com.jvho.warehouse.model._User;
import com.jvho.warehouse.ui.widget.LoginInputView;
import com.jvho.warehouse.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

import static android.view.inputmethod.EditorInfo.IME_ACTION_DONE;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.liv_phonenum)
    LoginInputView phonenum;
    @BindView(R.id.liv_pwd)
    LoginInputView password;
    @BindView(R.id.tv_login_btn)
    TextView btnLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        phonenum.setHint("请输入用户名");
        phonenum.setInputType(InputType.TYPE_CLASS_TEXT);
        password.setHint("请输入密码");
        password.setImgLabel(R.drawable.img_pwd);
        password.getEtValue().setImeOptions(IME_ACTION_DONE);

        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login_btn:
                availableAccount();
                break;
        }
    }

    private void availableAccount() {
        final String username = phonenum.getEdtTextValue();
        final String pwd = password.getEdtTextValue();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(pwd)) {
            showErrDialog("户名和密码不能为空");
        } else {
            BmobUser.loginByAccount(username, pwd, new LogInListener<_User>() {
                @Override
                public void done(_User user, BmobException e) {
                    if (null != user) {
                        gotoMainPage(user.getAdmin());
                    } else {
                        showErrDialog(e.getMessage());
                    }
                }
            });
        }
    }

    private void gotoMainPage(boolean isAdmin) {
        HomeActivity.gotoHomeActivity(this, isAdmin);

        this.finish();
    }

    private void showErrDialog(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new ToastUtil().showTipToast(LoginActivity.this, msg);
            }
        });

    }
}
