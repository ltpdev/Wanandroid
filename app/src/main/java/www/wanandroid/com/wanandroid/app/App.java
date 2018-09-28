package www.wanandroid.com.wanandroid.app;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;

public class App extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        initARouter();
    }

    private void initARouter() {
        ARouter.openLog();
        ARouter.openDebug();
        ARouter.init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ARouter.getInstance().destroy();
    }
}
