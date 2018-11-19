package www.wanandroid.com.wanandroid.service;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import www.wanandroid.com.wanandroid.service.bean.Banner;
import www.wanandroid.com.wanandroid.service.bean.CategoryTitle;
import www.wanandroid.com.wanandroid.service.bean.HotKey;
import www.wanandroid.com.wanandroid.service.bean.HttpResult;
import www.wanandroid.com.wanandroid.service.bean.IndexArticle;
import www.wanandroid.com.wanandroid.service.bean.KnowledgeClassify;
import www.wanandroid.com.wanandroid.service.bean.KnowledgeSystem;
import www.wanandroid.com.wanandroid.service.bean.LiveList;
import www.wanandroid.com.wanandroid.service.bean.Navigation;
import www.wanandroid.com.wanandroid.service.bean.Project;
import www.wanandroid.com.wanandroid.service.bean.ProjectClassification;
import www.wanandroid.com.wanandroid.service.bean.UserInfo;
import www.wanandroid.com.wanandroid.service.bean.WxArticle;
import www.wanandroid.com.wanandroid.service.bean.WxArticleChapters;

public interface RetrofitService {
    //获取首页文章列表
    @Headers({"baseUrl:normal"})
    @GET("article/list/{page}/json")
    Observable<HttpResult<IndexArticle>> getIndexArticleList(@Path("page") int page);

    //获取首页banner
    @Headers({"baseUrl:normal"})
    @GET("banner/json")
    Observable<HttpResult<List<Banner>>> getIndexBanner();

    //收藏站内文章
    @Headers({"baseUrl:normal"})
    @POST("lg/collect/{id}/json")
    Observable<HttpResult> collectArticle(@Path("id") int id);

    //取消收藏(文章列表)
    @Headers({"baseUrl:normal"})
    @POST("lg/uncollect_originId/{id}/json")
    Observable<HttpResult> cancleCollect(@Path("id") int id);

    //用户登录
    @Headers({"baseUrl:normal"})
    @FormUrlEncoded
    @POST("user/login")
    Observable<HttpResult<UserInfo>> userLogin(@Field("username") String username, @Field("password") String password);

    //获取知识体系
    @Headers({"baseUrl:normal"})
    @GET("tree/json")
    Observable<HttpResult<List<KnowledgeSystem>>> getKnowledgeSystem();

    //获取单个知识体系列表
    @Headers({"baseUrl:normal"})
    @GET("article/list/{page}/json")
    Observable<HttpResult<KnowledgeClassify>> getKnowledgeClassifyList(@Path("page") int page, @Query("cid") int id);

    //获取公众号列表tabs
    @Headers({"baseUrl:normal"})
    @GET("wxarticle/chapters/json")
    Observable<HttpResult<List<WxArticleChapters>>> getWxArticleTabs();

    //获取公众号列表
    @Headers({"baseUrl:normal"})
    @GET("wxarticle/list/{id}/{page}/json")
    Observable<HttpResult<WxArticle>> getWxArticleList(@Path("id") int id, @Path("page") int page);

    //获取导航数据
    @Headers({"baseUrl:normal"})
    @GET("navi/json")
    Observable<HttpResult<List<Navigation>>> getNavigationList();

    //获取项目分类
    @Headers({"baseUrl:normal"})
    @GET("project/tree/json")
    Observable<HttpResult<List<ProjectClassification>>> getProjectClassification();

    //获取项目具体列表
    @Headers({"baseUrl:normal"})
    @GET("project/list/{page}/json")
    Observable<HttpResult<Project>> getProjectList(@Path("page") int page, @Query("cid") int cid);

    //获取热门搜索 http://www.wanandroid.com/hotkey/json
    @Headers({"baseUrl:normal"})
    @GET("hotkey/json")
    Observable<HttpResult<List<HotKey>>> getHotSearchWord();

    //搜索
    @Headers({"baseUrl:normal"})
    @FormUrlEncoded
    @POST("article/query/{page}/json")
    Observable<HttpResult<IndexArticle>> searchArticle(@Path("page") int page, @Field("k") String keyWord);

    /**
     * 斗鱼 直播列表
     */
    @Headers({"baseUrl:douyu"})
    @GET("/api/v1/live/{tag_id}")
    Observable<HttpResult<List<LiveList>>> getLiveList(@Path("tag_id") String tagId,  @Query("offset") int offset,  @Query("limit") int limit);

    /**
     * 斗鱼 直播标题列表
     */
    @Headers({"baseUrl:douyu"})
    @GET("/api/v1/getColumnDetail")
    Observable<HttpResult<List<CategoryTitle>>> getLiveTitle(@QueryMap Map<String, String> params);
}
