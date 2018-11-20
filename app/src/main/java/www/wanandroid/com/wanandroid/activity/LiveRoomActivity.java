package www.wanandroid.com.wanandroid.activity;

import android.os.Bundle;

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
import www.wanandroid.com.wanandroid.widget.IJKVideoPlayer;
import www.wanandroid.com.wanandroid.widget.VideoPlayerListener;

@Route(path = Constant.ACTIVITY_URL_ROOM_LIVE)
public class LiveRoomActivity extends BaseActivity {
    @BindView(R.id.videoPlayer)
    IJKVideoPlayer videoPlayer;
    @Autowired(name = Constant.LIVE_ROOM_ID)
    String roomId;
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
           videoPlayer.setVideoPlayerListener(new VideoPlayerListener() {
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
           });
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

}
