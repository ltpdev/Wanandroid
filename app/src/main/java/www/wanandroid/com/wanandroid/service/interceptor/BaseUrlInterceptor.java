package www.wanandroid.com.wanandroid.service.interceptor;

import java.io.IOException;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import www.wanandroid.com.wanandroid.constant.Constant;

/*自定义设置baseUrl拦截器*/
public class BaseUrlInterceptor implements Interceptor{
    @Override
    public Response intercept(Chain chain) throws IOException {
        //获取原始的originlRequest
        Request originlRequest=chain.request();
        //获取老的url
        HttpUrl oldUrl=originlRequest.url();
        //获取originalRequest的创建者builder
        Request.Builder builder=originlRequest.newBuilder();
        //获取头部信息的集合
        List<String>urlNameList=originlRequest.headers("baseUrl");
        if (urlNameList!=null&&urlNameList.size()>0){
            //删除原有配置的值，就是namesAndValues集合里的值
            builder.removeHeader("baseUrl");
            //获取头部信息配置的value
            String urlName=urlNameList.get(0);
            HttpUrl baseURL=null;
            //根据头信息中配置的value,来匹配新的base_url地址
            if (urlName.equals(Constant.WANANDROID)){
                baseURL=HttpUrl.parse(Constant.BASE_URL);
            }else if (urlName.equals(Constant.DOUYU)){
                baseURL=HttpUrl.parse(Constant.DOUYU_URL);
            }else if (urlName.equals(Constant.GETLIVE)){
                baseURL=HttpUrl.parse(Constant.GETLIVE_URL);
            }
            //重新建新的httpUrl,需要重新设置的url部分
            HttpUrl newHttpUrl=oldUrl.newBuilder()
                    .scheme(baseURL.scheme())//http协议如：http或者https
                    .host(baseURL.host())//主机地址
                    .port(baseURL.port())//端口
                    .build();
            Request newRequest=builder.url(newHttpUrl).build();
            return chain.proceed(newRequest);
        }else {
            return chain.proceed(originlRequest);
        }
    }
}
