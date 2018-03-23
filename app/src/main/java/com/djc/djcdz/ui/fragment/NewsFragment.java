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
import com.djc.djcdz.ui.MasterActivity;
import com.djc.djcdz.ui.WebActivity;
import com.djc.djcdz.ui.adapter.LogAdapter;
import com.djc.djcdz.ui.adapter.NewsAdapter;
import com.djc.djcdz.ui.adapter.PageAdapter;
import com.djc.djcdz.util.ToastUtil;
import com.djc.djcdz.view.MyScrollView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/2/24.
 * 情报
 */

public class NewsFragment extends BaseFragment {
    @BindView(R.id.scrollView)
    MyScrollView scrollView;
    @BindView(R.id.recycler_news)
    RecyclerView recyclerNews;
    @BindView(R.id.recycler_page)
    RecyclerView recyclerPage;
    private MainTabActivity activity;
    private int currentPage = 1;  //当前页数
    private int pageCount = 6;  //每页展示的个数
    private int totalPage; //总页数
    private List<RspDto.News> newsList = new ArrayList<>();
    private List<Integer> pages = new ArrayList<>();
    private PageAdapter pageAdapter;
    private NewsAdapter newsAdapter;

    public static NewsFragment newInstance() {
        return new NewsFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_news;
    }

    @Override
    protected void initView() {
        activity = (MainTabActivity) getActivity();
        totalPage = activity.newsList.size() / pageCount;
        if (activity.newsList.size() % pageCount != 0) {
            totalPage = totalPage + 1;
        }
        for (int i = currentPage; i <= pageCount; i++) {
            newsList.add(activity.newsList.get(i - 1));
        }
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerNews.setLayoutManager(manager);
        newsAdapter = new NewsAdapter(newsList);
        newsAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);  //开启动画
        newsAdapter.setNotDoAnimationCount(6);
        newsAdapter.isFirstOnly(false); //重复执行动画
        recyclerNews.setAdapter(newsAdapter);

        newsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(mContext, WebActivity.class);
                intent.putExtra("title", "position:" + position);
                startActivity(intent);
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
        newsList.clear();
        int currentTag = pageCount * (currentPage - 1) + 1;
        int max;
        if (currentPage == totalPage) {
            max = activity.newsList.size();
        } else {
            max = currentTag + pageCount - 1;
        }

        for (int i = currentTag; i <= max; i++) {
            newsList.add(activity.newsList.get(i - 1));
        }
        newsAdapter.notifyDataSetChanged();
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
