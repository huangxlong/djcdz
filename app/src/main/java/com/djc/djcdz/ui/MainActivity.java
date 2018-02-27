package com.djc.djcdz.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.djc.djcdz.R;
import com.djc.djcdz.base.BaseActivity;

public class MainActivity extends BaseActivity {


    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        startActivity(new Intent(this,MainTabActivity.class));
    }
}
