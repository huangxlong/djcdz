package com.djc.djcdz.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.djc.djcdz.R;
import com.djc.djcdz.entity.MasterBean;

import java.util.List;

/**
 * Created by Administrator
 * on 2018/3/14 星期三.
 */

public class MasterAdapter extends BaseQuickAdapter<MasterBean, BaseViewHolder> {
    public MasterAdapter(@Nullable List<MasterBean> data) {
        super(R.layout.item_master, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MasterBean item) {
        int position = helper.getLayoutPosition();
        List<MasterBean> data = getData();
        helper.setText(R.id.tv_name, data.get(position).name);
    }
}
