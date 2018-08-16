package com.jvho.warehouse.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.jvho.warehouse.R;
import com.jvho.warehouse.base.ListBaseAdapter;
import com.jvho.warehouse.base.SuperViewHolder;
import com.jvho.warehouse.model.Organization;

/**
 * Created by JV on 2018/8/15.
 */
public class OrgAdapter extends ListBaseAdapter<Organization> {


    public OrgAdapter(Context context) {
        super(context);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_organization;
    }

    @Override
    public void onBindItemHolder(SuperViewHolder holder, final int position) {
        final Organization organization = mDataList.get(position);

        TextView name = holder.getView(R.id.tv_organization_name);
        name.setText(organization.getName());
    }

    @Override
    public void onViewRecycled(@NonNull SuperViewHolder holder) {
        super.onViewRecycled(holder);
    }

}
