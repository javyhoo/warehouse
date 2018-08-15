package com.jvho.warehouse.utils;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.Nullable;

import com.jvho.core.base.SweetAlertDialog;


public class ToastUtil {

    public void showTipToast(final Context context, String tips, final OnTipsListener listener) {
        new SweetAlertDialog.Builder(context)
                .setTitle("提示")
                .setMessage(tips)
                .setCancelable(false)
                .setPositiveButton("确定", new SweetAlertDialog.OnDialogClickListener() {
                    @Override
                    public void onClick(Dialog dialog, int which, @Nullable String inputMsg) {
                        if (null != listener) {
                            listener.onClick();
                        }
                    }
                })
                .show();
    }

    public interface OnTipsListener {
        void onClick();
    }

    private OnTipsListener listener;

    public void setOnTipsListener(OnTipsListener listener) {
        this.listener = listener;
    }

}
