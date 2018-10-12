package www.wanandroid.com.wanandroid.service.interceptor;

import android.util.Log;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import www.wanandroid.com.wanandroid.constant.Constant;
import www.wanandroid.com.wanandroid.utils.SpUtil;

public class ReadCookiesInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        HashSet<String> preferences = (HashSet) SpUtil.readStringSet(Constant.PREF_COOKIES);
        builder.addHeader("Content-type", "application/json; charset=utf-8");
        for (String cookie : preferences) {
            builder.addHeader("Cookie", cookie);
            Log.i("OkHttp", "Adding Header: " + cookie); // This is done so I know which headers are being added; this interceptor is used after the normal logging of OkHttp
        }
       // builder.addHeader("Cookie", "JSESSIONID=9EE9BBCA5C2E28B2EBD49E77B5BB4A78;loginUserName=lintaoping,loginUserPassword=123456");
        return chain.proceed(builder.build());
    }
}
