package com.djc.djcdz.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.djc.djcdz.R;
import com.djc.djcdz.entity.CommentsBean;

import java.util.List;

/**
 * Created by Administrator
 * on 2018/2/26 星期一.
 */

public class CommentsAdapter extends BaseQuickAdapter<CommentsBean, BaseViewHolder> {

    public CommentsAdapter(int layoutResId, @Nullable List<CommentsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommentsBean item) {
        int position = helper.getLayoutPosition();
        helper.setText(R.id.tv_comments, "内容");
        helper.setText(R.id.tv_name, "名字");
        Glide.with(mContext).load(item.photoUrl).into((ImageView) helper.getView(R.id.iv_photo));
    }
}
