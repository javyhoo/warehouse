package com.jvho.warehouse.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.jvho.warehouse.R;
import com.jvho.warehouse.base.ListBaseAdapter;
import com.jvho.warehouse.base.SuperViewHolder;
import com.jvho.warehouse.model.Goods;
import com.jvho.warehouse.model.Organization;
import com.jvho.warehouse.model.Record;
import com.jvho.warehouse.model.Warehouse;
import com.jvho.warehouse.model._User;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by JV on 2018/8/15.
 */
public class RecordAdapter extends ListBaseAdapter<Record> {

    private String userName = "", organizationName = "", goodsNmae = "", warehouseName = "", strAmount = "", time = "";

    public RecordAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_record;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, final int position) {
        clearValue();

        Record record = mDataList.get(position);

        final TextView tvRecord = holder.getView(R.id.tv_record);

        String goodsId = record.getGoods();
        Integer amount = record.getAmount();
        String userId = record.getUser();

        strAmount = amount < 0 ? "共 " + Math.abs(amount) + " 件取出自" : "共 " + amount + " 件存入";
        time = record.getCreatedAt();

        BmobQuery<_User> query = new BmobQuery<>();
        query.getObject(userId, new QueryListener<_User>() {
            @Override
            public void done(_User user, BmobException e) {
                if (e == null) {
                    userName = user.getUsername();

                    String warehouseId = user.getWarehouse();
                    BmobQuery<Warehouse> queryWarehoue = new BmobQuery<>();
                    queryWarehoue.getObject(warehouseId, new QueryListener<Warehouse>() {
                        @Override
                        public void done(Warehouse warehouse, BmobException e) {
                            if (e == null) {
                                warehouseName = warehouse.getName();

                                setTextView(tvRecord);
                            }
                        }
                    });
                }
            }
        });

        BmobQuery<Goods> query1 = new BmobQuery<>();
        query1.getObject(goodsId, new QueryListener<Goods>() {
            @Override
            public void done(Goods goods, BmobException e) {
                if (e == null) {
                    goodsNmae = goods.getName();

                    String organizationId = goods.getOrganization();
                    BmobQuery<Organization> queryOrg = new BmobQuery<>();
                    queryOrg.getObject(organizationId, new QueryListener<Organization>() {
                        @Override
                        public void done(Organization organization, BmobException e) {
                            if (e == null) {
                                organizationName = organization.getName();

                                setTextView(tvRecord);
                            }
                        }
                    });
                }
            }
        });

    }

    @Override
    public void onViewRecycled(@NonNull SuperViewHolder holder) {
        super.onViewRecycled(holder);
    }

    private void clearValue() {
        userName = "";
        organizationName = "";
        goodsNmae = "";
        warehouseName = "";
        strAmount = "";
        time = "";
    }

    private void setTextView(TextView tv) {
        tv.setText(time + userName + "把" + organizationName + "的" + goodsNmae + strAmount + warehouseName);
    }

}
