package www.wanandroid.com.wanandroid.service.interceptor;

import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import www.wanandroid.com.wanandroid.utils.SpUtil;

public class SaveCookiesInterceptor implements Interceptor{
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request=chain.request();
        Response response=chain.proceed(request);
        Log.i("SaveCookiesInterceptor","intercept");
        //set-cookie可能为多个
        if (!response.headers("set-cookie").isEmpty()){
            List<String>cookies=response.headers("set-cookie");
            String cookie=encodeCookie(cookies);
            Log.i("SaveCookiesInterceptor",cookie);
            saveCookie(request.url().toString(),request.url().host(),cookie);
        }

        return response;
    }
    /**
     * 保存cookie到本地，这里我们分别为该url和host设置相同的cookie，其中host可选
     * 这样能使得该cookie的应用范围更广
     */
    private void saveCookie(String url, String domain, String cookies) {
         if (TextUtils.isEmpty(url)){
             throw new NullPointerException("url is null.");
         }else {
          /*   if (TextUtils.isEmpty(SpUtil.readString(url))){
                 SpUtil.writeString(url,cookies);
             }*/
             SpUtil.writeString(url,cookies);
         }

        /* if (!TextUtils.isEmpty(domain)){
             if (TextUtils.isEmpty(SpUtil.readString(domain))){
                 SpUtil.writeString(domain,cookies);
             }
         }*/
    }

    /**
     * 整合cookie为唯一字符串
     */
    private String encodeCookie(List<String> cookies) {
        StringBuilder sb=new StringBuilder();
        Set<String>set=new HashSet<>();
        for (String cookie:cookies){
            String[]arr=cookie.split(";");
            for (String s:arr){
                if (set.contains(s)){
                    continue;
                }
                set.add(s);
            }
        }

        for (String cookie:set){
            sb.append(cookie).append(";");
        }
        int last=sb.lastIndexOf(";");
        if (sb.length()-1==last){
            sb.deleteCharAt(last);
        }
        return sb.toString();
    }
}
