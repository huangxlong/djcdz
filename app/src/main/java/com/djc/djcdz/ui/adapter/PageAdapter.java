package com.djc.djcdz.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.BaseAdapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.djc.djcdz.R;

import java.util.List;

/**
 * Created by Administrator
 * on 2018/3/22 星期四.
 */

public class PageAdapter extends BaseQuickAdapter<Integer, BaseViewHolder> {
    private int mCurrentPage;

    public PageAdapter(@Nullable List<Integer> data, int current) {
        super(R.layout.item_page, data);
        mCurrentPage = current;
    }

    public void setCurrent(int page) {
        mCurrentPage = page;
    }

    @Override
    protected void convert(BaseViewHolder helper, Integer item) {
        List<Integer> data = getData();
        int position = helper.getLayoutPosition();
        helper.setText(R.id.tv_page, data.get(position) + "");
        if (mCurrentPage == position + 1) {
            helper.setTextColor(R.id.tv_page, mContext.getResources().getColor(R.color.page_current));
        } else {
            helper.setTextColor(R.id.tv_page, mContext.getResources().getColor(R.color.page_other));
        }
    }
}
