package www.wanandroid.com.wanandroid.utils;

import android.content.Context;

public class NumberUtil {
    public static int dip2px(Context context,float dip){
        float density=context.getResources().getDisplayMetrics().density;
        return (int) (dip*density+0.5f);
    }
    //px转dip
    public static float px2dip(Context context,int px){
        float density=context.getResources().getDisplayMetrics().density;
        return px/density;
    }
    //获取屏幕的宽
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }
    //获取屏幕的高
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }
}
