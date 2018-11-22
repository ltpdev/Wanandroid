package www.wanandroid.com.wanandroid.fragment;

import android.os.Bundle;

import www.wanandroid.com.wanandroid.R;
import www.wanandroid.com.wanandroid.constant.Constant;

public class AnchorFragment extends LazyLoadFragment{



    public static AnchorFragment getInstance() {
        AnchorFragment fragment = new AnchorFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setContentView() {
         return R.layout.fragment_anchor;
    }

    @Override
    protected void lazyLoad() {

    }
}
