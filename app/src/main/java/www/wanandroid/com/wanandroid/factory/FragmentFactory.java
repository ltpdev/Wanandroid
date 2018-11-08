package www.wanandroid.com.wanandroid.factory;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import www.wanandroid.com.wanandroid.R;
import www.wanandroid.com.wanandroid.fragment.HomeFragment;
import www.wanandroid.com.wanandroid.fragment.NavigationFragment;
import www.wanandroid.com.wanandroid.fragment.ProjectFragment;
import www.wanandroid.com.wanandroid.fragment.SystemFragment;
import www.wanandroid.com.wanandroid.fragment.WechatFragment;

public class FragmentFactory {
    private static FragmentFactory instance;
    private HomeFragment homeFragment;
    private SystemFragment systemFragment;
    private NavigationFragment navigationFragment;
    private ProjectFragment projectFragment;
    private WechatFragment wechatFragment;

    public static FragmentFactory getInstance() {
        if (instance == null) {
            synchronized (FragmentFactory.class) {
                if (instance == null) {
                    instance = new FragmentFactory();
                }
            }
        }
        return instance;
    }

    public Fragment getHomeFragment() {
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
        }
        return homeFragment;
    }

    public Fragment getSystemFragment() {
        if (systemFragment == null) {
            systemFragment = new SystemFragment();
        }
        return systemFragment;
    }

    public Fragment getNavigationFragment() {
        if (navigationFragment == null) {
            navigationFragment = new NavigationFragment();
        }
        return navigationFragment;
    }


    public Fragment getProjectFragment() {
        if (projectFragment == null) {
            projectFragment = new ProjectFragment();
        }
        return projectFragment;
    }

    public Fragment getWechatFragment() {
        if (wechatFragment == null) {
            wechatFragment = new WechatFragment();
        }
        return wechatFragment;
    }


    public void destory() {
        homeFragment = null;
        systemFragment = null;
        navigationFragment = null;
        projectFragment = null;
        wechatFragment=null;
    }


    /**
     * 隐藏所有的fragment
     */
    public void hideAllFragment(FragmentTransaction fragmentTransaction) {
        if (FragmentFactory.getInstance().getHomeFragment().isAdded()) {
            fragmentTransaction.hide(FragmentFactory.getInstance().getHomeFragment());
        }
        if (FragmentFactory.getInstance().getSystemFragment().isAdded()) {
            fragmentTransaction.hide(FragmentFactory.getInstance().getSystemFragment());
        }
        if (FragmentFactory.getInstance().getWechatFragment().isAdded()) {
            fragmentTransaction.hide(FragmentFactory.getInstance().getWechatFragment());
        }
        if (FragmentFactory.getInstance().getNavigationFragment().isAdded()) {
            fragmentTransaction.hide(FragmentFactory.getInstance().getNavigationFragment());
        }
        if (FragmentFactory.getInstance().getProjectFragment().isAdded()) {
            fragmentTransaction.hide(FragmentFactory.getInstance().getProjectFragment());
        }
    }


    public Fragment getFragment(int tabId) {
        switch (tabId) {
            case R.id.tab_home:
                return getHomeFragment();
            case R.id.tab_sys:
                return getSystemFragment();
            case R.id.tab_navigation:
                return getNavigationFragment();
            case R.id.tab_project:
                return getProjectFragment();
            case R.id.tab_wechat:
                return getWechatFragment();
            default:
                return null;
        }
    }
}
