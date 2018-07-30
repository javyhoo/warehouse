package com.jvho.core.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.jvho.core.R;
import com.jvho.core.navigator.NavigationView;

public abstract class BaseActivity extends AppCompatActivity {

    protected NavigationView navigation;
    private LinearLayout contentView;

    protected abstract int getLayoutResource();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_base);

        contentView = findViewById(R.id.ll_content);
        navigation = findViewById(R.id.nav_bar);

        if (getLayoutResource() != 0) {
            LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (null != inflater) {
                inflater.inflate(getLayoutResource(), contentView);
            }
        }
    }

}
