package www.wanandroid.com.wanandroid.adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.Random;

import www.wanandroid.com.wanandroid.R;
import www.wanandroid.com.wanandroid.service.bean.LiveList;
import www.wanandroid.com.wanandroid.service.bean.Navigation;
import www.wanandroid.com.wanandroid.utils.NumberUtil;

public class LiveCategoryAdapter extends BaseQuickAdapter<LiveList, BaseViewHolder> {
    public LiveCategoryAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, LiveList item) {
        helper.setText(R.id.tv_room_name, item.getRoom_name());
        helper.setText(R.id.tv_nick_name, item.getNickname());
        helper.setText(R.id.tv_online, item.getHn() + "");
        Random random=new Random();
        int height=NumberUtil.dip2px(mContext,180)+random.nextInt(NumberUtil.dip2px(mContext,50));
        Log.i("height",height+":px");
        ImageView imageView=helper.getView(R.id.iv_live_room);
        LinearLayout.LayoutParams layoutParams= (LinearLayout.LayoutParams) imageView.getLayoutParams();
        layoutParams.height=height;
        layoutParams.width=LinearLayout.LayoutParams.MATCH_PARENT;
        imageView.setLayoutParams(layoutParams);
        Glide.with(mContext).load(item.getRoom_src()).into(imageView);
    }
}
