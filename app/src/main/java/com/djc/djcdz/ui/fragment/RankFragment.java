package com.djc.djcdz.ui.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.djc.djcdz.R;
import com.djc.djcdz.base.BaseFragment;
import com.djc.djcdz.entity.RspDto;
import com.djc.djcdz.ui.MainTabActivity;
import com.djc.djcdz.ui.WebActivity;
import com.djc.djcdz.ui.adapter.NewsAdapter;
import com.djc.djcdz.ui.adapter.PageAdapter;
import com.djc.djcdz.ui.adapter.RankAdapter;
import com.djc.djcdz.util.ToastUtil;
import com.djc.djcdz.view.HorCenterRecyclerView;
import com.djc.djcdz.view.MyScrollView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/2/24.
 * 最新榜单
 */

public class RankFragment extends BaseFragment {
    @BindView(R.id.scrollView)
    MyScrollView scrollView;
    @BindView(R.id.recycler_rank)
    RecyclerView recyclerRank;
    @BindView(R.id.recycler_page)
    HorCenterRecyclerView recyclerPage;
    private MainTabActivity activity;
    private int currentPage = 1;  //当前页数
    private int pageCount = 6;  //每页展示的个数
    private int totalPage; //总页数
    private List<RspDto.Rank> rankList = new ArrayList<>();
    private List<Integer> pages = new ArrayList<>();
    private PageAdapter pageAdapter;
    private RankAdapter rankAdapter;


    public static RankFragment newInstance() {
        return new RankFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_rank;
    }

    @Override
    protected void initView() {
        activity = (MainTabActivity) getActivity();
        totalPage = activity.rankList.size() / pageCount;
        if (activity.rankList.size() % pageCount != 0) {
            totalPage = totalPage + 1;
        }
        for (int i = currentPage; i <= pageCount; i++) {
            rankList.add(activity.rankList.get(i - 1));
        }
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerRank.setLayoutManager(manager);
        rankAdapter = new RankAdapter(rankList);
        rankAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);  //开启动画
        rankAdapter.setNotDoAnimationCount(6);
        rankAdapter.isFirstOnly(false); //重复执行动画
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


        for (int i = 0; i < totalPage; i++) {
            pages.add(i + 1);
        }
        pageAdapter = new PageAdapter(pages, currentPage);
        LinearLayoutManager pagerManager = new LinearLayoutManager(mContext);
        pagerManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerPage.setLayoutManager(pagerManager);
        recyclerPage.setAdapter(pageAdapter);
        pageAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtil.show(mContext, position + "");
                currentPage = position + 1;
                turnPage(false);


            }
        });

    }

    /**
     * 翻页
     */
    private void turnPage(Boolean isLeft) {
        pageAdapter.setCurrent(currentPage);
        rankList.clear();
        int currentTag = pageCount * (currentPage - 1) + 1;
        int max;
        if (currentPage == totalPage) {
            max = activity.rankList.size();
        } else {
            max = currentTag + pageCount - 1;
        }

        for (int i = currentTag; i <= max; i++) {
            rankList.add(activity.rankList.get(i - 1));
        }
        rankAdapter.notifyDataSetChanged();
        pageAdapter.notifyDataSetChanged();

        if (isLeft) {
            recyclerPage.scrollToPosition(currentPage - 2);
        } else {
            recyclerPage.scrollToPosition(currentPage + 1);
        }

        scrollView.smoothScrollTo(0, 0);

    }


    @OnClick({R.id.iv_left, R.id.iv_right})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_left:
                //上一页
                if (currentPage != 1) {
                    currentPage -= 1;
                    turnPage(true);
                }
                break;
            case R.id.iv_right:
                //下一页
                if (currentPage != totalPage) {
                    currentPage += 1;
                    turnPage(false);
                }
                break;
        }
    }
}
