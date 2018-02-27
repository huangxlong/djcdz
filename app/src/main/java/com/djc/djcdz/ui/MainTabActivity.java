package com.djc.djcdz.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.djc.djcdz.R;
import com.djc.djcdz.app.App;
import com.djc.djcdz.base.BaseActivity;
import com.djc.djcdz.ui.fragment.CommentsFragment;
import com.djc.djcdz.ui.fragment.HomeFragment;
import com.djc.djcdz.ui.fragment.MasterFragment;
import com.djc.djcdz.ui.fragment.NewsFragment;
import com.djc.djcdz.ui.fragment.RankFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/2/24.
 */

public class MainTabActivity extends BaseActivity {
    @BindView(R.id.iv_home)
    ImageView ivHome;
    @BindView(R.id.tv_home)
    TextView tvHome;
    @BindView(R.id.iv_comments)
    ImageView ivComments;
    @BindView(R.id.tv_comments)
    TextView tvComments;
    @BindView(R.id.iv_rank)
    ImageView ivRank;
    @BindView(R.id.tv_rank)
    TextView tvRank;
    @BindView(R.id.iv_news)
    ImageView ivNews;
    @BindView(R.id.tv_news)
    TextView tvNews;
    @BindView(R.id.iv_master)
    ImageView ivMaster;
    @BindView(R.id.tv_master)
    TextView tvMaster;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private long exitTime = 0;
    private Fragment mContent;
    private List<Fragment> mFragments = new ArrayList<>();

    @Override
    protected int getLayout() {
        return R.layout.activity_main_tab;
    }

    @Override
    protected void initView() {
        mFragments.add(HomeFragment.newInstance());
        mFragments.add(CommentsFragment.newInstance());
        mFragments.add(RankFragment.newInstance());
        mFragments.add(NewsFragment.newInstance());
        mFragments.add(MasterFragment.newInstance());

        switchContent(null, mFragments.get(0));
    }

    public void setTitleName(String title) {
        tvTitle.setText(title);
    }

    @OnClick({R.id.layout_home, R.id.layout_comments, R.id.layout_rank, R.id.layout_news, R.id.layout_master})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.layout_home:
                setDefaultResources(TAB_HOME);
                switchContent(mContent, mFragments.get(0));
                break;
            case R.id.layout_comments:
                setDefaultResources(TAB_COMMENTS);
                switchContent(mContent, mFragments.get(1));
                break;
            case R.id.layout_rank:
                setDefaultResources(TAB_RANK);
                switchContent(mContent, mFragments.get(2));
                break;
            case R.id.layout_news:
                setDefaultResources(TAB_NEWS);
                switchContent(mContent, mFragments.get(3));
                break;
            case R.id.layout_master:
                setDefaultResources(TAB_MASTER);
                switchContent(mContent, mFragments.get(4));
                break;
        }
    }

    private static final int TAB_HOME = 1;
    private static final int TAB_COMMENTS = 2;
    private static final int TAB_RANK = 3;
    private static final int TAB_NEWS = 4;
    private static final int TAB_MASTER = 5;

    private void setDefaultResources(int selectIndex) {

        tvHome.setTextColor(getResources().getColor(R.color.main_tab_normal));
        tvComments.setTextColor(getResources().getColor(R.color.main_tab_normal));
        tvRank.setTextColor(getResources().getColor(R.color.main_tab_normal));
        tvNews.setTextColor(getResources().getColor(R.color.main_tab_normal));
        tvMaster.setTextColor(getResources().getColor(R.color.main_tab_normal));

        switch (selectIndex) {
            case TAB_HOME:
                tvHome.setTextColor(getResources().getColor(R.color.main_tab_act));
                break;
            case TAB_COMMENTS:
                tvComments.setTextColor(getResources().getColor(R.color.main_tab_act));
                break;
            case TAB_RANK:
                tvRank.setTextColor(getResources().getColor(R.color.main_tab_act));
                break;
            case TAB_NEWS:
                tvNews.setTextColor(getResources().getColor(R.color.main_tab_act));
                break;
            case TAB_MASTER:
                tvMaster.setTextColor(getResources().getColor(R.color.main_tab_act));
                break;
        }
    }

    /**
     * 切换Fragment
     *
     * @param from 当前fragment
     * @param to   切换fragment
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
        }
    }

    @Override
    public void onBackPressed() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            showToast(getString(R.string.toast_exit_app));
            exitTime = System.currentTimeMillis();
        } else {
            super.onBackPressed();
            App.exit();
        }
    }
}
