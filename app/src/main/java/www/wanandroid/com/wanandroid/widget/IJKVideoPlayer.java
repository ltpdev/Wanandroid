package www.wanandroid.com.wanandroid.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;
/*自定义需求视频播放器*/
public class IJKVideoPlayer extends FrameLayout {
    /**
     * 由ijkplayer提供，用于播放视频，需要给他传入一个surfaceView
     */
    private IMediaPlayer mediaPlayer = null;
    /*视频文件地址*/
    private String mPath = "";
    private SurfaceView surfaceView;
    private VideoPlayerListener videoPlayerListener;
    private Context context;

    public IJKVideoPlayer(@NonNull Context context) {
        super(context);
        initVideoView(context);
    }

    public IJKVideoPlayer(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initVideoView(context);
    }

    public IJKVideoPlayer(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initVideoView(context);
    }

    private void initVideoView(Context context) {
        this.context = context;
        //获取焦点
        setFocusable(true);
    }

    /**
     * 设置视频地址。
     * 根据是否第一次播放视频，做不同的操作。
     *
     * @param path the path of the video.
     */
    public void setVideoPath(String path) {
        if (TextUtils.isEmpty(mPath)) {
            //如果是第一次播放视频，那就创建一个新的surfaceView
            mPath = path;
            createSurfaceView();
        } else {
            //否则就直接load
            mPath = path;
            load();
        }
    }


    //新建一个surfaceView
    private void createSurfaceView() {
        //生成一个新的surfaceView
        surfaceView = new SurfaceView(context);
        surfaceView.getHolder().addCallback(new LmnSurfaceView());
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, Gravity.CENTER);
        surfaceView.setLayoutParams(layoutParams);
        this.addView(surfaceView);
    }

    //加载视频
    private void load() {
        //每次都要重新创建IMediaPlayer
        createPlayer();
        try {
            mediaPlayer.setDataSource(mPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mediaPlayer.setDisplay(surfaceView.getHolder());
        //异步准备
        mediaPlayer.prepareAsync();
    }

    private void createPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.setDisplay(null);
            mediaPlayer.release();
        }
        IjkMediaPlayer ijkMediaPlayer = new IjkMediaPlayer();
        ijkMediaPlayer.native_setLogLevel(IjkMediaPlayer.IJK_LOG_DEBUG);
        //开启硬解码
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec", 1);
        mediaPlayer = ijkMediaPlayer;
        if (videoPlayerListener != null) {
            mediaPlayer.setOnPreparedListener(videoPlayerListener);
            mediaPlayer.setOnInfoListener(videoPlayerListener);
            mediaPlayer.setOnSeekCompleteListener(videoPlayerListener);
            mediaPlayer.setOnBufferingUpdateListener(videoPlayerListener);
            mediaPlayer.setOnErrorListener(videoPlayerListener);
        }
    }


    public void setVideoPlayerListener(VideoPlayerListener videoPlayerListener) {
        this.videoPlayerListener = videoPlayerListener;
        //???????
         /* if (mediaPlayer!=null){
              mediaPlayer.setOnPreparedListener(videoPlayerListener);
          }*/
    }

    /*开始播放*/
    public void start() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    //释放资源
    public void release() {
        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    //停止bof
    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    //暂停播放
    public void pause() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    //重置资源
    public void reset() {
        if (mediaPlayer != null) {
            mediaPlayer.reset();
        }
    }

    //获取视频总时长
    public long getDuration() {
        if (mediaPlayer != null) {
            return mediaPlayer.getDuration();
        } else {
            return 0;
        }
    }


    //获取视频当前时长
    public long getCurrentDuration() {
        if (mediaPlayer != null) {
            return mediaPlayer.getCurrentPosition();
        } else {
            return 0;
        }
    }

    //跳到某指定的时间段处播放
    public void seekTo(long position) {
        if (mediaPlayer != null) {
            mediaPlayer.seekTo(position);
        }
    }

    //判断视频是否在播放
    public boolean isPlaying() {
        if (mediaPlayer != null) {
            return mediaPlayer.isPlaying();
        }
        return false;
    }

    private class LmnSurfaceView implements SurfaceHolder.Callback {

        @Override
        public void surfaceCreated(SurfaceHolder holder) {

        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            //surfaceview创建成功后，加载视频
            load();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }
    }
}
