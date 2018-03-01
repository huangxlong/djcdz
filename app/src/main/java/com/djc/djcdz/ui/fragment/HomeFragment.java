package com.djc.djcdz.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.djc.djcdz.R;
import com.djc.djcdz.base.BaseFragment;
import com.djc.djcdz.entity.BaseRsp;
import com.djc.djcdz.entity.CommendReportReq;
import com.djc.djcdz.http.BaseSubscriber;
import com.djc.djcdz.http.RetrofitFactory;
import com.djc.djcdz.util.DensityUtil;
import com.djc.djcdz.util.JsonUtil;
import com.djc.djcdz.util.LogUtils;
import com.djc.djcdz.util.ScreenUtils;
import com.djc.djcdz.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/2/24.
 * 首页
 */

public class HomeFragment extends BaseFragment {

    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.pointLayout)
    LinearLayout mHomePoint;

    private int currentIndex;
    private final int NEXT_FOCUS = 2;// 更换图片的消息的标志
    private final int REFRESH_VIEW = 3;
    private final int TIME = 10000;// 每张图片停留的时间

    private List<View> pageViewsList = new ArrayList<View>();// 存放焦点图片
    //    private List<RspDto.Banner> mBanners = new ArrayList<>();
    private List<String> mBanners = new ArrayList<>();

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


    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
//        initBanner();
    }

    @OnClick({R.id.btn_test})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_test:
                CommendReportReq req = new CommendReportReq();
                String json = JsonUtil.parse2String(req);
                RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);
                RetrofitFactory.getHttpService()
                        .report(req)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new BaseSubscriber<BaseRsp>(mContext) {
                            @Override
                            public void onResponse(BaseRsp rsp) {
                                if (rsp.code == 1) {
                                    LogUtils.d("rsp", "success");
                                } else {
                                    LogUtils.d("rsp", rsp.msg);
                                    ToastUtil.show(mContext, rsp.msg);
                                }
                            }
                        });

                break;
        }
    }

    //初始化banner
    public void initBanner() {
        if (getContext() == null) {
            return;
        }
        ViewGroup.LayoutParams lp = mViewPager.getLayoutParams();
        lp.width = ScreenUtils.getScreenWidth(getContext());
        if (ScreenUtils.isTablet(getContext())) {
            lp.height = (lp.width - DensityUtil.dip2px(getContext(), 80)) / 2;
        } else {
            lp.height = lp.width * 116 / 375;
        }
        mViewPager.setLayoutParams(lp);
//        mViewPager.setBackgroundResource(R.drawable.banner_loading);
        getBanner();
    }

    private void getBanner() {
        //if(success)
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
//            final RspDto.Banner banner;
            if (mBanners.size() == 2) {
//                banner = mBanners.get(i % 2);
            } else {
//                banner = mBanners.get(i);
            }
            View fv = LayoutInflater.from(getActivity()).inflate(R.layout.page_focus_view, null);
            ImageView focusImage = fv.findViewById(R.id.home_img_focus);
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
                ImageView point = new ImageView(getActivity());
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
            View fv = LayoutInflater.from(getActivity()).inflate(R.layout.page_focus_view, null);
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
}
