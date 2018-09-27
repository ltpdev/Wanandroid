package www.wanandroid.com.wanandroid.service;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import www.wanandroid.com.wanandroid.service.bean.Banner;
import www.wanandroid.com.wanandroid.service.bean.HttpResult;
import www.wanandroid.com.wanandroid.service.bean.IndexArticle;

public interface RetrofitService {
    //获取首页文章列表
    @GET("article/list/{page}/json")
    Observable<HttpResult<IndexArticle>> getIndexArticleList(@Path("page") int page);

    //获取首页banner
    @GET("banner/json")
    Observable<HttpResult<List<Banner>>> getIndexBanner();
}
