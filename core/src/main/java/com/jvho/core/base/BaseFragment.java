package com.jvho.core.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BaseFragment extends Fragment {

    protected BaseActivity activity;
    private boolean init = false;
    protected View rootview;

    public BaseFragment() {
        super();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (activity == null) {
            activity = (BaseActivity) getActivity();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.init = true;
        rootview = super.onCreateView(inflater, container, savedInstanceState);

        return rootview;

    }

    public boolean isInitView() {
        return init;
    }

    public void setActivity(BaseActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.init = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.init = false;

        if (rootview != null) {
            if (rootview.getParent() != null)
                ((ViewGroup) (rootview.getParent())).removeView(rootview);
        }
    }
}
