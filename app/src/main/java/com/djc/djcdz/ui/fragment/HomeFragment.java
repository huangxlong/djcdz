package com.djc.djcdz.ui.fragment;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.djc.djcdz.R;
import com.djc.djcdz.base.BaseFragment;
import com.djc.djcdz.entity.RspDto;
import com.djc.djcdz.ui.HtmlActivity;
import com.djc.djcdz.ui.MainTabActivity;
import com.djc.djcdz.ui.MasterActivity;
import com.djc.djcdz.ui.WebActivity;
import com.djc.djcdz.ui.adapter.CommentsAdapter;
import com.djc.djcdz.ui.adapter.NewsAdapter;
import com.djc.djcdz.ui.adapter.RankAdapter;
import com.djc.djcdz.util.ToastUtil;
import com.djc.djcdz.view.MyItemDecoration;
import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/2/24.
 * 首页
 */

public class HomeFragment extends BaseFragment {

    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.pointLayout)
    LinearLayout mHomePoint;
    @BindView(R.id.flexboxLayout)
    FlexboxLayout flexboxLayout;
    @BindView(R.id.tv_rolling)
    TextView tvRolling;
    @BindView(R.id.recycler_news)
    RecyclerView recyclerNews;
    @BindView(R.id.recycler_comment)
    RecyclerView recyclerComment;
    @BindView(R.id.recycler_rank)
    RecyclerView recyclerRank;
    private List<RspDto.Master> masters = new ArrayList<>();
    private List<RspDto.News> newsList = new ArrayList<>();
    private List<RspDto.Comment> commentList = new ArrayList<>();
    private List<RspDto.Rank> rankList = new ArrayList<>();
    private MainTabActivity activity;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        activity = (MainTabActivity) getActivity();
        activity.initBanner(mViewPager, mHomePoint);
        tvRolling.setSelected(true);  //设置文字滚动

        initMaster();

        initNews();

        initComment();

        initRank();

    }

    /**
     * 初始化榜单
     */
    private void initRank() {
        for (int i = 0; i < 5; i++) {
            rankList.add(activity.rankList.get(i));
        }

        RankAdapter rankAdapter = new RankAdapter(rankList);
        LinearLayoutManager rankManager = new LinearLayoutManager(mContext);
        rankManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerRank.setLayoutManager(rankManager);
        recyclerRank.setAdapter(rankAdapter);

        rankAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.tv_check:
                        ToastUtil.show(mContext, "查看" + position);
                        break;
                    case R.id.tv_star_ta:
                        ToastUtil.show(mContext, "追踪" + position);
                        break;
                }

            }
        });

    }

    /**
     * 初始化解盘
     */
    private void initComment() {
        for (int i = 0; i < 5; i++) {
            RspDto.Comment comment = new RspDto.Comment();
            commentList.add(comment);
        }

        CommentsAdapter commentsAdapter = new CommentsAdapter(commentList);
        LinearLayoutManager commentManager = new LinearLayoutManager(mContext);
        commentManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerComment.addItemDecoration(new MyItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        recyclerComment.setLayoutManager(commentManager);
        recyclerComment.setAdapter(commentsAdapter);
    }

    /**
     * 初始化情报
     */
    private void initNews() {
        for (int i = 0; i < 3; i++) {
            newsList.add(activity.newsList.get(i));
        }
        NewsAdapter newsAdapter = new NewsAdapter(newsList);
        LinearLayoutManager newsManager = new LinearLayoutManager(mContext);
        newsManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerNews.addItemDecoration(new MyItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        recyclerNews.setLayoutManager(newsManager);
        recyclerNews.setAdapter(newsAdapter);

        newsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mContext, WebActivity.class);
                intent.putExtra("title", "position:" + position);
                startActivity(intent);
            }
        });
    }

    /**
     * 初始化名师
     */
    private void initMaster() {
        for (int i = 0; i < 3; i++) {
            RspDto.Master master = new RspDto.Master();
            if (i == 0) {
                master.isFollow = true;
            }
            master.name = "山妖" + i;
            master.id = i;
            masters.add(master);
        }


        for (int i = 0; i < masters.size(); i++) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_master, null);
            TextView tvName = view.findViewById(R.id.tv_name);
            Button btnFellow = view.findViewById(R.id.btn_fellow);
            tvName.setText(masters.get(i).name);
            if (masters.get(i).isFollow) {
                btnFellow.setText("已追踪");
                btnFellow.setBackgroundResource(R.drawable.shape_fellow_star);
                btnFellow.setTextColor(getResources().getColor(R.color.bg_white));
            }
            flexboxLayout.addView(view);

        }

        for (int i = 0; i < flexboxLayout.getChildCount(); i++) {
            final int finalI = i;
            flexboxLayout.getChildAt(i).findViewById(R.id.btn_fellow).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showLoadingDialog("");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            hideLoadingDialog();
                            Button btn = flexboxLayout.getChildAt(finalI).findViewById(R.id.btn_fellow);
                            if (btn.getText().equals("已追踪")) {
                                btn.setText("追踪他");
                                btn.setTextColor(getResources().getColor(R.color.btn_normal));
                                btn.setBackgroundResource(R.drawable.shape_fellow_normal);
                            } else {
                                for (int j = 0; j < flexboxLayout.getChildCount(); j++) {
                                    Button btnj = flexboxLayout.getChildAt(j).findViewById(R.id.btn_fellow);
                                    btnj.setBackgroundResource(R.drawable.shape_fellow_normal);
                                    btnj.setText("追踪他");
                                    btnj.setTextColor(getResources().getColor(R.color.btn_normal));
                                    if (j == finalI) {
                                        btnj.setBackgroundResource(R.drawable.shape_fellow_star);
                                        btnj.setText("已追踪");
                                        btnj.setTextColor(getResources().getColor(R.color.bg_white));
                                    }
                                }
                            }
                        }
                    }, 500);

                }
            });

            flexboxLayout.getChildAt(i).findViewById(R.id.iv_photo).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, MasterActivity.class);
                    intent.putExtra("id", masters.get(finalI).id);
                    startActivity(intent);
                }
            });
        }
    }

    @OnClick({R.id.btn_test, R.id.tv_rolling, R.id.tv_news_more, R.id.tv_comment_more, R.id.tv_rank_more})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_test:
//                HomeFragment.this.getActivity().finish();
//                App.clearActivities();
//                Intent intent = new Intent(getActivity(), LoginActivity.class);
//                startActivity(intent);

                startActivity(new Intent(getActivity(), HtmlActivity.class));

                break;

            case R.id.tv_rolling:
                //滚动新闻
                Intent intent1 = new Intent(mContext, WebActivity.class);
                startActivity(intent1);
                break;
            case R.id.tv_news_more:
                //机构情报更多
                activity.switchContent(activity.mContent, activity.mFragments.get(3));
                activity.setDefaultResources(4);
                activity.setTitleName("情报");
                break;
            case R.id.tv_comment_more:
                //名师解盘更多
                activity.switchContent(activity.mContent, activity.mFragments.get(1));
                activity.setDefaultResources(2);
                activity.setTitleName("解盘");
                break;
            case R.id.tv_rank_more:
                //榜单更多
                activity.switchContent(activity.mContent, activity.mFragments.get(2));
                activity.setDefaultResources(3);
                activity.setTitleName("榜单");
                break;
        }
    }
}
