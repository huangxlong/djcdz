package com.djc.djcdz.ui.login;


import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.djc.djcdz.R;
import com.djc.djcdz.base.BaseActivity;
import com.djc.djcdz.ui.MainTabActivity;
import com.djc.djcdz.ui.PlayerActivity;
import com.djc.djcdz.view.tickview.RatingView;
import com.djc.djcdz.view.tickview.TickView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.input_layout_name)
    LinearLayout mName;
    @BindView(R.id.input_layout_psw)
    LinearLayout mPsw;
    @BindView(R.id.progressBar2)
    ProgressBar progress;
    @BindView(R.id.main_btn_login)
    TextView mBtnLogin;
    @BindView(R.id.input_layout)
    View mInputLayout;
    @BindView(R.id.tick_view)
    TickView tickView;
    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.rating_view)
    RatingView ratingView;
    private Boolean isClick = false;  //是否已经点击登录按钮，防止多次点击
    private Unbinder unbinder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        unbinder = ButterKnife.bind(this);
        initView();
    }


    private void initView() {
        etAccount.setText("123");
        etPassword.setText("123");
    }

    @OnClick({R.id.main_btn_login, R.id.btn_reset})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_btn_login:
//                login();
                //执行登录按钮动画
                if (!isClick) {
                    isClick = true;
                    inputAnimator(mBtnLogin, mBtnLogin.getMeasuredWidth());
                }
                break;
            case R.id.btn_reset:
                reset();

                break;

        }
    }

    /**
     * 登录
     */
    private void login() {
        final String mAccount = etAccount.getText().toString().trim();
        final String mPassword = etPassword.getText().toString().trim();


        //执行网络请求
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mAccount.equals("123") && mPassword.equals("123")) {
                    //success
                    ratingView.setChecked(false);
                    ratingView.setVisibility(View.GONE);
                    tickView.setVisibility(View.VISIBLE);
                    tickView.setChecked(true);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ratingView.setChecked(false);
                            Intent intent = new Intent(LoginActivity.this, MainTabActivity.class);
//                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this).toBundle());
//                            } else {
                            startActivity(intent);
//                            }
                        }
                    }, 1000);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            reset();
                        }
                    }, 1200);
                } else {
                    //失败

                    reset();

                }

            }
        }, 700);


    }

    private void reset() {
        mBtnLogin.setVisibility(View.VISIBLE);
        final ObjectAnimator animator2 = ObjectAnimator.ofFloat(mBtnLogin,
                "scaleX", 0, 1f);
        animator2.setDuration(300);
        animator2.start();
        tickView.setChecked(false);
        tickView.setVisibility(View.GONE);
        ratingView.setChecked(false);
        ratingView.setVisibility(View.GONE);

    }

    /**
     * 登录的动画效果
     *
     * @param view 控件
     * @param w    宽
     */
    private void inputAnimator(final View view, float w) {

        AnimatorSet set = new AnimatorSet();

        ValueAnimator animator = ValueAnimator.ofFloat(0, w);
        final ObjectAnimator animator2 = ObjectAnimator.ofFloat(view,
                "scaleX", 1, 0f);
        set.setDuration(500);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.playTogether(animator, animator2);
        set.start();
        set.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                /**
                 * 动画结束后，先显示加载的动画，然后再隐藏输入框
                 */
                isClick = false;  //登录按钮动画执行完毕，置为未点击状态
                mBtnLogin.setVisibility(View.GONE);
                ratingView.setChecked(true);
                ratingView.setVisibility(View.VISIBLE);

                login();


            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
