package www.wanandroid.com.wanandroid.app;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;

public class App extends Application{

    private static App instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
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


    public static App getInst() {
        return instance;
    }
}
