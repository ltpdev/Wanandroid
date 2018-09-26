package www.wanandroid.com.wanandroid.service;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import www.wanandroid.com.wanandroid.service.bean.IndexArticle;

public interface RetrofitService {
  //获取首页文章列表
    @GET("article/list/{page}/json")
    Observable<IndexArticle>getIndexArticleList(@Path("page") int page);

}
