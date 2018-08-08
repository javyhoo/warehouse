package com.jvho.warehouse.ui.adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jvho.core.base.SweetAlertDialog;
import com.jvho.warehouse.R;
import com.jvho.warehouse.model.Warehouse;
import com.jvho.warehouse.utils.ToastUtil;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by JV on 2018/8/7.
 */
public class WarehouseAdapter extends RecyclerView.Adapter<WarehouseAdapter.WarehouseViewHolder> {

    private List<Warehouse> list;
    private Context context;

    public WarehouseAdapter(Context context, List<Warehouse> data) {
        this.context = context;
        this.list = data;
    }

    public void queryData() {
        BmobQuery<Warehouse> query = new BmobQuery<>();
        query.addWhereEqualTo("status", 1);
        query.findObjects(new FindListener<Warehouse>() {
            @Override
            public void done(List<Warehouse> list, BmobException e) {
                if (null == e) {
                    refresh(list);
                } else {
                    new ToastUtil().showTipToast(context, "查询失败" + e.toString());
                }
            }
        });
    }

    private void refresh(List<Warehouse> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WarehouseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_manager, parent, false);
        return new WarehouseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WarehouseViewHolder holder, int position) {
        final Warehouse warehouse = list.get(position);

        holder.name.setText(warehouse.getName());

        holder.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SweetAlertDialog.Builder(context)
                        .setType(SweetAlertDialog.NORMAL_TYPE)
                        .setTitle("确定删除" + warehouse.getName() + "？")
                        .setPositiveButton("确定", new SweetAlertDialog.OnDialogClickListener() {
                            @Override
                            public void onClick(Dialog dialog, int which, @Nullable String inputMsg) {
                                warehouse.delete(new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if (null == e) {
                                            Toast.makeText(context, "删除成功!", Toast.LENGTH_SHORT).show();
                                            queryData();
                                        } else {
                                            new ToastUtil().showTipToast(context, "删除失败，" + e.toString());
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
    public int getItemCount() {
        return this.list.size();
    }

    class WarehouseViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView btnRemove;

        public WarehouseViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tv_manage_name);
            btnRemove = itemView.findViewById(R.id.tv_manage_remove);
        }
    }
}
