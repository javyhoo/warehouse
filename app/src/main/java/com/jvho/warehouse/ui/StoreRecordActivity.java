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
import android.widget.Spinner;
import android.widget.Toast;

import com.jvho.core.base.BaseActivity;
import com.jvho.core.base.SweetAlertDialog;
import com.jvho.core.navigator.NavigationView;
import com.jvho.warehouse.R;
import com.jvho.warehouse.model.Organization;
import com.jvho.warehouse.ui.widget.LabelView;
import com.jvho.warehouse.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by JV on 2018/8/16.
 */
public class StoreRecordActivity extends BaseActivity {

    private static final String TAG_TITLE = "tag_title";

    private String title;
    private List<String> listOrg = new ArrayList<>();
    private String org, goods;
    private int count;

    @BindView(R.id.store_record_org)
    Spinner spinnerOrg;
    @BindView(R.id.store_record_goods)
    Spinner spinnerGoods;
    @BindView(R.id.store_record_count)
    LabelView counts;

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
                new SweetAlertDialog.Builder(this)
                        .setTitle(title)
                        .setMessage(title + org + "的" + goods + "一共" + count + "件")
                        .setCancelable(true)
                        .setPositiveButton("确定", new SweetAlertDialog.OnDialogClickListener() {
                            @Override
                            public void onClick(Dialog dialog, int which, @Nullable String inputMsg) {

                            }
                        }).show();
            }
        }
    }

    private void initView() {
        counts.setLabel("数量：");
        counts.setHint("请输入数量");
        counts.setInputType(InputType.TYPE_CLASS_NUMBER);

        setOrgSpinner();
//        setGoodsSpinner();
    }

    private void setOrgSpinner() {
        BmobQuery<Organization> query = new BmobQuery<>();
        query.addWhereEqualTo("status", 1);
        query.order("-updatedAt");
        query.findObjects(new FindListener<Organization>() {
            @Override
            public void done(List<Organization> list, BmobException e) {
                if (e == null) {
                    listOrg.clear();

                    for (Organization item : list) {
                        listOrg.add(item.getName());
                    }

                    ArrayAdapter warehouseAdapter = new ArrayAdapter<String>(StoreRecordActivity.this,
                            android.R.layout.simple_spinner_item, listOrg);
                    warehouseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerOrg.setAdapter(warehouseAdapter);
                    spinnerOrg.setOnItemSelectedListener(new OrgSpinnerSelectedListener());
                    spinnerOrg.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(StoreRecordActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    class OrgSpinnerSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            org = listOrg.get(arg2);
        }

        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }

}
