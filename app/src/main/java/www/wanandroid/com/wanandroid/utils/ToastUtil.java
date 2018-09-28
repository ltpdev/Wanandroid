package www.wanandroid.com.wanandroid.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ToastUtil {
    private static Toast toast;

    public static void showText(Context context, String text) {
        if (toast == null) {
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            //设置吐司显示的位置
            toast.setGravity(Gravity.CENTER, 0, 0);
            //得到吐司的父布局
            LinearLayout linearLayout = (LinearLayout) toast.getView();
            //设置父布局背景形状
            linearLayout.setBackground(DrawableUtils.getGradientDrawable(Color.parseColor("#aa000000"), 16));
            //第一个孩子就是TextView了
            TextView tv = (TextView) linearLayout.getChildAt(0);
            tv.setTextSize(18);
            tv.setTextColor(Color.WHITE);
            tv.setGravity(Gravity.CENTER);
        } else {
            toast.setText(text);
        }
        toast.show();
    }
}
