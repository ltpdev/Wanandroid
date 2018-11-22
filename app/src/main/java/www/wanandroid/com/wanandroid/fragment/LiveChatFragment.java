package www.wanandroid.com.wanandroid.fragment;

import android.os.Bundle;

import www.wanandroid.com.wanandroid.R;

public class LiveChatFragment extends LazyLoadFragment{

    public static LiveChatFragment getInstance() {
        LiveChatFragment fragment = new LiveChatFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int setContentView() {
         return R.layout.fragment_chat;
    }

    @Override
    protected void lazyLoad() {

    }
}
