package com.djc.djcdz.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.djc.djcdz.R;
import com.djc.djcdz.entity.NewsBean;

import java.util.List;

/**
 * Created by Administrator
 * on 2018/2/26 星期一.
 */

public class NewsAdapter extends BaseQuickAdapter<NewsBean, BaseViewHolder> {

    public NewsAdapter(@Nullable List<NewsBean> data) {
        super(R.layout.item_news, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewsBean item) {
        List<NewsBean> data = getData();
        int position = helper.getLayoutPosition();
        Glide.with(mContext).load(data.get(position).photoUrl).into((ImageView) helper.getView(R.id.iv_photo));
        helper.setText(R.id.tv_title, "标题" + position)
                .setText(R.id.tv_content, "内容" + position)
                .setText(R.id.tv_count, "121" + position)
                .setText(R.id.tv_time, "2018-02-22");
    }


}
