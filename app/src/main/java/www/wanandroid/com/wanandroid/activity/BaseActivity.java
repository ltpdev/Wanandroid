package www.wanandroid.com.wanandroid.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.zhy.autolayout.AutoLayoutActivity;

import butterknife.ButterKnife;
import www.wanandroid.com.wanandroid.R;
import www.wanandroid.com.wanandroid.fragment.LoadingDialog;
import www.wanandroid.com.wanandroid.manager.ActivityManager;


public abstract class BaseActivity extends AutoLayoutActivity{
    private LoadingDialog loadingDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        ARouter.getInstance().inject(this);
        ActivityManager.getInstance().addActivity(this);
        ButterKnife.bind(this);
        init();
    }

    protected abstract void init();

    protected abstract int getLayoutResId();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getInstance().finishActivity(this);
    }


    //显示加载对话框
    public void showLoadingDialog() {
        if (loadingDialog == null) {
            loadingDialog = LoadingDialog.newInstance();
        }
        if (!loadingDialog.isAdded()){
            loadingDialog.startAnimation();
            loadingDialog.show(getSupportFragmentManager(), "loadingDialog");
        }
    }
    //关闭加载对话框
    public void closeLoadingDialog() {
        if (loadingDialog != null && loadingDialog.getFragmentManager() != null) {
            loadingDialog.dismiss();
            loadingDialog.stopAnimation();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
