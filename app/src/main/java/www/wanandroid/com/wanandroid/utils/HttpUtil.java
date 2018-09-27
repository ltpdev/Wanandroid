package www.wanandroid.com.wanandroid.utils;

import android.util.Log;

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
        RetrofitClient.getInstance().createRetrofitService().getIndexArticleList(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
    }
    //获取首页Banner
    public static void getIndexBanner(Observer observer){
        RetrofitClient.getInstance().createRetrofitService().getIndexBanner()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
    }
}
