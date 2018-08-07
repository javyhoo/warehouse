package com.jvho.warehouse.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.jvho.warehouse.R;
import com.jvho.warehouse.model.Series;
import com.jvho.warehouse.model.Warehouse;

import java.util.List;

public class GoodsAdapter extends BaseExpandableListAdapter {

    private List<Series> seriesList;
    private List<List<Warehouse>> warehouseList;
    private Context context;

    public GoodsAdapter(Context context, List<Series> seriesList, List<List<Warehouse>> warehouseList) {
        this.context = context;
        this.seriesList = seriesList;
        this.warehouseList = warehouseList;
    }

    @Override
    public int getGroupCount() {
        return this.seriesList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.warehouseList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.seriesList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.warehouseList.get(groupPosition).get(childPosition);
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

        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_goods, parent, false);
            seriesViewHolder = new SeriesViewHolder();
            seriesViewHolder.tvName = convertView.findViewById(R.id.tv_goods_name);
            seriesViewHolder.tvAmount = convertView.findViewById(R.id.tv_goods_amount);
            convertView.setTag(seriesViewHolder);
        } else {
            seriesViewHolder = (SeriesViewHolder) convertView.getTag();
        }

        seriesViewHolder.tvName.setText(seriesList.get(groupPosition).getName());
//        seriesViewHolder.tvAmount.setVisibility(View.GONE);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        SeriesViewHolder seriesViewHolder;

        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_goods, parent, false);
            seriesViewHolder = new SeriesViewHolder();
            seriesViewHolder.tvName = convertView.findViewById(R.id.tv_goods_name);
            seriesViewHolder.tvAmount = convertView.findViewById(R.id.tv_goods_amount);
            convertView.setBackgroundColor(context.getResources().getColor(R.color.tab_normal_color));
            convertView.setTag(seriesViewHolder);
        } else {
            seriesViewHolder = (SeriesViewHolder) convertView.getTag();
        }

        seriesViewHolder.tvName.setText(warehouseList.get(groupPosition).get(childPosition).getName());
//        seriesViewHolder.tvAmount.setText("数量：" + warehouseList.get(groupPosition).get(childPosition).getAmount().toString());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public void refresh(List<Series> seriesList, List<List<Warehouse>> warehouseList){
        this.seriesList = seriesList;
        this.warehouseList = warehouseList;
        notifyDataSetChanged();
    }

    class SeriesViewHolder{
        TextView tvName;
        TextView tvAmount;
    }
}
