package com.djc.djcdz.ui.fragment.master;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.djc.djcdz.R;
import com.djc.djcdz.base.BaseFragment;
import com.djc.djcdz.util.InputValidUtils;
import com.djc.djcdz.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 留言
 * Created by Administrator
 * on 2018/3/22 星期四.
 */

public class WordsFragment extends BaseFragment {

    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_words)
    EditText etWords;
    private String name;
    private String phone;
    private String words;

    public static WordsFragment newInstance() {
        return new WordsFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_words;
    }

    @Override
    protected void initView() {

    }


    @OnClick(R.id.btn_commit)
    public void onClick() {

        if (check()) {

            //提交
            showLoadingDialog("");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ToastUtil.show(mContext, "已提交");
                    etName.setText("");
                    etPhone.setText("");
                    etWords.setText("");
                    hideLoadingDialog();
                }
            }, 500);
        }
    }

    private Boolean check() {
        name = etName.getText().toString().trim();
        phone = etPhone.getText().toString().trim();
        words = etWords.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            etName.setError("姓名不能为空");
            etName.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(phone)) {
            etPhone.setError("手机号不能为空");
            etPhone.requestFocus();
            return false;
        } else if (!InputValidUtils.isMobilePhoneValid(phone)) {
            etPhone.setError("请输入正确的手机号");
            etPhone.requestFocus();
            return false;
        } else if (TextUtils.isEmpty(words)) {
            etWords.setError("留言内容不能为空");
            etWords.requestFocus();
            return false;
        }
        return true;
    }
}
