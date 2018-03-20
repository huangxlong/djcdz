package com.djc.djcdz.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.djc.djcdz.R;
import com.djc.djcdz.base.BaseFragment;
import com.djc.djcdz.entity.CommentsBean;
import com.djc.djcdz.ui.adapter.CommentsAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator
 * on 2018/3/13 星期二.
 */

public class CommentsFragment extends BaseFragment {
    @BindView(R.id.recycler)
    RecyclerView recycler;

    private List<CommentsBean> commentsList = new ArrayList<>();


    public static CommentsFragment newInstance() {
        return new CommentsFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_comments;
    }

    @Override
    protected void initView() {

        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(manager);


        CommentsBean commentsBean = new CommentsBean();
        commentsBean.name = "22";
        commentsList.add(commentsBean);
        CommentsAdapter adapter = new CommentsAdapter(commentsList);
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        adapter.isFirstOnly(false);
        recycler.setAdapter(adapter);

    }
}