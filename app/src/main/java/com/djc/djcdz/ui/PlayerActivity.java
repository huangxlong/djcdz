package com.djc.djcdz.ui;

import com.bumptech.glide.Glide;
import com.djc.djcdz.R;
import com.djc.djcdz.base.BaseActivity;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;
import com.xiao.nicevideoplayer.TxVideoPlayerController;

import butterknife.BindView;

/**
 * Created by Administrator
 * on 2018/3/15 星期四.
 */

public class PlayerActivity extends BaseActivity {

    @BindView(R.id.nice_video_player)
    NiceVideoPlayer mNiceVideoPlayer;


    @Override
    protected int getLayout() {
        return R.layout.activity_player;
    }

    @Override
    protected void initView() {
        mNiceVideoPlayer.setPlayerType(NiceVideoPlayer.TYPE_IJK); // IjkPlayer or MediaPlayer
        String videoUrl = "http://covertness.qiniudn" +
        ".com/android_zaixianyingyinbofangqi_test_baseline.mp4";
        mNiceVideoPlayer.setUp(videoUrl, null);
        TxVideoPlayerController controller = new TxVideoPlayerController(this);
        controller.setTitle("测试标题");
        controller.setLenght(98000);
        Glide.with(this)
                .load("http://imgsrc.baidu.com/image/c0%3Dshijue%2C0%2C0%2C245%2C40/sign=304dee3ab299a9012f38537575fc600e/91529822720e0cf3f8b77cd50046f21fbe09aa5f.jpg")
                .placeholder(R.drawable.img_default)
                .crossFade()
                .into(controller.imageView());
        mNiceVideoPlayer.setController(controller);
    }
    @Override
    protected void onStop() {
        super.onStop();
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
    }

    @Override
    public void onBackPressed() {
        if (NiceVideoPlayerManager.instance().onBackPressd()) return;
        super.onBackPressed();
    }

}
