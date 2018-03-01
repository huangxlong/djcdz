package com.djc.djcdz.http;

import com.djc.djcdz.entity.BaseRsp;
import com.djc.djcdz.entity.CommendReportReq;

import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by Administrator
 * on 2018/2/28 星期三.
 */

public interface HttpService {


    @POST("reportClick")
    Observable<Response<BaseRsp>> report(@Body CommendReportReq body);
}
