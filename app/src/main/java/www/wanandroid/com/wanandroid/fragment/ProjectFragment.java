package www.wanandroid.com.wanandroid.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import www.wanandroid.com.wanandroid.R;
import www.wanandroid.com.wanandroid.adapter.FragmentAdapter;
import www.wanandroid.com.wanandroid.constant.Constant;
import www.wanandroid.com.wanandroid.observer.MyObserver;
import www.wanandroid.com.wanandroid.service.bean.ProjectClassification;
import www.wanandroid.com.wanandroid.service.bean.WxArticleChapters;
import www.wanandroid.com.wanandroid.utils.HttpUtil;
import www.wanandroid.com.wanandroid.utils.ToastUtil;

public class ProjectFragment extends BaseFragment {
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private FragmentAdapter fragmentAdapter;
    private List<String> titles = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();



    @Override
    protected void init() {
        initTablayoutAndViewPager();
    }


    private void initTablayoutAndViewPager() {
        HttpUtil.getProjectClassification(new MyObserver<List<ProjectClassification>>() {
            @Override
            protected void onRequestSuccess(List<ProjectClassification> data) {
                for (ProjectClassification projectClassification : data) {
                    titles.add(projectClassification.getName());
                    fragments.add(ProjectListFragment.getInstance(projectClassification.getId()));
                }
                viewPager.setOffscreenPageLimit(data.size());
                fragmentAdapter = new FragmentAdapter(getChildFragmentManager(), fragments, titles);
                viewPager.setAdapter(fragmentAdapter);
                tablayout.setupWithViewPager(viewPager);
            }

            @Override
            protected void onRequestError() {

            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_project;
    }







}
