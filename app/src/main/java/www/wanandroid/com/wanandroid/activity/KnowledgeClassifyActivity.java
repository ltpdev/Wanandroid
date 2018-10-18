package www.wanandroid.com.wanandroid.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import www.wanandroid.com.wanandroid.R;
import www.wanandroid.com.wanandroid.adapter.FragmentAdapter;
import www.wanandroid.com.wanandroid.constant.Constant;
import www.wanandroid.com.wanandroid.fragment.KnowledgeListFragment;
import www.wanandroid.com.wanandroid.service.bean.KnowledgeSystem;


public class KnowledgeClassifyActivity extends BaseActivity {

    KnowledgeSystem knowledgeSystem;
    @BindView(R.id.tablayout)
    TabLayout tablayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    private FragmentAdapter fragmentAdapter;
    private List<String> titles = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void init() {
        knowledgeSystem = (KnowledgeSystem) getIntent().getSerializableExtra(Constant.KNOWLEDGE);
        if (knowledgeSystem != null) {
            getSupportActionBar().setTitle(knowledgeSystem.getName());
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initTablayoutAndViewPager();
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_knowledge_classify;
    }


    private void initTablayoutAndViewPager() {
        if (knowledgeSystem != null) {
            for (KnowledgeSystem.ChildrenBean childrenBean : knowledgeSystem.getChildren()) {
                titles.add(childrenBean.getName());
                fragments.add(KnowledgeListFragment.getInstance(childrenBean.getId()));
            }
            viewPager.setOffscreenPageLimit(knowledgeSystem.getChildren().size());
        }
        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), fragments, titles);
        viewPager.setAdapter(fragmentAdapter);
        tablayout.setupWithViewPager(viewPager);
    }


}
