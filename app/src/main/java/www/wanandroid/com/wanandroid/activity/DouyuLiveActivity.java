package www.wanandroid.com.wanandroid.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import www.wanandroid.com.wanandroid.R;
import www.wanandroid.com.wanandroid.adapter.FragmentAdapter;
import www.wanandroid.com.wanandroid.constant.Constant;
import www.wanandroid.com.wanandroid.fragment.LiveCategoryFragment;
import www.wanandroid.com.wanandroid.observer.MyObserver;
import www.wanandroid.com.wanandroid.service.bean.CategoryTitle;
import www.wanandroid.com.wanandroid.service.bean.LiveList;
import www.wanandroid.com.wanandroid.utils.HttpUtil;

@Route(path = Constant.ACTIVITY_URL_DOUYU_LIVE)
public class DouyuLiveActivity extends BaseActivity {
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.knowledge_toolbar)
    Toolbar knowledgeToolbar;
    private List<String> titles = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();
    private FragmentAdapter fragmentAdapter;

    @Override
    protected void init() {
        initListener();
        initData();

    }

    private void initListener() {
        knowledgeToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_douyu_live;
    }

    private void initData() {
        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), fragments, titles);
        viewPager.setAdapter(fragmentAdapter);
        tablayout.setupWithViewPager(viewPager);
        HttpUtil.getLiveTitle(new MyObserver<List<CategoryTitle>>() {
            @Override
            protected void onRequestSuccess(List<CategoryTitle> data) {
                 for (CategoryTitle categoryTitle:data){
                     fragments.add(LiveCategoryFragment.getInstance(categoryTitle.getTag_id()));
                     titles.add(categoryTitle.getTag_name());
                 }
                fragmentAdapter.notifyDataSetChanged();
            }

            @Override
            protected void onRequestError() {

            }
        });
    }

}
