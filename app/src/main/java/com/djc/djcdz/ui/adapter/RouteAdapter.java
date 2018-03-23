package com.djc.djcdz.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.djc.djcdz.R;
import com.djc.djcdz.entity.RspDto;

import java.util.List;

/**
 * Created by Administrator
 * on 2018/3/21 星期三.
 */

public class RouteAdapter extends BaseQuickAdapter<RspDto.Route, BaseViewHolder> {
    public RouteAdapter(@Nullable List<RspDto.Route> data) {
        super(R.layout.item_route, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RspDto.Route item) {
        List<RspDto.Route> data = getData();
        int position = helper.getLayoutPosition();
        helper.setText(R.id.tv_title, data.get(position).title);
        helper.setText(R.id.tv_time, data.get(position).time);
    }
}
