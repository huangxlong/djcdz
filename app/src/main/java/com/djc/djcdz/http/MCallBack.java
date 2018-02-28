package com.djc.djcdz.http;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/2/23.
 *
 */

public abstract class MCallBack implements Callback {

    @Override
    public void onFailure(Call call, IOException e) {

    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {

    }


    public abstract void onResponse();
}
