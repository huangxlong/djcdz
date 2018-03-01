package com.djc.djcdz.http;

import android.app.Activity;
import android.os.Build;
import android.text.TextUtils;

import com.djc.djcdz.R;
import com.djc.djcdz.app.App;
import com.djc.djcdz.app.CSConfig;
import com.djc.djcdz.entity.BaseRsp;
import com.djc.djcdz.util.LogUtils;
import com.djc.djcdz.util.NetUtil;
import com.djc.djcdz.util.ToastUtil;
import com.djc.djcdz.view.LoadingDialog;

import java.util.List;

import okhttp3.Headers;
import retrofit2.Response;
import rx.Subscriber;

/**
 * Created by Administrator on 2018/2/28 0024.
 *
 */

public abstract class BaseSubscriber<T> extends Subscriber<Response<T>> {

    private static final int SESSION_NULL = -10002;
    private static final int SESSION_ERROR = -10003;
    private static final int SESSION_TIMEOUT = -10004;
    private Activity mContext;

    protected BaseSubscriber(Activity ctx) {
        this.mContext = ctx;
    }

    @Override
    public void onStart() {
        super.onStart();
        showLoadingDialog();
        LogUtils.d("request:", "Start");
        if (!NetUtil.isConnected(mContext)) {
            hideLoadingDialog();
            ToastUtil.show(mContext, R.string.toast_net_error);
            //取消本次订阅
            if (!isUnsubscribed()) {
                unsubscribe();
            }
            return;
        }
    }

    @Override
    public void onCompleted() {
        hideLoadingDialog();
        LogUtils.d("request:", "completed");
    }

    @Override
    public void onError(Throwable e) {
        hideLoadingDialog();
        LogUtils.d("request:", "Error");
        BaseRsp rsp = new BaseRsp();
        rsp.code = -100;
        if (e instanceof Exception) {
            //访问获得对应的Exception
            rsp.msg = ExceptionHandle.handleException(e).message;
            onResponse(rsp);
//            onError(ExceptionHandle.handleException(e));
        } else {
            //将Throwable 和 未知错误的status code返回
//            onError(new ExceptionHandle.ResponeThrowable(e,ExceptionHandle.ERROR.UNKNOWN));
            rsp.msg = "未知错误";
            onResponse(rsp);
        }
    }

    @Override
    public void onNext(Response<T> response) {
        LogUtils.d("request:", "Next");
        BaseRsp rsp = new BaseRsp();
        String data = "";
        String rspStr = "";
        try {
            rsp = (BaseRsp) response.body();
            rsp.data = rsp.data == null ? "" : rsp.data;
//            rsp = JsonUtil.parseJson(rspStr, BaseRsp.class);
            LogUtils.d("onResponse", "data:" + rsp.data);
//            JSONObject object = new JSONObject(rspStr);
//            String string = object.get("data").toString();

        } catch (Exception e) {
            e.printStackTrace();
            if (!TextUtils.isEmpty(e.getMessage()) && e.getMessage().contains("BadPaddingException")) {
//                rsp.code = PRIVATE_ERROR;
//                rsp.msg = "数据异常，请重试";
            }
        } finally {
            //获取session的操作，session放在cookie头，且取出后含有“；”，取出后为下面的 s （也就是jsesseionid）
            Headers headers = response.headers();
            List<String> cookies = headers.values("x-auth-token");
            if (cookies != null && !cookies.isEmpty()) {
                String session = cookies.get(0);
                CSConfig.instance().setSession(App.getApplication(), session);
            }
            if ((rsp.code == SESSION_NULL || rsp.code == SESSION_ERROR || rsp.code == SESSION_TIMEOUT || rsp.code == 401)) {
                //
//                reLogin();
            }
//            else if (rsp.code == PRIVATE_ERROR) {
//                HttpUtil.clearPrivate();
//                onResponse(rsp, data);
//            }
            else {
                onResponse(rsp);
            }
        }

    }

    public abstract void onResponse(BaseRsp rsp);


    private LoadingDialog mLoadingDialog;

    private void showLoadingDialog() {
        if (isValidContext(mContext)) {
            mContext.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mLoadingDialog == null) {
                        mLoadingDialog = new LoadingDialog(mContext);
                    }

                    mLoadingDialog.setMessage(mContext.getString(R.string.loading));
                    mLoadingDialog.show();
                }
            });
        }
    }

    protected void showLoadingDialog(final String text) {
        if (isValidContext(mContext)) {
            mContext.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mLoadingDialog == null) {
                        mLoadingDialog = new LoadingDialog(mContext);
                    }

                    mLoadingDialog.setMessage(text);
                    mLoadingDialog.show();
                }
            });
        }
    }

    private void hideLoadingDialog() {
        if (isValidContext(mContext)) {
            mContext.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mLoadingDialog != null) {
                        mLoadingDialog.dismiss();
                    }
                }
            });
        }
    }

    private boolean isValidContext(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return !(activity == null || activity.isDestroyed() || activity.isFinishing());
        } else {
            return !(activity == null || activity.isFinishing());
        }
    }
}
