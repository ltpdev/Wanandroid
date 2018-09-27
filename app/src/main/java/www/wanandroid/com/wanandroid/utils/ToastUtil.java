package www.wanandroid.com.wanandroid.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ToastUtil {
    private static Toast toast;
    public static void showText(Context context,String text){
        if (toast==null){
            toast=Toast.makeText(context,text,Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            LinearLayout linearLayout= (LinearLayout) toast.getView();
            TextView tv= (TextView) linearLayout.getChildAt(0);
            tv.setTextSize(18);
            linearLayout.setBackground(DrawableUtils.getGradientDrawable(Color.parseColor("#aa000000"),16));
            tv.setTextColor(Color.WHITE);
            tv.setGravity(Gravity.CENTER);
        }else {
            toast.setText(text);
        }
        toast.show();
    }
}
