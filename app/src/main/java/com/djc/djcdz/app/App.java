package com.djc.djcdz.app;

import android.app.Activity;
import android.app.Application;

import com.djc.djcdz.BuildConfig;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2018/2/22.
 */

public class App extends Application {
    protected static App sApp;
    public static final boolean IS_PUBLISH_VERSION = BuildConfig.PUBLIC;
    public List<Activity> mActivities = new ArrayList<Activity>();

    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;

        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);     		// 初始化 JPush
    }

    public static App getApplication() {
        return sApp;
    }

    public static void addActivity(Activity activity) {
        if (sApp != null) {
            sApp.mActivities.add(activity);
        }
    }

    public static Activity getTopActivity() {
        if (sApp != null && sApp.mActivities.size() > 0) {
            return sApp.mActivities.get(sApp.mActivities.size() - 1);
        } else {
            return null;
        }
    }

    public static void removeActivity(Activity activity) {
        if (sApp != null) {
            sApp.mActivities.remove(activity);
        }
    }

    public static void clearActivities() {
        for (Activity activity : sApp.mActivities) {
            if (activity != null) {
                activity.finish();
            }
        }
        sApp.mActivities.clear();
    }

    public static void exit() {
        clearActivities();
        System.exit(0);
    }

}
