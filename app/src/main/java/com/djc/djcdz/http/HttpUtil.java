package com.djc.djcdz.http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2018/2/23.
 *
 */

public class HttpUtil {
    public final static int OK = 1;
    private final static int CONNECT_TIMEOUT = 30;
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    private static final MediaType TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType TYPE_FORM = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");

    private OkHttpClient mClient;
    private static HttpUtil mInstance;

    private static HttpUtil instance() {
        if (mInstance == null) {
            synchronized (HttpUtil.class) {
                if (mInstance == null) {
                    mInstance = new HttpUtil();
                }
            }
        }
        return mInstance;
    }

    private OkHttpClient getHttp() {
        return mClient;
    }

    private HttpUtil() {
        mClient = new OkHttpClient().newBuilder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .cookieJar(new CookieJar() {
                    private final HashMap<HttpUrl, List<Cookie>> cookieStore = new HashMap<>();

                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                        cookieStore.put(url, cookies);
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        List<Cookie> cookies = cookieStore.get(url.host());
                        return cookies != null ? cookies : new ArrayList<Cookie>();
                    }
                }).build();
    }
}
