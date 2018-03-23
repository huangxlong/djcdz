package com.djc.djcdz.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.djc.djcdz.R;
import com.djc.djcdz.entity.RspDto;

import java.util.List;

/**
 * Created by Administrator
 * on 2018/2/26 星期一.
 */

public class CommentsAdapter extends BaseQuickAdapter<RspDto.Comment, BaseViewHolder> {

    public CommentsAdapter(@Nullable List<RspDto.Comment> data) {
        super(R.layout.item_comments, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, RspDto.Comment item) {
        List<RspDto.Comment> data = getData();
        int position = helper.getLayoutPosition();
        helper.setText(R.id.tv_name, "名字")
                .setText(R.id.tv_comments, data.get(position).commnets);
//        Glide.with(mContext).load(item.photoUrl).into((ImageView) helper.getView(R.id.iv_photo));
    }
}
