package www.wanandroid.com.wanandroid.observer;

import com.alibaba.android.arouter.launcher.ARouter;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import www.wanandroid.com.wanandroid.activity.BaseActivity;
import www.wanandroid.com.wanandroid.constant.Constant;
import www.wanandroid.com.wanandroid.manager.ActivityManager;
import www.wanandroid.com.wanandroid.service.bean.HttpResult;
import www.wanandroid.com.wanandroid.utils.ToastUtil;

public abstract class MyObserver<T> implements Observer<HttpResult<T>>{
    private BaseActivity activity;
    @Override
    public void onSubscribe(Disposable d) {
        activity= (BaseActivity) ActivityManager.getInstance().currentActivity();
        activity.showLoadingDialog();
    }

    @Override
    public void onNext(HttpResult<T> httpResult) {
        if (httpResult.errorCode==0){
            onRequestSuccess(httpResult.data);
        }else {
            ToastUtil.showText(activity,httpResult.errorMsg);
            onRequestError();
            if (httpResult.errorCode== Constant.LOGIN_INVALID){
                 //登录
                ARouter.getInstance().build(Constant.ACTIVITY_URL_LOGIN)
                        .navigation();
            }
        }
    }

    @Override
    public void onError(Throwable e) {
        onRequestError();
        ToastUtil.showText(activity,e.getMessage());
        activity.closeLoadingDialog();
    }

    @Override
    public void onComplete() {
        activity.closeLoadingDialog();
    }

    protected abstract void onRequestSuccess(T data);
    protected abstract void onRequestError();
}
