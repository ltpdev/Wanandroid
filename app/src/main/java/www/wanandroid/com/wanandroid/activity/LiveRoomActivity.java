package www.wanandroid.com.wanandroid.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

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
import www.wanandroid.com.wanandroid.widget.IJKVideoPlayer;
import www.wanandroid.com.wanandroid.widget.VideoPlayerListener;

@Route(path = Constant.ACTIVITY_URL_ROOM_LIVE)
public class LiveRoomActivity extends BaseActivity implements IJKVideoPlayer.ScrollListener,VideoPlayerListener{
    @BindView(R.id.videoPlayer)
    IJKVideoPlayer videoPlayer;
    @Autowired(name = Constant.LIVE_ROOM_ID)
    String roomId;
    private float screenBrightness;

    @Override
    protected void init() {
        initIJKPlayer();
        initlistener();
        requestData();
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
        }catch (Exception e){
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
        videoPlayer=null;
    }

    @Override
    public void changeBrightness(float value) {
        //调节亮度
        WindowManager.LayoutParams attributes=getWindow().getAttributes();
        screenBrightness=attributes.screenBrightness;
        float index=value/ NumberUtil.getScreenHeight(this);
        screenBrightness=screenBrightness+index;
        if (screenBrightness>1.0f){
            screenBrightness=1.0f;
        }else if (screenBrightness<0.01f){
            screenBrightness=0.01f;
        }
        attributes.screenBrightness=screenBrightness;
        getWindow().setAttributes(attributes);
        float xx=(screenBrightness/1);
        xx=Float.parseFloat(String.format("%.2f", xx));
        int radio= (int) (xx*100);
        Log.i("screenBrightness", "screenBrightness: "+radio+"%");
    }

    @Override
    public void changeVolume(float value) {

    }

    @Override
    public void changeVideoViewPosition(float value) {

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
}
