package www.wanandroid.com.wanandroid.utils;

import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.Query;
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

    //获取知识体系
    public static void getKnowledgeSystem(Observer observer){
        subscribe(RetrofitClient.getInstance().createRetrofitService().getKnowledgeSystem(),observer);
    }
    //获取单个知识体系列表
    public static void getKnowledgeClassifyList(int page,int id,Observer observer){
        subscribe(RetrofitClient.getInstance().createRetrofitService().getKnowledgeClassifyList(page,id),observer);
    }
    //获取微信公众号文章tabs
    public static void getWeChatArticleTabs(Observer observer){
        subscribe(RetrofitClient.getInstance().createRetrofitService().getWxArticleTabs(),observer);
    }
    //获取微信公众号文章列表
    public static void getWeChatArticles(int id,int page,Observer observer){
        subscribe(RetrofitClient.getInstance().createRetrofitService().getWxArticleList(id,page),observer);
    }
    //获取导航数据列表
    public static void getNavigationList(Observer observer){
        subscribe(RetrofitClient.getInstance().createRetrofitService().getNavigationList(),observer);
    }
    //获取项目分类
    public static void getProjectClassification(Observer observer){
        subscribe(RetrofitClient.getInstance().createRetrofitService().getProjectClassification(),observer);
    }
    //获取项目分类
    public static void getProjectList(int page, int cid,Observer observer){
        subscribe(RetrofitClient.getInstance().createRetrofitService().getProjectList(page,cid),observer);
    }
    //获取热门搜索
    public static void getHotSearchWord(Observer observer){
        subscribe(RetrofitClient.getInstance().createRetrofitService().getHotSearchWord(),observer);
    }
    //搜索文章
    public static void searchArticle(int page,String keyWord,Observer observer){
        subscribe(RetrofitClient.getInstance().createRetrofitService().searchArticle(page,keyWord),observer);
    }

    //获取斗鱼直播列表
    public static void getLiveList(String tagId,int offset,int limit,Observer observer){
        subscribe(RetrofitClient.getInstance().createRetrofitService().getLiveList(tagId,offset,limit),observer);
    }
    //订阅关系
    private static void subscribe(Observable observable,Observer observer){
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(observer);
    }


}
