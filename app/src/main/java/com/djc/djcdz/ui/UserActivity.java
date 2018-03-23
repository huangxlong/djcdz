package com.djc.djcdz.ui;

import android.os.Bundle;

import com.djc.djcdz.R;
import com.djc.djcdz.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator
 * on 2018/2/28 星期三.
 * 个人中心
 */

public class UserActivity extends BaseActivity {

    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        unbinder = ButterKnife.bind(this);
        initView();
    }


    private void initView() {

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
