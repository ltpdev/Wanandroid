package www.wanandroid.com.wanandroid.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;
import java.util.Random;

import www.wanandroid.com.wanandroid.R;
import www.wanandroid.com.wanandroid.constant.Constant;
import www.wanandroid.com.wanandroid.service.bean.Navigation;
import www.wanandroid.com.wanandroid.utils.NumberUtil;
import www.wanandroid.com.wanandroid.widget.FlowLayout;

public class ContentAdapter extends BaseQuickAdapter<Navigation, BaseViewHolder> {
    public ContentAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, Navigation item) {
        helper.setText(R.id.tv_menu_name, item.getName());
        FlowLayout flowLayout = helper.getView(R.id.flow_layout);
        int padding = NumberUtil.dip2px(mContext,10);
        for (final Navigation.ArticlesBean articlesBean : item.getArticles()) {
            TextView textView = new TextView(mContext);
            textView.setText(articlesBean.getTitle());
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            textView.setGravity(Gravity.CENTER);
            textView.setPadding(padding, padding, padding, padding);
            textView.setBackgroundColor(mContext.getResources().getColor(R.color.divider));
            Random random = new Random();
            int r = 30 + random.nextInt(210);
            int g = 30 + random.nextInt(210);
            int b = 30 + random.nextInt(210);
            textView.setTextColor(Color.rgb(r,g,b));
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ARouter.getInstance().build(Constant.ACTIVITY_URL_WEBVIEW).
                            withString(Constant.KEY_WEBVIEW, articlesBean.getLink())
                            .navigation();
                }
            });
            flowLayout.addView(textView);
        }
    }
}
