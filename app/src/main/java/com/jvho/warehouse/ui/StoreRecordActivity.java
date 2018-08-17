package com.jvho.warehouse.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.jvho.core.base.BaseActivity;
import com.jvho.core.base.SweetAlertDialog;
import com.jvho.core.navigator.NavigationView;
import com.jvho.warehouse.R;
import com.jvho.warehouse.model.Goods;
import com.jvho.warehouse.model.Organization;
import com.jvho.warehouse.model.Record;
import com.jvho.warehouse.model.WarehouseGoods;
import com.jvho.warehouse.model._User;
import com.jvho.warehouse.ui.widget.LabelView;
import com.jvho.warehouse.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by JV on 2018/8/16.
 */
public class StoreRecordActivity extends BaseActivity {

    private static final String TAG_TITLE = "tag_title";

    private String title;
    private List<String> listOrg = new ArrayList<>();
    private List<String> listGoods = new ArrayList<>();
    private List<Goods> goodses = new ArrayList<>();
    private List<Organization> organizations = new ArrayList<>();
    private String org, goods, selectedGoodsId;
    private int count;

    @BindView(R.id.store_record_org)
    Spinner spinnerOrg;
    @BindView(R.id.store_record_goods)
    Spinner spinnerGoods;
    @BindView(R.id.store_record_count)
    LabelView counts;
    @BindView(R.id.progress_loading)
    ProgressBar pbLoading;

    public static void gotoStoreRecordActivity(Context context, String title) {
        Intent i = new Intent(context, StoreRecordActivity.class);
        i.putExtra(TAG_TITLE, title);
        context.startActivity(i);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_store_record;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        title = getIntent().getStringExtra(TAG_TITLE);

        setNavigation();
        initView();
    }

