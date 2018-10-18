package www.wanandroid.com.wanandroid.service;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import www.wanandroid.com.wanandroid.service.bean.Banner;
import www.wanandroid.com.wanandroid.service.bean.HttpResult;
import www.wanandroid.com.wanandroid.service.bean.IndexArticle;
import www.wanandroid.com.wanandroid.service.bean.KnowledgeClassify;
import www.wanandroid.com.wanandroid.service.bean.KnowledgeSystem;
import www.wanandroid.com.wanandroid.service.bean.UserInfo;

public interface RetrofitService {
    //获取首页文章列表
    @GET("article/list/{page}/json")
    Observable<HttpResult<IndexArticle>> getIndexArticleList(@Path("page") int page);

    //获取首页banner
    @GET("banner/json")
    Observable<HttpResult<List<Banner>>> getIndexBanner();

    //收藏站内文章
    @POST("lg/collect/{id}/json")
    Observable<HttpResult> collectArticle(@Path("id") int id);

    //取消收藏(文章列表)
    @POST("lg/uncollect_originId/{id}/json")
    Observable<HttpResult> cancleCollect(@Path("id") int id);

    //用户登录
    @FormUrlEncoded
    @POST("user/login")
    Observable<HttpResult<UserInfo>> userLogin(@Field("username") String username, @Field("password") String password);

    //获取知识体系
    @GET("tree/json")
    Observable<HttpResult<List<KnowledgeSystem>>> getKnowledgeSystem();

    //获取单个知识体系列表
    @GET("article/list/{page}/json")
    Observable<HttpResult<KnowledgeClassify>> getKnowledgeClassifyList(@Path("page") int page, @Query("cid") int id);
}
