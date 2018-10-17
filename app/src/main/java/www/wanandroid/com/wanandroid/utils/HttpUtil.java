package www.wanandroid.com.wanandroid.utils;

import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import www.wanandroid.com.wanandroid.service.RetrofitClient;
import www.wanandroid.com.wanandroid.service.bean.HttpResult;
import www.wanandroid.com.wanandroid.service.bean.IndexArticle;

/*网络请求工具*/
public class HttpUtil {
  //获取首页文章列表
    public static void getIndexArticleList(int page,Observer observer){
        subscribe(RetrofitClient.getInstance().createRetrofitService().getIndexArticleList(page),observer);
    }
    //获取首页Banner
    public static void getIndexBanner(Observer observer){
        subscribe(RetrofitClient.getInstance().createRetrofitService().getIndexBanner(),observer);
    }

    //收藏站内文章
    public static void collectArticle(int id,Observer observer){
        subscribe(RetrofitClient.getInstance().createRetrofitService().collectArticle(id),observer);
    }
    //用户登录
    public static void userLogin(String username,String password,Observer observer){
        subscribe(RetrofitClient.getInstance().createRetrofitService().userLogin(username,password),observer);
    }

    //取消收藏
    public static void cancleCollect(int id,Observer observer){
        subscribe(RetrofitClient.getInstance().createRetrofitService().cancleCollect(id),observer);
    }

    //订阅关系
    private static void subscribe(Observable observable,Observer observer){
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
    }


}
