package www.wanandroid.com.wanandroid.activity;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;
import www.wanandroid.com.wanandroid.R;
import www.wanandroid.com.wanandroid.adapter.FragmentAdapter;
import www.wanandroid.com.wanandroid.constant.Constant;
import www.wanandroid.com.wanandroid.fragment.AnchorFragment;
import www.wanandroid.com.wanandroid.fragment.LiveChatFragment;
import www.wanandroid.com.wanandroid.fragment.RankFragment;
import www.wanandroid.com.wanandroid.manager.ThreadPoolManager;
import www.wanandroid.com.wanandroid.observer.MyObserver;
import www.wanandroid.com.wanandroid.service.bean.LiveUrl;
import www.wanandroid.com.wanandroid.utils.HttpUtil;
import www.wanandroid.com.wanandroid.utils.NumberUtil;
import www.wanandroid.com.wanandroid.utils.ToastUtil;
import www.wanandroid.com.wanandroid.widget.CustomCircleProgressBar;
import www.wanandroid.com.wanandroid.widget.IJKVideoPlayer;
import www.wanandroid.com.wanandroid.widget.VideoPlayerListener;

@Route(path = Constant.ACTIVITY_URL_ROOM_LIVE)
public class LiveRoomActivity extends BaseActivity implements IJKVideoPlayer.ScrollListener, VideoPlayerListener {
    @BindView(R.id.videoPlayer)
    IJKVideoPlayer videoPlayer;
    @Autowired(name = Constant.LIVE_ROOM_ID)
    String roomId;
    @Autowired(name = Constant.LIVE_ROOM_NAME)
    String roomName;
    @BindView(R.id.tv_control_num)
    TextView tvControlNum;
    @BindView(R.id.view_control)
    RelativeLayout viewControl;
    @BindView(R.id.circle_progressBar)
    CustomCircleProgressBar circleProgressBar;
    @BindView(R.id.iv_live_play)
    ImageView ivLivePlay;
    @BindView(R.id.iv_full_screen)
    ImageView ivFullScreen;
    @BindView(R.id.rl_video_control)
    RelativeLayout rlVideoControl;
    @BindView(R.id.tv_room_name)
    TextView tvRoomName;
    @BindView(R.id.fl_video_player)
    FrameLayout flVideoPlayer;
    @BindView(R.id.iv_danmu)
    ImageView ivDanmu;
    @BindView(R.id.rl_loading)
    RelativeLayout rlLoading;
    @BindView(R.id.view_play)
    RelativeLayout viewPlay;
    @BindView(R.id.view_danmu)
    RelativeLayout viewDanmu;
    @BindView(R.id.view_full_screen)
    RelativeLayout viewFullScreen;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private float screenBrightness;
    private AudioManager audioManager;
    //是否全屏
    private boolean isFulled = false;
    private boolean isLiveing = true;
    private FragmentAdapter fragmentAdapter;


    @Override
    protected void init() {
        initIJKPlayer();
        initlistener();
        initAudioManager();
        initViewPager();
        requestData();
        KeepLive keepLive = new KeepLive();
        ThreadPoolManager.getInstance().getCachedThreadPool().execute(keepLive);
    }

