package www.wanandroid.com.wanandroid.service.interceptor;

import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import www.wanandroid.com.wanandroid.utils.SpUtil;

public class AddCookiesInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request=chain.request();
        Request.Builder builder=request.newBuilder();
        String cookie = getCookie(request.url().toString(), request.url().host());
        Log.i("AddCookiesInterceptor","intercept");
        if (!TextUtils.isEmpty(cookie)) {
            builder.addHeader("Cookie", cookie);
            Log.i("AddCookiesInterceptor",cookie);
        }
        return chain.proceed(builder.build());
    }

    private String getCookie(String url, String domain) {
        if (!TextUtils.isEmpty(url)) {
            return SpUtil.readString(url);
        }
        if (!TextUtils.isEmpty(domain)) {
            return SpUtil.readString(domain);
        }
        return null;
    }
}
