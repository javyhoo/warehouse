package com.jvho.warehouse.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jvho.warehouse.R;
import com.jvho.warehouse.model.Organization;
import com.jvho.warehouse.ui.GoodsActivity;

import java.util.List;

public class OrganizationAdapter extends RecyclerView.Adapter<OrganizationAdapter.OrganizationViewHolder> {

    private List<Organization> list;
    private Context context;

    public OrganizationAdapter(Context context, List<Organization> data) {
        this.context = context;
        this.list = data;
    }

    @NonNull
    @Override
    public OrganizationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_organization, parent, false);
        return new OrganizationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrganizationViewHolder holder, int position) {
        final Organization organization = list.get(position);

        holder.name.setText(organization.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoodsActivity.gotoGoodsActivity(context, organization.getObjectId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }

    public void refresh(List<Organization> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    class OrganizationViewHolder extends RecyclerView.ViewHolder {
        TextView name;

        public OrganizationViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tv_organization_name);
        }
    }
}
