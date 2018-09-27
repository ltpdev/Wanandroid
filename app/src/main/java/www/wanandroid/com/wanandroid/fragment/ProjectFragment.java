package www.wanandroid.com.wanandroid.fragment;

import android.widget.Toast;

import www.wanandroid.com.wanandroid.R;
import www.wanandroid.com.wanandroid.utils.ToastUtil;

public class ProjectFragment extends BaseFragment{



    @Override
    protected void init() {
        ToastUtil.showText(getActivity(),"ProjectFragment init");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_project;
    }
}
