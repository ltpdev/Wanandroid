package www.wanandroid.com.wanandroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import www.wanandroid.com.wanandroid.R;
import www.wanandroid.com.wanandroid.constant.Constant;
import www.wanandroid.com.wanandroid.event.UpEvent;
import www.wanandroid.com.wanandroid.factory.FragmentFactory;
import www.wanandroid.com.wanandroid.manager.ActivityManager;
import www.wanandroid.com.wanandroid.utils.ToastUtil;

@Route(path = Constant.ACTIVITY_URL_MAIN)
public class MainActivity extends BaseActivity {
    @BindView(R.id.fl_container)
    FrameLayout flContainer;
    @BindView(R.id.bottomBar)
    BottomBar bottomBar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawerLayout)
    DrawerLayout drawerLayout;
    @BindView(R.id.fb)
    FloatingActionButton fb;
    private FragmentManager fragmentManager;
    private long startTime;

    @Override
    protected void init() {
        initToolBar();
        initActionBarDrawerToggle();
        fragmentManager = getSupportFragmentManager();
        initListener();
    }


    private void initActionBarDrawerToggle() {
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    private void initToolBar() {
        toolbar.setNavigationIcon(R.mipmap.menu);
        toolbar.inflateMenu(R.menu.menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_search:
                        ToastUtil.showText(MainActivity.this, "搜索");
                        break;
                }
                return true;
            }
        });
        //点击左边返回按钮监听事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    private void initListener() {
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                switch (tabId) {
                    case R.id.tab_home:
                        toolbar.setTitle("玩Android");
                        break;
                    case R.id.tab_sys:
                        toolbar.setTitle("知识体系");
                        break;
                    case R.id.tab_navigation:
                        toolbar.setTitle("导航");
                        break;
                    case R.id.tab_project:
                        toolbar.setTitle("项目");
                        break;
                }
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fl_container, FragmentFactory.getInstance().getFragment(tabId));
                transaction.commit();
            }
        });
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new UpEvent(true));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FragmentFactory.getInstance().destory();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - startTime) > 3000) {
                ToastUtil.showText(this, "再按一次退出程序");
                startTime = System.currentTimeMillis();
            } else {
                ActivityManager.getInstance().finishActivity(this);
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }





}
