package com.cat.zeus.page;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cat.zeus.utils.APT;
import com.cat.zeus.utils.LogUtil;

public class ZeusFragment extends Fragment {
    public static final String TAG = ZeusFragment.class.getSimpleName();

    protected Activity activity;

    public static <T extends Fragment> T newInstance(@NonNull Class<? extends Fragment> clazz, Bundle args) {
        T fragment = null;
        try {
            fragment = (T) clazz.newInstance();
            if (null != args) {
                fragment.setArguments(args);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fragment;
    }

    public static <T extends Fragment> T newInstance(@NonNull Class<? extends Fragment> clazz) {
        return newInstance(clazz, null);
    }

    protected View setLayoutView() {
        return null;
    }

    protected int setLayout() {
        return -1;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        LogUtil.i(TAG, "onAttach context: " + context);
        this.activity = (Activity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.i(TAG, "onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layout = APT.getLayoutByAnnotation(this.getClass());
        if (layout == 0) {
            layout = setLayout();
        }

        View v = setLayoutView();
        if (0 != layout) {
            v = inflater.inflate(layout, container, false);
        }
        LogUtil.i(TAG, "onCreateView");
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LogUtil.i(TAG, "onViewCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtil.i(TAG, "onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.i(TAG, "onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtil.i(TAG, "onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtil.i(TAG, "onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        LogUtil.i(TAG, "onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.i(TAG, "onDestroy");
    }
}
