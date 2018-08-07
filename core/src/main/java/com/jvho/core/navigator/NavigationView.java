package com.jvho.core.navigator;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jvho.core.R;

public class NavigationView extends LinearLayout {

    private ImageView btnLeft;
    private FrameLayout btnRight;
    private TextView tvTitle;
    private ImageView rightImg;
    private TextView rightTxt;
    private View rootView;

    public NavigationView(Context context) {
        super(context);
        init(context);
    }

    public NavigationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NavigationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rootView = li.inflate(R.layout.view_navigator, this);

        btnLeft = findViewById(R.id.btn_nav_left);
        tvTitle = findViewById(R.id.tv_nav_center);
        btnRight = findViewById(R.id.btn_nav_right);
        rightImg = findViewById(R.id.btn_nav_right_img);
        rightTxt = findViewById(R.id.btn_nav_right_txt);

        btnLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getContext() instanceof Activity) {
                    ((Activity) v.getContext()).finish();
                }
            }
        });

        btnRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null){
                    listener.onClick(view);
                }
            }
        });

    }


    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    public void setRightButton(int resource, RightBtnClickListener listener) {
        this.listener = listener;
        rightTxt.setVisibility(View.GONE);
        rightImg.setVisibility(View.VISIBLE);
        rightImg.setImageResource(resource);
    }

    public void setRightButton(String text, RightBtnClickListener listener) {
        this.listener = listener;
        rightTxt.setVisibility(View.VISIBLE);
        rightImg.setVisibility(View.GONE);
        rightTxt.setText(text);
    }

    public void hideRightButton() {
        btnRight.setVisibility(View.INVISIBLE);
    }

    public void hideNavigator() {
        rootView.setVisibility(View.GONE);
    }

    public void showNavigator() {
        rootView.setVisibility(View.VISIBLE);
    }

    public interface RightBtnClickListener extends OnClickListener {
        void onClick(View view);
    }

    private RightBtnClickListener listener;

    public void setRightClickListener(RightBtnClickListener listener){
        this.listener = listener;
    }
}
