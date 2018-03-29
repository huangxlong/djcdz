package com.djc.djcdz.ui.adapter;

import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.djc.djcdz.R;
import com.djc.djcdz.entity.RspDto;

import java.util.List;

/**
 * Created by Administrator
 * on 2018/3/26 星期一.
 */

public class RankAdapter extends BaseQuickAdapter<RspDto.Rank, BaseViewHolder> {
    public RankAdapter(@Nullable List<RspDto.Rank> data) {
        super(R.layout.item_rank, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RspDto.Rank item) {
        List<RspDto.Rank> data = getData();
        int position = helper.getLayoutPosition();
        ((TextView) helper.getView(R.id.tv_check)).getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        ((TextView) helper.getView(R.id.tv_star_ta)).getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        helper.addOnClickListener(R.id.tv_check)
                .setText(R.id.tv_rank, data.get(position).rank + "")
                .setText(R.id.tv_name, data.get(position).name)
                .addOnClickListener(R.id.tv_star_ta);
    }
}
