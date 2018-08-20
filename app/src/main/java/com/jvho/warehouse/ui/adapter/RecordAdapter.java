package com.jvho.warehouse.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.jvho.warehouse.R;
import com.jvho.warehouse.base.ListBaseAdapter;
import com.jvho.warehouse.base.SuperViewHolder;
import com.jvho.warehouse.model.Record;

/**
 * Created by JV on 2018/8/15.
 */
public class RecordAdapter extends ListBaseAdapter<Record> {


    public RecordAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_record;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, final int position) {

        Record record = mDataList.get(position);
        String warehouse = record.getWarehouse();
        String userName = record.getUser();
        String orgName = record.getOrganization();
        String goodsName = record.getGoods();
        Integer amount = record.getAmount();
        String strAmount = amount < 0 ? "共 " + Math.abs(amount) + " 件取出自" : "共 " + amount + " 件存入";
        String time = record.getCreatedAt();

        TextView tvRecord = holder.getView(R.id.tv_record);
        tvRecord.setText(time + "\n" + userName + "把" + orgName + "的" + goodsName + strAmount + warehouse);

    }

    @Override
    public void onViewRecycled(@NonNull SuperViewHolder holder) {
        super.onViewRecycled(holder);
    }

}
