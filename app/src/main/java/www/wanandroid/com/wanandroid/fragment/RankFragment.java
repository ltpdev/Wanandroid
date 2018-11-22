package www.wanandroid.com.wanandroid.fragment;

import android.os.Bundle;

import www.wanandroid.com.wanandroid.R;

public class RankFragment extends LazyLoadFragment {
    public static RankFragment getInstance() {
        RankFragment fragment = new RankFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_rank;
    }

    @Override
    protected void lazyLoad() {

    }
}
