package com.djc.djcdz.ui.fragment;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.djc.djcdz.R;
import com.djc.djcdz.base.BaseFragment;
import com.djc.djcdz.entity.RspDto;
import com.djc.djcdz.ui.MainTabActivity;
import com.djc.djcdz.ui.adapter.CommentsAdapter;
import com.djc.djcdz.ui.adapter.PageAdapter;
import com.djc.djcdz.util.ToastUtil;
import com.djc.djcdz.view.HorCenterRecyclerView;
import com.djc.djcdz.view.MyItemDecoration;
import com.djc.djcdz.view.MyScrollView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator
 * on 2018/3/13 星期二.
 */

public class CommentsFragment extends BaseFragment {
    @BindView(R.id.scrollView)
    MyScrollView scrollView;
    @BindView(R.id.recycler_comment)
    RecyclerView recyclerComment;
    @BindView(R.id.recycler_page)
    HorCenterRecyclerView recyclerPage;
    private MainTabActivity activity;
    private int currentPage = 1;  //当前页数
    private int pageCount = 6;  //每页展示的个数
    private int totalPage; //总页数
    private List<Integer> pages = new ArrayList<>();
    private PageAdapter pageAdapter;
    private CommentsAdapter commentAdapter;
    private List<RspDto.Comment> commentsList = new ArrayList<>();


    public static CommentsFragment newInstance() {
        return new CommentsFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_comments;
    }

    @Override
    protected void initView() {
        activity = (MainTabActivity) getActivity();
        totalPage = activity.commentList.size() / pageCount;
        if (activity.commentList.size() % pageCount != 0) {
            totalPage = totalPage + 1;
        }
        for (int i = currentPage; i <= pageCount; i++) {
            commentsList.add(activity.commentList.get(i - 1));
        }

        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerComment.setLayoutManager(manager);
        commentAdapter = new CommentsAdapter(commentsList);
        commentAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        commentAdapter.isFirstOnly(false);
        recyclerComment.addItemDecoration(new MyItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        recyclerComment.setAdapter(commentAdapter);


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
        commentsList.clear();
        int currentTag = pageCount * (currentPage - 1) + 1;
        int max;
        if (currentPage == totalPage) {
            max = activity.commentList.size();
        } else {
            max = currentTag + pageCount - 1;
        }

        for (int i = currentTag; i <= max; i++) {
            commentsList.add(activity.commentList.get(i - 1));
        }
        commentAdapter.notifyDataSetChanged();
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