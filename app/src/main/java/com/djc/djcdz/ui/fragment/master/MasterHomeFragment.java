package com.djc.djcdz.ui.fragment.master;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.djc.djcdz.R;
import com.djc.djcdz.base.BaseFragment;
import com.djc.djcdz.entity.RspDto;
import com.djc.djcdz.ui.MasterActivity;
import com.djc.djcdz.ui.adapter.LogAdapter;
import com.djc.djcdz.ui.adapter.RecordAdapter;
import com.djc.djcdz.ui.adapter.RouteAdapter;
import com.djc.djcdz.view.MyItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator
 * on 2018/3/22 星期四.
 */

public class MasterHomeFragment extends BaseFragment {

    @BindView(R.id.recycler_route)
    RecyclerView recyclerRoute;
    @BindView(R.id.recycler_log)
    RecyclerView recyclerLog;
    @BindView(R.id.recycler_record)
    RecyclerView recyclerRecord;
    private List<RspDto.Route> mRouteList = new ArrayList<>();
    private List<RspDto.Log> mLogList = new ArrayList<>();
    private List<RspDto.Record> mRecordList = new ArrayList<>();
    private MasterActivity activity;


    public static MasterHomeFragment newInstance() {
        return new MasterHomeFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_master_home;
    }

    @Override
    protected void initView() {
        activity = (MasterActivity) getActivity();
        //最新研究成果
        mRouteList.add(activity.routeList.get(0));


        RouteAdapter routeAdapter = new RouteAdapter(mRouteList);
        LinearLayoutManager routeManager = new LinearLayoutManager(mContext);
        routeManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerRoute.setLayoutManager(routeManager);
        recyclerRoute.addItemDecoration(new MyItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        recyclerRoute.setAdapter(routeAdapter);


        //操作日志
        for (int i = 0; i < 5; i++) {
            mLogList.add(activity.logList.get(i));
        }

        LogAdapter logAdapter = new LogAdapter(mLogList);
        LinearLayoutManager logManager = new LinearLayoutManager(mContext);
        logManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerLog.setLayoutManager(logManager);
        recyclerLog.addItemDecoration(new MyItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        recyclerLog.setAdapter(logAdapter);


        //操作记录
        for (int i = 0; i < 6; i++) {
            mRecordList.add(activity.recordList.get(i));
        }

        RecordAdapter recordAdapter = new RecordAdapter(mRecordList);
        LinearLayoutManager recordManager = new LinearLayoutManager(mContext);
        recordManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerRecord.setLayoutManager(recordManager);
        recyclerRecord.setAdapter(recordAdapter);

    }


    @OnClick({R.id.tv_route_more, R.id.tv_log_more, R.id.tv_record_more})
    void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_route_more:
                //最新研究成果更多
                activity.switchContent(activity.mContent, activity.mFragments.get(1));
                activity.addTitle("路线图");
                break;
            case R.id.tv_log_more:
                //操作日志更多
                activity.switchContent(activity.mContent, activity.mFragments.get(2));
                activity.addTitle("操作日志");
                break;
            case R.id.tv_record_more:
                //操作记录更多
                activity.switchContent(activity.mContent, activity.mFragments.get(3));
                activity.addTitle("操作记录");
                break;
        }
    }


}
