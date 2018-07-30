package com.jvho.warehouse.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jvho.warehouse.R;

public class LabelView extends LinearLayout {

    private TextView tvLabel;
    private EditText etValue;
    private ImageView ivLabel;
    private TextView tvValue;

    public LabelView(Context context) {
        super(context);
        init(context);
    }

    public LabelView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LabelView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        li.inflate(R.layout.view_label, this);

        tvLabel = findViewById(R.id.tv_label);
        ivLabel = findViewById(R.id.iv_label);
        etValue = findViewById(R.id.et_value);
        tvValue = findViewById(R.id.tv_value);
    }

    public TextView getTvLabel() {
        return tvLabel;
    }

    public EditText getEtValue() {
        return etValue;
    }

    public void setHint(String hint) {
        this.etValue.setHint(hint);
    }

    public void setInputType(int type) {
        this.etValue.setInputType(type);
    }

    public String getEdtTextValue() {
        return etValue.getText().toString().trim();
    }

    public void setLabel(String label) {
        this.tvLabel.setText(label);
        tvLabel.setVisibility(View.VISIBLE);
        ivLabel.setVisibility(View.GONE);
    }

    public void setLabel(int resId) {
        this.ivLabel.setImageResource(resId);
        tvLabel.setVisibility(View.GONE);
        ivLabel.setVisibility(View.VISIBLE);
    }

    public void setValue(String value) {
        etValue.setText(value);
        tvValue.setText(value);
    }

    public void editMode(boolean isEdit) {
        if (isEdit) {
            etValue.setVisibility(View.VISIBLE);
            tvValue.setVisibility(View.GONE);
        } else {
            etValue.setVisibility(View.GONE);
            tvValue.setVisibility(View.VISIBLE);
        }
    }

}
