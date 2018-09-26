package www.wanandroid.com.wanandroid.fragment;

import android.widget.Toast;

import www.wanandroid.com.wanandroid.R;

public class ProjectFragment extends BaseFragment{



    @Override
    protected void init() {
        Toast.makeText(getActivity(),"ProjectFragment init",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_project;
    }
}
