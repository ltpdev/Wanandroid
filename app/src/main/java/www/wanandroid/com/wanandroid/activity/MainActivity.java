package www.wanandroid.com.wanandroid.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import www.wanandroid.com.wanandroid.R;
import www.wanandroid.com.wanandroid.factory.FragmentFactory;

public class MainActivity extends BaseActivity {
    @BindView(R.id.fl_container)
    FrameLayout flContainer;
    @BindView(R.id.bottomBar)
    BottomBar bottomBar;
    private FragmentManager fragmentManager;

    @Override
    protected void init() {
        fragmentManager = getSupportFragmentManager();
        initListener();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    private void initListener() {
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                FragmentTransaction transaction=fragmentManager.beginTransaction();
                transaction.replace(R.id.fl_container, FragmentFactory.getInstance().getFragment(tabId));
                transaction.commit();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FragmentFactory.getInstance().destory();
    }
}
