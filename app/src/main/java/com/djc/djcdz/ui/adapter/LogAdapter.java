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

public class LogAdapter extends BaseQuickAdapter<RspDto.Log, BaseViewHolder> {
    public LogAdapter(@Nullable List<RspDto.Log> data) {
        super(R.layout.item_log, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RspDto.Log item) {
        List<RspDto.Log> data = getData();
        int position = helper.getLayoutPosition();
        helper.setText(R.id.tv_content, data.get(position).content);
        helper.setText(R.id.tv_time, data.get(position).time);
    }
}
