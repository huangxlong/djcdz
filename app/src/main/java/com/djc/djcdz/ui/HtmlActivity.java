package com.djc.djcdz.ui;

import android.graphics.drawable.Drawable;
import android.text.Html;

import com.djc.djcdz.R;
import com.djc.djcdz.base.BaseActivity;
import com.djc.djcdz.view.MImageGetter;

import org.sufficientlysecure.htmltextview.HtmlAssetsImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import butterknife.BindView;

public class HtmlActivity extends BaseActivity {

    @BindView(R.id.htmlTextView)
    HtmlTextView htmlTextView;

    @Override
    protected int getLayout() {
        return R.layout.activity_html;
    }

    private String html = "";

    @Override
    protected void initView() {
        htmlTextView.setHtml(html, new MImageGetter(htmlTextView, this)
        );
    }
}
