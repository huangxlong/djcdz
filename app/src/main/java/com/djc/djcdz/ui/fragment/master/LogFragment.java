package com.djc.djcdz.ui.fragment.master;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.djc.djcdz.R;
import com.djc.djcdz.base.BaseFragment;
import com.djc.djcdz.entity.RspDto;
import com.djc.djcdz.ui.MasterActivity;
import com.djc.djcdz.ui.adapter.LogAdapter;
import com.djc.djcdz.ui.adapter.PageAdapter;
import com.djc.djcdz.ui.adapter.RouteAdapter;
import com.djc.djcdz.util.ToastUtil;
import com.djc.djcdz.view.HorCenterRecyclerView;
import com.djc.djcdz.view.MyItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 操作日志
 * Created by Administrator
 * on 2018/3/23 星期五.
 */

public class LogFragment extends BaseFragment {
    @BindView(R.id.recycler_log)
    RecyclerView recyclerLog;
    @BindView(R.id.recycler_page)
    HorCenterRecyclerView recyclerPage;
    private MasterActivity activity;
    private int currentPage = 1;  //当前页数
    private int pageCount = 6;  //每页展示的个数
    private int totalPage; //总页数

    private List<RspDto.Log> mLogList = new ArrayList<>();
    private List<Integer> pages = new ArrayList<>();
    private PageAdapter pageAdapter;
    private LogAdapter logAdapter;


    public static LogFragment newInstance() {
        return new LogFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_log;
    }

    @Override
    protected void initView() {
        activity = (MasterActivity) getActivity();


        totalPage = activity.logList.size() / pageCount;
        if (activity.logList.size() % pageCount != 0) {
            totalPage = totalPage + 1;
        }
        for (int i = currentPage; i <= pageCount; i++) {
            mLogList.add(activity.logList.get(i - 1));
        }
        logAdapter = new LogAdapter(mLogList);
        LinearLayoutManager logManager = new LinearLayoutManager(mContext);
        logManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerLog.addItemDecoration(new MyItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        recyclerLog.setLayoutManager(logManager);
        recyclerLog.setAdapter(logAdapter);


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
        mLogList.clear();
        int currentTag = pageCount * (currentPage - 1) + 1;
        int max;
        if (currentPage == totalPage) {
            max = activity.logList.size();
        } else {
            max = currentTag + pageCount - 1;
        }

        for (int i = currentTag; i <= max; i++) {
            mLogList.add(activity.logList.get(i - 1));
        }
        logAdapter.notifyDataSetChanged();
        pageAdapter.notifyDataSetChanged();

        if (isLeft) {
            recyclerPage.scrollToPosition(currentPage - 2);
        } else {
            recyclerPage.scrollToPosition(currentPage + 1);
        }

        activity.scrollView.smoothScrollTo(0, 0);

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
