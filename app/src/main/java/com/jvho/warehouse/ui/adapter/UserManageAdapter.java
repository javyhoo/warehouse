package com.jvho.warehouse.ui.adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jvho.core.base.SweetAlertDialog;
import com.jvho.warehouse.R;
import com.jvho.warehouse.base.ListBaseAdapter;
import com.jvho.warehouse.base.SuperViewHolder;
import com.jvho.warehouse.model._User;
import com.jvho.warehouse.utils.ToastUtil;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by JV on 2018/8/15.
 */
public class UserManageAdapter extends ListBaseAdapter<_User> {

    private Context context;

    public UserManageAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_manager;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, final int position) {
        final _User user = mDataList.get(position);

        String warehouse = "(" + user.getWarehouse() + ")";
        String admin = user.getAdmin() ? "(管理员)" : "(一般使用者)";

        TextView name = holder.getView(R.id.tv_manage_name);
        name.setText(user.getUsername() + admin + warehouse);

        TextView btnRemove = holder.getView(R.id.tv_manage_remove);
//        if (user.getAdmin()) {
//            btnRemove.setVisibility(View.VISIBLE);
//        } else {
//            btnRemove.setVisibility(View.GONE);
//        }
        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SweetAlertDialog.Builder(context)
                        .setType(SweetAlertDialog.NORMAL_TYPE)
                        .setTitle("确定删除" + user.getUsername() + "？")
                        .setPositiveButton("确定", new SweetAlertDialog.OnDialogClickListener() {
                            @Override
                            public void onClick(Dialog dialog, int which, @Nullable String inputMsg) {
//                                user.delete(new UpdateListener() {
//                                    @Override
//                                    public void done(BmobException e) {
//                                        if (null == e) {
//                                            Toast.makeText(context, "删除成功!", Toast.LENGTH_SHORT).show();
//                                            remove(position);
//                                        } else {
//                                            new ToastUtil().showTipToast(context, "删除失败，" + e.toString(), null);
//                                        }
//                                    }
//                                });

                                user.setStatus(0);
                                user.update(new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (null == e){
                                            Toast.makeText(context, "删除成功!", Toast.LENGTH_SHORT).show();
                                            remove(position);
                                        } else {
                                            new ToastUtil().showTipToast(context, "删除失败，" + e.toString(), null);
                                        }
                                    }
                                });
                            }
                        })
                        .show();
            }
        });
    }

    @Override
    public void onViewRecycled(@NonNull SuperViewHolder holder) {
        super.onViewRecycled(holder);
    }

}
