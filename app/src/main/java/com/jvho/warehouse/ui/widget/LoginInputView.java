package com.jvho.warehouse.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jvho.warehouse.R;


public class LoginInputView extends LinearLayout {

    private ImageView imgLabel;
    private EditText etValue;

    public LoginInputView(Context context) {
        super(context);
        init(context);
    }

    public LoginInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LoginInputView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        li.inflate(R.layout.view_psw, this);

        imgLabel = (ImageView) findViewById(R.id.iv_psd);
        etValue = (EditText) findViewById(R.id.edt_psd);
    }

    // resId = R.drawable.img_phone
    public void setImgLabel(int resId) {
        this.imgLabel.setImageResource(resId);
    }

    public EditText getEtValue() {
        return etValue;
    }

    public String getEdtTextValue() {
        return etValue.getText().toString().trim();
    }

    public void setEdtTextValue(String value) {
        this.etValue.setText(value);
    }

    public void setHint(String hint){
        this.etValue.setHint(hint);
    }

    public void setInputType(int inputType){
        this.etValue.setInputType(inputType);
    }
}
