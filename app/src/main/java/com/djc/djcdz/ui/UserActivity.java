package com.djc.djcdz.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.djc.djcdz.R;
import com.djc.djcdz.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator
 * on 2018/2/28 星期三.
 * 个人中心
 */

public class UserActivity extends BaseActivity {

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_account)
    TextView tvAccount;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_wx)
    TextView tvWx;
    @BindView(R.id.tv_open_time)
    TextView tvOpenTime;
    @BindView(R.id.tv_close_time)
    TextView tvCloseTime;
    @BindView(R.id.tv_remain_time)
    TextView tvRemainTime;
    @BindView(R.id.tv_service_phone)
    TextView tvServicePhone;
    @BindView(R.id.tv_service_qq)
    TextView tvServiceQq;
    @BindView(R.id.tv_service_wx)
    TextView tvServiceWx;
    @BindView(R.id.tv_staring)
    TextView tvStaring;
    @BindView(R.id.iv_code)
    ImageView ivCode;


    @Override
    protected int getLayout() {
        return R.layout.activity_user;
    }


    @Override
    protected void initView() {
        ivCode.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showToast("..");
                //保存二维码



                return false;
            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick({R.id.iv_back, R.id.rl_recharge, R.id.rl_statement})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.rl_recharge:
                showToast("充值");
                break;
            case R.id.rl_statement:
                showToast("免责声明");
                break;
        }
    }
}
