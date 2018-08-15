package com.jvho.warehouse.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jvho.warehouse.R;

/**
 * Created by JV on 2018/8/15.
 */
public class EmptyView extends LinearLayout {

    private TextView btnFresh;

    public EmptyView(Context context) {
        super(context);
        init(context);
    }

    public EmptyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public EmptyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        li.inflate(R.layout.layout_empty, this);

        btnFresh = findViewById(R.id.btn_empty);
        btnFresh.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != listener) {
                    listener.onRefresh();
                }
            }
        });
    }

    public interface OnRefreshListener {
        void onRefresh();
    }

    OnRefreshListener listener;

    public void setRefreshListener(OnRefreshListener listener) {
        this.listener = listener;
    }

}
