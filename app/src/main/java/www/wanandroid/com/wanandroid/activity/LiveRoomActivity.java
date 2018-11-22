package www.wanandroid.com.wanandroid.activity;

import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;

import butterknife.BindView;
import butterknife.ButterKnife;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;
import www.wanandroid.com.wanandroid.R;
import www.wanandroid.com.wanandroid.constant.Constant;
import www.wanandroid.com.wanandroid.observer.MyObserver;
import www.wanandroid.com.wanandroid.service.bean.LiveUrl;
import www.wanandroid.com.wanandroid.utils.HttpUtil;
import www.wanandroid.com.wanandroid.utils.NumberUtil;
import www.wanandroid.com.wanandroid.widget.CustomCircleProgressBar;
import www.wanandroid.com.wanandroid.widget.IJKVideoPlayer;
import www.wanandroid.com.wanandroid.widget.VideoPlayerListener;

@Route(path = Constant.ACTIVITY_URL_ROOM_LIVE)
public class LiveRoomActivity extends BaseActivity implements IJKVideoPlayer.ScrollListener, VideoPlayerListener {
    @BindView(R.id.videoPlayer)
    IJKVideoPlayer videoPlayer;
    @Autowired(name = Constant.LIVE_ROOM_ID)
    String roomId;
    @BindView(R.id.tv_control_num)
    TextView tvControlNum;
    @BindView(R.id.view_control)
    RelativeLayout viewControl;
    @BindView(R.id.circle_progressBar)
    CustomCircleProgressBar circleProgressBar;
    private float screenBrightness;
    private AudioManager audioManager;


    @Override
    protected void init() {
        initIJKPlayer();
        initlistener();
        initAudioManager();
        requestData();
    }

    private void initAudioManager() {
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
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
    }


    private void requestData() {
        HttpUtil.getLiveUrl(roomId, new MyObserver<LiveUrl>() {
            @Override
            protected void onRequestSuccess(LiveUrl data) {
                videoPlayer.setVideoPath(data.getHls_url());
            }

            @Override
            protected void onRequestError() {

            }

        });
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        IjkMediaPlayer.native_profileEnd();
        videoPlayer.release();
        videoPlayer = null;
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
      /*  float xx = (screenBrightness / 1);
        xx = Float.parseFloat(String.format("%.2f", xx));
        int radio = (int) (xx * 100);*/
        Log.i("screenBrightness", "screenBrightness: " + screenBrightness);
        viewControl.setVisibility(View.VISIBLE);
        circleProgressBar.setMaxProgress(1);
        tvControlNum.setText("当前亮度");
        circleProgressBar.setProgress(screenBrightness);
    }

    @Override
    public void changeVolume(float value) {
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int current = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        int index = (int) (value / NumberUtil.getScreenHeight(this) * maxVolume * 3);
        int volume = Math.max(current + index, 0);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);
      /*  float xx = (float) volume / maxVolume;
        xx = Float.parseFloat(String.format("%.2f", xx));
        int radio = (int) (xx * 100);
        if (radio > 100) {
            radio = 100;
        }*/
        Log.i("Volume", "Volume: " + volume);
        viewControl.setVisibility(View.VISIBLE);
        circleProgressBar.setMaxProgress(maxVolume);
        tvControlNum.setText("当前音量");
        circleProgressBar.setProgress(volume);
    }


    @Override
    public void changeVideoViewPosition(float value) {

    }

    //滑动结束
    @Override
    public void scrollEnd() {
        viewControl.setVisibility(View.GONE);
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
    public boolean onInfo(IMediaPlayer iMediaPlayer, int i, int i1) {
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
