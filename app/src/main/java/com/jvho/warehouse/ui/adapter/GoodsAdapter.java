package com.jvho.warehouse.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.jvho.warehouse.R;
import com.jvho.warehouse.model.Goods;
import com.jvho.warehouse.model.WarehouseGoods;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

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
        if (goodses == null) {
            Toast.makeText(context, "父数据为空", Toast.LENGTH_SHORT).show();
            return 0;
        }
        return this.goodses.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (warehouses.get(groupPosition) == null) {
            Toast.makeText(context, "第" + (groupPosition + 1) + "组数据为空", Toast.LENGTH_SHORT).show();
            return 0;
        }
        return this.warehouses.get(groupPosition).size();
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
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final SeriesViewHolder seriesViewHolder;

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
            Goods goods = goodses.get(groupPosition);
            seriesViewHolder.tvName.setText(goods.getName());

            BmobQuery<WarehouseGoods> query = new BmobQuery<>();
            query.addWhereEqualTo("goods", goods.getName());
            query.sum(new String[]{"amount"});
            query.findStatistics(WarehouseGoods.class, new QueryListener<JSONArray>() {
                @Override
                public void done(JSONArray jsonArray, BmobException e) {
                    if (e == null) {
                        if (jsonArray != null) {
                            try {
                                JSONObject obj = jsonArray.getJSONObject(0);
                                int sum = obj.getInt("_sumAmount");
                                seriesViewHolder.tvAmount.setText("数量：" + String.valueOf(sum));
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                                seriesViewHolder.tvAmount.setText("数量：0");
                            }
                        } else {
                            seriesViewHolder.tvAmount.setText("数量：0");
                        }
                    } else {
                        seriesViewHolder.tvAmount.setText("数量：0");
                    }
                }
            });
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
            seriesViewHolder.tvName.setText(warehouses.get(groupPosition).get(childPosition).getWarehouse());
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
