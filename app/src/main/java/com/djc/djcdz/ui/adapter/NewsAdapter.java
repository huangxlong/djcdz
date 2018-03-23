package com.djc.djcdz.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.djc.djcdz.R;
import com.djc.djcdz.entity.RspDto;

import java.util.List;

/**
 * Created by Administrator
 * on 2018/2/26 星期一.
 */

public class NewsAdapter extends BaseQuickAdapter<RspDto.News, BaseViewHolder> {

    public NewsAdapter(@Nullable List<RspDto.News> data) {
        super(R.layout.item_news, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RspDto.News item) {
        List<RspDto.News> data = getData();
        int position = helper.getLayoutPosition();
//        Glide.with(mContext).load(data.get(position).photoUrl).into((ImageView) helper.getView(R.id.iv_photo));
        helper.setText(R.id.tv_title, data.get(position).title);
//                .setText(R.id.tv_count, "121" + position)
//                .setText(R.id.tv_time, "2018-02-22");
    }


}
