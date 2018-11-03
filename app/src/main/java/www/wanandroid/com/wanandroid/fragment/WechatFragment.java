package www.wanandroid.com.wanandroid.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import www.wanandroid.com.wanandroid.R;
import www.wanandroid.com.wanandroid.adapter.FragmentAdapter;
import www.wanandroid.com.wanandroid.observer.MyObserver;
import www.wanandroid.com.wanandroid.service.bean.WxArticleChapters;
import www.wanandroid.com.wanandroid.utils.HttpUtil;

public class WechatFragment extends BaseFragment {
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
        HttpUtil.getWeChatArticleTabs(new MyObserver<List<WxArticleChapters>>() {
            @Override
            protected void onRequestSuccess(List<WxArticleChapters> data) {
                for (WxArticleChapters wxArticleChapters : data) {
                    titles.add(wxArticleChapters.getName());
                    fragments.add(KnowledgeListFragment.getInstance(wxArticleChapters.getId()));
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
        return R.layout.fragment_wechat;
    }
}
