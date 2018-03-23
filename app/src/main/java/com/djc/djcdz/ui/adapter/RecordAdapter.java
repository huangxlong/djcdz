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

public class RecordAdapter extends BaseQuickAdapter<RspDto.Record, BaseViewHolder> {
    public RecordAdapter(@Nullable List<RspDto.Record> data) {
        super(R.layout.item_record, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RspDto.Record item) {
        List<RspDto.Record> data = getData();
        int position = helper.getLayoutPosition();
        helper.setText(R.id.tv_name, data.get(position).name)
                .setText(R.id.tv_code, data.get(position).code)
                .setText(R.id.tv_new_price, data.get(position).newPrice)
                .setText(R.id.tv_new_profit, data.get(position).newProfit)
                .setText(R.id.tv_buy_price, data.get(position).buyPrice)
                .setText(R.id.tv_buy_time, data.get(position).buyTime)
                .setRating(R.id.ratingBar, data.get(position).star, (int) data.get(position).star)
                .setText(R.id.tv_depot_sug, data.get(position).depotSug)
                .setText(R.id.tv_stop_sug, data.get(position).stopSug)
                .setText(R.id.tv_sale_price, data.get(position).salePrice)
                .setText(R.id.tv_sale_time, data.get(position).saleTime);

    }
}