    private void setNavigation() {
        navigation.setTitle(title);
        navigation.setRightButton("确定", new NavigationView.RightBtnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });
    }

    private void submit() {
        String strCount = counts.getEdtTextValue();
        if (TextUtils.isEmpty(strCount)) {
            new ToastUtil().showTipToast(this, "数量不能为空", null);
        } else {
            count = Integer.parseInt(strCount);
            if (count < 1) {
                new ToastUtil().showTipToast(this, "数量要大于0", null);
            } else {
                _User userInfo = BmobUser.getCurrentUser(_User.class);
                final String userId = userInfo.getObjectId();
                final String curWarehouseId = userInfo.getWarehouse();

                BmobQuery<WarehouseGoods> query = new BmobQuery<>();
                query.addWhereEqualTo("warehouse", curWarehouseId);
                query.addWhereEqualTo("goods", selectedGoodsId);
                query.addWhereEqualTo("status", 1);
                query.findObjects(new FindListener<WarehouseGoods>() {
                    @Override
                    public void done(List<WarehouseGoods> list, BmobException e) {
                        if (e == null) {
                            final WarehouseGoods item = list.get(0);
                            Integer i = item.getAmount();

                            if ("出货".equals(title)) {
                                if (i < count) {
                                    new ToastUtil().showTipToast(StoreRecordActivity.this, "货存只有" + count + "件，请确认！", null);
                                    return;
                                } else {
                                    i = i - count;
                                }
                            } else {
                                i = i + count;
                            }

                            item.setAmount(i);

                            new SweetAlertDialog.Builder(StoreRecordActivity.this)
                                    .setTitle(title)
                                    .setMessage(title + org + "的" + goods + "一共" + count + "件")
                                    .setCancelable(true)
                                    .setPositiveButton("确定", new SweetAlertDialog.OnDialogClickListener() {
                                        @Override
                                        public void onClick(Dialog dialog, int which, @Nullable String inputMsg) {
                                            item.update(new UpdateListener() {
                                                @Override
                                                public void done(BmobException e) {
                                                    if (e == null) {
                                                        saveWarehouseGoods(userId);
                                                    } else {
                                                        new ToastUtil().showTipToast(StoreRecordActivity.this, "网络错误！" + e.toString(), null);
                                                    }
                                                }
                                            });
                                        }
                                    }).show();
                        } else {
                            new SweetAlertDialog.Builder(StoreRecordActivity.this)
                                    .setTitle(title)
                                    .setMessage(title + org + "的" + goods + "一共" + count + "件")
                                    .setCancelable(true)
                                    .setPositiveButton("确定", new SweetAlertDialog.OnDialogClickListener() {
                                        @Override
                                        public void onClick(Dialog dialog, int which, @Nullable String inputMsg) {
                                            WarehouseGoods warehouseGoods = new WarehouseGoods();
                                            warehouseGoods.setStatus(1);
                                            warehouseGoods.setGoods(selectedGoodsId);
                                            warehouseGoods.setWarehouse(curWarehouseId);
                                            warehouseGoods.setAmount(count);
                                            warehouseGoods.save(new SaveListener<String>() {
                                                @Override
                                                public void done(String s, BmobException e) {
                                                    if (null == e) {
                                                        saveWarehouseGoods(userId);
                                                    } else {
                                                        new ToastUtil().showTipToast(StoreRecordActivity.this, "网络错误！" + e.toString(), null);
                                                    }
                                                }
                                            });
                                        }
                                    }).show();
                        }
                    }
                });


            }
        }
    }

    private void saveWarehouseGoods(String userId) {
        Integer amount = "出货".equals(title) ? -count : count;

        Record record = new Record();
        record.setUser(userId);
        record.setGoods(selectedGoodsId);
        record.setAmount(amount);
        record.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    new ToastUtil().showTipToast(StoreRecordActivity.this, title + "成功", new ToastUtil.OnTipsListener() {
                        @Override
                        public void onClick() {
                            StoreRecordActivity.this.finish();
                        }
                    });
                } else {
                    new ToastUtil().showTipToast(StoreRecordActivity.this, e.toString(), null);
                }
            }
        });
    }

    private void initView() {
        counts.setLabel("数量：");
        counts.setHint("请输入数量");
        counts.setInputType(InputType.TYPE_CLASS_NUMBER);

        pbLoading.setVisibility(View.VISIBLE);
        pbLoading.setMax(100);
        pbLoading.setProgress(0);
        setOrgSpinner();
    }

    private void setOrgSpinner() {
        BmobQuery<Organization> query = new BmobQuery<>();
        query.addWhereEqualTo("status", 1);
        query.order("name");
        query.findObjects(new FindListener<Organization>() {
            @Override
            public void done(List<Organization> list, BmobException e) {
                if (e == null) {
                    listOrg.clear();
                    organizations.clear();

                    organizations.addAll(list);
                    for (Organization item : list) {
                        listOrg.add(item.getName());
                    }

                    ArrayAdapter orgAdapter = new ArrayAdapter<String>(StoreRecordActivity.this,
                            android.R.layout.simple_spinner_item, listOrg);
                    orgAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerOrg.setAdapter(orgAdapter);
                    spinnerOrg.setOnItemSelectedListener(new OrgSpinnerSelectedListener());
                    spinnerOrg.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(StoreRecordActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setGoodsSpinner(String orgId) {
        BmobQuery<Goods> query = new BmobQuery<>();
        query.addWhereEqualTo("status", 1);
        query.addWhereEqualTo("organization", orgId);
        query.order("name");
        query.findObjects(new FindListener<Goods>() {
            @Override
            public void done(List<Goods> list, BmobException e) {
                pbLoading.setProgress(100);
                pbLoading.setVisibility(View.GONE);

                if (e == null) {
                    listGoods.clear();
                    goodses.clear();

                    goodses.addAll(list);
                    for (Goods item : list) {
                        listGoods.add(item.getName());
                    }

                    ArrayAdapter goodsAdapter = new ArrayAdapter<String>(StoreRecordActivity.this,
                            android.R.layout.simple_spinner_item, listGoods);
                    goodsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerGoods.setAdapter(goodsAdapter);
                    spinnerGoods.setOnItemSelectedListener(new GoodsSpinnerSelectedListener());
                    spinnerGoods.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(StoreRecordActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    class OrgSpinnerSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            org = listOrg.get(arg2);
            Organization organization = organizations.get(arg2);
            setGoodsSpinner(organization.getObjectId());
        }

        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }

    class GoodsSpinnerSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            goods = listGoods.get(arg2);
            selectedGoodsId = goodses.get(arg2).getObjectId();
        }

        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }

}
