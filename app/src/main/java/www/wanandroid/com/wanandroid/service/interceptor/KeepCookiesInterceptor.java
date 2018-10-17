package www.wanandroid.com.wanandroid.service.interceptor;

import android.util.Log;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Response;
import www.wanandroid.com.wanandroid.constant.Constant;
import www.wanandroid.com.wanandroid.utils.SpUtil;

public class KeepCookiesInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            HashSet<String> cookies = new HashSet<>();
            for (String header : originalResponse.headers("Set-Cookie")) {
                cookies.add(header);
                Log.i("OkHttp"," get Header"+header);
            }
            SpUtil.writeStringSet(Constant.PREF_COOKIES, cookies);
        }
        return originalResponse;
    }



}
