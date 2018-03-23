package com.djc.djcdz.ui;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.djc.djcdz.R;
import com.djc.djcdz.app.App;
import com.djc.djcdz.base.BaseActivity;
import com.djc.djcdz.entity.RspDto;
import com.djc.djcdz.ui.fragment.CommentsFragment;
import com.djc.djcdz.ui.fragment.HomeFragment;
import com.djc.djcdz.ui.fragment.MasterFragment;
import com.djc.djcdz.ui.fragment.NewsFragment;
import com.djc.djcdz.ui.fragment.RankFragment;
import com.djc.djcdz.util.DensityUtil;
import com.djc.djcdz.util.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static java.security.AccessController.getContext;

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
    public Fragment mContent;
    public List<Fragment> mFragments = new ArrayList<>();
    public List<RspDto.News> newsList = new ArrayList<>();
    public List<RspDto.Comment> commentList = new ArrayList<>();


    /**
     * 异步信息处理器
     */
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case NEXT_FOCUS:
                    if (pageViewsList.size() > 1) {
                        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                    }
                    handler.removeMessages(NEXT_FOCUS);
                    handler.sendEmptyMessageDelayed(NEXT_FOCUS, TIME);
                    break;
                case REFRESH_VIEW:
                    refreshBannerList();
                    break;
            }
        }
    };
    private Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab);
        unbinder = ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mFragments.add(HomeFragment.newInstance());
        mFragments.add(CommentsFragment.newInstance());
        mFragments.add(RankFragment.newInstance());
        mFragments.add(NewsFragment.newInstance());
        mFragments.add(MasterFragment.newInstance());
        switchContent(null, mFragments.get(0));
        setDefaultResources(1);

        for (int i = 0; i < 50; i++) {
            RspDto.News news = new RspDto.News();
            news.title = "ttt" + i;
            newsList.add(news);
        }

        for (int i = 0; i < 50; i++) {
            RspDto.Comment comment = new RspDto.Comment();
            comment.commnets = "comment" + i;
            commentList.add(comment);
        }
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
                setTitleName("大决策");
                break;
            case R.id.layout_comments:
                setDefaultResources(TAB_COMMENTS);
                switchContent(mContent, mFragments.get(1));
                setTitleName("解盘");
                break;
            case R.id.layout_rank:
                setDefaultResources(TAB_RANK);
                switchContent(mContent, mFragments.get(2));
                setTitleName("榜单");
                break;
            case R.id.layout_news:
                setDefaultResources(TAB_NEWS);
                switchContent(mContent, mFragments.get(3));
                setTitleName("情报");
                break;
            case R.id.layout_master:
                setDefaultResources(TAB_MASTER);
                switchContent(mContent, mFragments.get(4));
                setTitleName("名师");
                break;
        }
    }

    private static final int TAB_HOME = 1;
    private static final int TAB_COMMENTS = 2;
    private static final int TAB_RANK = 3;
    private static final int TAB_NEWS = 4;
    private static final int TAB_MASTER = 5;

    public void setDefaultResources(int selectIndex) {

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

    ViewPager mViewPager;
    LinearLayout mHomePoint;
    private List<String> mBanners = new ArrayList<>();
    private int currentIndex;
    private final int NEXT_FOCUS = 2;// 更换图片的消息的标志
    private final int REFRESH_VIEW = 3;
    private final int TIME = 5000;// 每张图片停留的时间
    private List<View> pageViewsList = new ArrayList<View>();// 存放焦点图片

    public void initBanner(ViewPager viewPager, LinearLayout pointLayout) {

        mViewPager = viewPager;
        mHomePoint = pointLayout;
        ViewGroup.LayoutParams lp = mViewPager.getLayoutParams();
        lp.width = ScreenUtils.getScreenWidth(this);
        if (ScreenUtils.isTablet(this)) {
            lp.height = (lp.width - DensityUtil.dip2px(this, 80)) / 2;
        } else {
            lp.height = lp.width * 116 / 375;
        }
        mViewPager.setLayoutParams(lp);
//        mViewPager.setBackgroundResource(R.drawable.banner_loading);
        getBanner();
    }

    private void getBanner() {
        mBanners.add("http://img.zcool.cn/community/019c6857fda8c0a84a0e282b15918f.jpg@900w_1l_2o_100sh.jpg");
        mBanners.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1521606516232&di=4ce1037135b516a8911640ebddb23dbb&imgtype=0&src=http%3A%2F%2Fpic.qiantucdn.com%2F58pic%2F17%2F80%2F73%2F85858PICCrn_1024.jpg");

        mViewPager.setAdapter(pageAdapter);
        mViewPager.setOnPageChangeListener(mOnPageChangeListener);
        refreshBannerList();
        handler.sendEmptyMessageDelayed(NEXT_FOCUS, TIME);
    }

    private void refreshBannerList() {
        final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1); // 点
        params.setMargins(0, 0, 10, 10);
        mHomePoint.removeAllViews();
        pageViewsList.clear();
        int length = mBanners.size();
        if (length == 2) {
            length = 4;
        }
        for (int i = 0; i < length; i++) {
            String banner;
            if (mBanners.size() == 2) {
                banner = mBanners.get(i % 2);
            } else {
                banner = mBanners.get(i);
            }
            View fv = LayoutInflater.from(this).inflate(R.layout.page_focus_view, null);
            ImageView focusImage = fv.findViewById(R.id.home_img_focus);
            Glide.with(this).load(banner).into(focusImage);

//            if (TextUtils.isEmpty(banner.picUrl)) {
//                focusImage.setImageResource(banner.defaultImg);
//            } else {
//                focusImage.setImageURI(banner.picUrl);
//            }
//            if (banner.bannerType == 1) {
//                focusImage.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (banner.jumpUrl.matches("^((https|http|ftp|rtsp|mms)?:\\/\\/)[^\\s]+")) {
//                            Intent intent = new Intent(getActivity(), CommonWebActivity.class);
//                            intent.putExtra(CommonWebActivity.KEY_EXTRA, banner.jumpUrl);
////                            intent.putExtra(CommonWebActivity.KEY_EXTRA, "file:///android_asset/banner.html");
//                            intent.putExtra(CommonWebActivity.TITLE, banner.bannerName);
//                            startActivity(intent);
//                        }
//
//                    }
//                });
//            }
            pageViewsList.add(i, fv);
            if (length > 1 && i < mBanners.size()) {
                ImageView point = new ImageView(this);
                point.setBackgroundResource(R.drawable.selector_point);
                if (i == 0) {
                    point.setEnabled(true);
                } else {
                    point.setEnabled(false);
                }
                point.setLayoutParams(params);
                mHomePoint.addView(point);
            }
        }
        if (length == 0) {
            View fv = LayoutInflater.from(this).inflate(R.layout.page_focus_view, null);
            ImageView focusImage = (ImageView) fv.findViewById(R.id.home_img_focus);
//            focusImage.setImageResource(R.drawable.banner);
            pageViewsList.add(0, fv);
        }
        if (mBanners.size() == 2) {
            currentIndex = 5000 - 5000 % mBanners.size();
        } else {
            currentIndex = 5000 - 5000 % pageViewsList.size();
        }
        pageAdapter.notifyDataSetChanged();
    }


    ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int location) {
            if (mBanners.size() > 1 && mHomePoint.getChildCount() == mBanners.size()) {
                // 设置点
                mHomePoint.getChildAt(currentIndex % mBanners.size()).setEnabled(false);
                mHomePoint.getChildAt(location % mBanners.size()).setEnabled(true);
                currentIndex = location;
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
            if (arg0 == 1) {
                handler.removeMessages(NEXT_FOCUS);
            } else if (arg0 == 0) {
                handler.sendEmptyMessageDelayed(NEXT_FOCUS, TIME);
            }

        }
    };


    private PagerAdapter pageAdapter = new PagerAdapter() {

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public int getCount() {
            if (pageViewsList.size() > 1)
                return Integer.MAX_VALUE;
            else
                return pageViewsList.size();
        }

        @Override
        public void destroyItem(View container, int position, Object object) {
        }

        @Override
        public Object instantiateItem(View container, int position) {
            if (pageViewsList.get(position % pageViewsList.size()).getParent() != null) {
                ((ViewPager) pageViewsList.get(position % pageViewsList.size()).getParent()).removeView(pageViewsList.get(position % pageViewsList.size()));
            }
            ((ViewPager) container).addView(pageViewsList.get(position % pageViewsList.size()), 0);

            return pageViewsList.get(position % pageViewsList.size());
        }
    };


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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
