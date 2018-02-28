package com.djc.djcdz.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.djc.djcdz.R;
import com.djc.djcdz.base.BaseFragment;
import com.djc.djcdz.entity.NewsBean;
import com.djc.djcdz.ui.MainTabActivity;
import com.djc.djcdz.ui.adapter.NewsAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/2/24.
 *  情报
 */

public class NewsFragment extends BaseFragment {
    @BindView(R.id.recycler)
    RecyclerView recycler;
    private List<NewsBean> newsList = new ArrayList<>();

    public static NewsFragment newInstance() {
        return new NewsFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_news;
    }

    @Override
    protected void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(manager);
        for (int i = 0; i < 50; i++) {
            NewsBean newsBean = new NewsBean();
            newsList.add(newsBean);
        }
        NewsAdapter adapter = new NewsAdapter(newsList);
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);  //开启动画
        adapter.setNotDoAnimationCount(6);
        adapter.isFirstOnly(false); //重复执行动画
        recycler.setAdapter(adapter);
        ((MainTabActivity) getActivity()).setTitleName("2");
    }
}
