package www.wanandroid.com.wanandroid.service;

import android.graphics.RectF;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import www.wanandroid.com.wanandroid.constant.Constant;
import www.wanandroid.com.wanandroid.service.interceptor.AddCookiesInterceptor;
import www.wanandroid.com.wanandroid.service.interceptor.SaveCookiesInterceptor;

public class RetrofitClient {
    private static RetrofitClient instance;
    private Retrofit retrofit;

    private RetrofitClient(){
        retrofit=createRetrofit();
    }

    public static RetrofitClient getInstance() {
        if (instance==null){
            synchronized (RetrofitClient.class){
                if (instance==null){
                    instance=new RetrofitClient();
                }
            }
        }
        return instance;
    }

    private Retrofit createRetrofit() {
        OkHttpClient.Builder builder=new OkHttpClient.Builder().
                connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS).
                        addInterceptor(new AddCookiesInterceptor()).
                        addInterceptor(new SaveCookiesInterceptor());
        return new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(builder.build())
                .build();
    }


    public RetrofitService createRetrofitService(){
        return retrofit.create(RetrofitService.class);
    }


}
