package com.jvho.warehouse.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.jvho.warehouse.R;
import com.jvho.warehouse.model.Goods;
import com.jvho.warehouse.model.WarehouseGoods;

import java.util.List;

public class GoodsAdapter extends BaseExpandableListAdapter {

    private List<Goods> goodses;
    private List<List<WarehouseGoods>> warehouses;
    private Context context;

    public GoodsAdapter(Context context, List<Goods> goodses, List<List<WarehouseGoods>> warehouses) {
        this.context = context;
        this.goodses = goodses;
        this.warehouses = warehouses;
    }

    @Override
    public int getGroupCount() {
        return this.goodses.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.warehouses.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.goodses.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.warehouses.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        SeriesViewHolder seriesViewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_goods, parent, false);
            seriesViewHolder = new SeriesViewHolder();
            seriesViewHolder.tvName = convertView.findViewById(R.id.tv_goods_name);
            seriesViewHolder.tvAmount = convertView.findViewById(R.id.tv_goods_amount);
            convertView.setTag(seriesViewHolder);
        } else {
            seriesViewHolder = (SeriesViewHolder) convertView.getTag();
        }

        if (goodses.size() > 0) {
            seriesViewHolder.tvName.setText(goodses.get(groupPosition).getName());
//        seriesViewHolder.tvAmount.setVisibility(View.GONE);
        }

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        SeriesViewHolder seriesViewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_goods, parent, false);
            seriesViewHolder = new SeriesViewHolder();
            seriesViewHolder.tvName = convertView.findViewById(R.id.tv_goods_name);
            seriesViewHolder.tvAmount = convertView.findViewById(R.id.tv_goods_amount);
            convertView.setBackgroundColor(context.getResources().getColor(R.color.list_line));
            convertView.setTag(seriesViewHolder);
        } else {
            seriesViewHolder = (SeriesViewHolder) convertView.getTag();
        }

        if (warehouses.size() > 0 && (warehouses.get(groupPosition)).size() > 0) {
            seriesViewHolder.tvName.setText(warehouses.get(groupPosition).get(childPosition).getGoods());
            seriesViewHolder.tvAmount.setText("数量：" + warehouses.get(groupPosition).get(childPosition).getAmount().toString());
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public void refresh(List<Goods> goodses, List<List<WarehouseGoods>> warehouses) {
        this.goodses = goodses;
        this.warehouses = warehouses;
        notifyDataSetChanged();
    }

    class SeriesViewHolder {
        TextView tvName;
        TextView tvAmount;
    }
}