    private void initViewPager() {
        List<Fragment>fragments=new ArrayList<>();
        fragments.add(LiveChatFragment.getInstance());
        fragments.add(AnchorFragment.getInstance());
        fragments.add(RankFragment.getInstance());
        List<String>titles=new ArrayList<>();
        titles.add("聊天");
        titles.add("主播");
        titles.add("排行榜");
        fragmentAdapter=new FragmentAdapter(getSupportFragmentManager(),fragments,titles);
        viewPager.setAdapter(fragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void initAudioManager() {
        //得到当前界面的装饰视图
        /*if(Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            //让应用主题内容占用系统状态栏的空间,注意:下面两个参数必须一起使用 stable 牢固的
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            //设置状态栏颜色为透明
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }*/
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        tvRoomName.setText(roomName);
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_live_room;
    }


    private void initIJKPlayer() {
        try {
            //加载so资源
            IjkMediaPlayer.loadLibrariesOnce(null);
            IjkMediaPlayer.native_profileBegin("libijkplayer.so");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void initlistener() {
        videoPlayer.setVideoPlayerListener(this);
        videoPlayer.setScrollListener(this);
        setVideoControlGone();
    }


    private void requestData() {
        HttpUtil.getLiveUrl(roomId, new MyObserver<LiveUrl>() {
            @Override
            protected void onRequestSuccess(LiveUrl data) {
                videoPlayer.pause();
                videoPlayer.setVideoPath(data.getHls_url());
            }

            @Override
            protected void onRequestError() {

            }

        });
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //屏幕方向为横向的时候
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            isFulled = true;
            setVideoViewScale(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            //屏幕方向为纵向的时候
            isFulled = false;
            setVideoViewScale(ViewGroup.LayoutParams.MATCH_PARENT, NumberUtil.dip2px(this, 200));
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        }
    }

    //改变视频布局宽高
    private void setVideoViewScale(int width, int height) {
        ViewGroup.LayoutParams layoutParams = flVideoPlayer.getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = height;
        flVideoPlayer.setLayoutParams(layoutParams);
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoPlayer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        videoPlayer.pause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        videoPlayer.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        IjkMediaPlayer.native_profileEnd();
        videoPlayer.release();
        videoPlayer = null;
        isLiveing = false;
        ThreadPoolManager.getInstance().getCachedThreadPool().shutdown();
    }

    @Override
    public void changeBrightness(float value) {
        //调节亮度
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        screenBrightness = attributes.screenBrightness;
        float index = value / NumberUtil.getScreenHeight(this);
        screenBrightness = screenBrightness + index;
        if (screenBrightness > 1.0f) {
            screenBrightness = 1.0f;
        } else if (screenBrightness < 0.01f) {
            screenBrightness = 0.01f;
        }
        attributes.screenBrightness = screenBrightness;
        getWindow().setAttributes(attributes);
        Log.i("screenBrightness", "screenBrightness: " + screenBrightness);
        viewControl.setVisibility(View.VISIBLE);
        circleProgressBar.setMaxProgress(1);
        tvControlNum.setText("当前亮度");
        circleProgressBar.setProgress(screenBrightness);
        videoPlayer.removeCallbacks(viewControlGoneRb);
        setViewControlGone();
    }

    @Override
    public void changeVolume(float value) {
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int current = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        int index = (int) (value / NumberUtil.getScreenHeight(this) * maxVolume * 3);
        int volume = Math.max(current + index, 0);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);
        Log.i("Volume", "Volume: " + volume);
        viewControl.setVisibility(View.VISIBLE);
        circleProgressBar.setMaxProgress(maxVolume);
        tvControlNum.setText("当前音量");
        circleProgressBar.setProgress(volume);
        videoPlayer.removeCallbacks(viewControlGoneRb);
        setViewControlGone();
    }


    private void setViewControlGone() {
        videoPlayer.postDelayed(viewControlGoneRb, 2000);
    }


    Runnable viewControlGoneRb = new Runnable() {
        @Override
        public void run() {
            viewControl.setVisibility(View.GONE);
        }
    };


    Runnable videoControlGoneRb = new Runnable() {
        @Override
        public void run() {
            rlVideoControl.setVisibility(View.GONE);
        }
    };


    private void setVideoControlGone() {
        videoPlayer.postDelayed(videoControlGoneRb, 2000);
    }

    @Override
    public void changeVideoViewPosition(float value) {

    }

    //滑动结束
    @Override
    public void scrollEnd() {

    }

    @Override
    public void onSingleTap() {
        if (rlVideoControl.getVisibility() == View.VISIBLE) {
            rlVideoControl.setVisibility(View.GONE);
            videoPlayer.removeCallbacks(videoControlGoneRb);
        } else {
            rlVideoControl.setVisibility(View.VISIBLE);
            videoPlayer.removeCallbacks(videoControlGoneRb);
            setVideoControlGone();
        }
    }

    @Override
    public void onBufferingUpdate(IMediaPlayer iMediaPlayer, int i) {

    }

    @Override
    public void onCompletion(IMediaPlayer iMediaPlayer) {

    }

    @Override
    public boolean onError(IMediaPlayer iMediaPlayer, int i, int i1) {
        return false;
    }

    @Override
    public boolean onInfo(IMediaPlayer iMediaPlayer, int what, int extra) {
        /*
        * int MEDIA_INFO_VIDEO_RENDERING_START = 3;//视频准备渲染
        int MEDIA_INFO_BUFFERING_START = 701;//开始缓冲
        int MEDIA_INFO_BUFFERING_END = 702;//缓冲结束
        int MEDIA_INFO_VIDEO_ROTATION_CHANGED = 10001;//视频选择信息
        int MEDIA_ERROR_SERVER_DIED = 100;//视频中断，一般是视频源异常或者不支持的视频类型。
        int MEDIA_ERROR_IJK_PLAYER = -10000,//一般是视频源有问题或者数据格式不支持，比如音频不是AAC之类的
        int MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK = 200;//数据错误没有有效的回收*/
        switch (what) {
            case IMediaPlayer.MEDIA_INFO_BUFFERING_START:
                rlLoading.setVisibility(View.VISIBLE);
                break;
            case IMediaPlayer.MEDIA_INFO_BUFFERING_END:
                rlLoading.setVisibility(View.GONE);
                break;
        }
        return false;
    }

    @Override
    public void onPrepared(IMediaPlayer iMediaPlayer) {
        videoPlayer.start();
    }

    @Override
    public void onSeekComplete(IMediaPlayer iMediaPlayer) {

    }

    @Override
    public void onVideoSizeChanged(IMediaPlayer iMediaPlayer, int i, int i1, int i2, int i3) {

    }


    @OnClick({R.id.view_back, R.id.view_play, R.id.view_full_screen, R.id.view_float_window, R.id.view_danmu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.view_back:
                if (isFulled) {
                    //退出全屏
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                } else {
                    finish();
                }
                break;
            case R.id.view_play:
                if (videoPlayer.isPlaying()) {
                    videoPlayer.pause();
                    ivLivePlay.setImageResource(R.mipmap.icon_pause);
                } else {
                    videoPlayer.start();
                    ivLivePlay.setImageResource(R.mipmap.icon_play);
                }
                break;
            case R.id.view_full_screen:
                if (isFulled) {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                } else {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                }
                break;
            case R.id.view_float_window:
                ToastUtil.showText(this, "悬浮窗");
                break;
            case R.id.view_danmu:
                ToastUtil.showText(this, "弹幕");
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (isFulled) {
            //退出全屏
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {
            super.onBackPressed();
        }
    }




    class KeepLive extends Thread {
        @Override
        public void run() {
            super.run();
            while (isLiveing) {
                try {
                    //睡眠2分钟
                    Thread.sleep(2 * 1000 * 60);
                    requestData();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
