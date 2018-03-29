package com.djc.djcdz.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;

import com.djc.djcdz.R;
import com.djc.djcdz.base.BaseActivity;
import com.djc.djcdz.entity.RspDto;
import com.djc.djcdz.ui.fragment.master.LogFragment;
import com.djc.djcdz.ui.fragment.master.MasterHomeFragment;
import com.djc.djcdz.ui.fragment.master.RecordFragment;
import com.djc.djcdz.ui.fragment.master.RouteFragment;
import com.djc.djcdz.ui.fragment.master.WordsFragment;
import com.djc.djcdz.util.ToastUtil;
import com.djc.djcdz.view.MyScrollView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MasterActivity extends BaseActivity {
    public Fragment mContent;
    public List<Fragment> mFragments = new ArrayList<>();
    public List<RspDto.Route> routeList = new ArrayList<>();
    public List<RspDto.Log> logList = new ArrayList<>();
    public List<RspDto.Record> recordList = new ArrayList<>();
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_account)
    TextView tvAccount;
    @BindView(R.id.scrollView)
    public MyScrollView scrollView;


    @Override
    protected int getLayout() {
        return R.layout.activity_master;
    }

    @Override
    protected void initView() {
        int id = getIntent().getIntExtra("id", 0);
        ToastUtil.show(this, id + "");
        initFragment();

        for (int i = 0; i < 128; i++) {
            RspDto.Route route = new RspDto.Route();
            route.time = "2-22 3:05";
            route.title = "路线图" + i;
            routeList.add(route);

            RspDto.Log log = new RspDto.Log();
            log.content = "002323雅百特低开后快速拉高，午后再度打板，但爆出天量，谨防股价强势调整，明日继续封板则继续持股，否则短线高抛。";
            log.time = "2-23 03:45";
            logList.add(log);

            RspDto.Record record = new RspDto.Record();
            recordList.add(record);
        }

    }

    private void initFragment() {
        mFragments.add(MasterHomeFragment.newInstance());
        mFragments.add(RouteFragment.newInstance());
        mFragments.add(LogFragment.newInstance());
        mFragments.add(RecordFragment.newInstance());
        mFragments.add(WordsFragment.newInstance());
        switchContent(mContent, mFragments.get(0));
    }


    @OnClick({R.id.iv_back, R.id.btn_words})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                back();
                break;
            case R.id.btn_words:
                if (!tvTitle.getText().equals("留言")) {
                    switchContent(mContent, mFragments.get(4));
                    addTitle("留言");
                }
                break;

        }
    }

    /**
     * 修改标题
     *
     * @param title
     */
    public void addTitle(String title) {
        tvTitle.setText(title);
    }

    /**
     * 切换Fragment
     *
     * @param from
     * @param to
     */
    public void switchContent(Fragment from, Fragment to) {
        if (mContent == null || mContent != to) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (mContent != null) {
                mContent.onPause();
            }
            if (to.isAdded()) {
                to.onResume();
            } else {
                transaction.add(R.id.container, to);
            }
            if (from != null) {
                transaction.hide(from);// 隐藏当前的fragment
            }
            transaction.show(to).commitAllowingStateLoss();// 显示下一个
            mContent = to;
        } else {
            mContent.onResume();
        }
        scrollView.smoothScrollTo(0, 0);
    }

    @Override
    public void onBackPressed() {
        back();
    }

    private void back() {
        if (tvTitle.getText().equals("名师堂")) {
            super.onBackPressed();
        } else {
            closeKeyboard();
            switchContent(mContent, mFragments.get(0));
            addTitle("名师堂");
        }
    }
}
