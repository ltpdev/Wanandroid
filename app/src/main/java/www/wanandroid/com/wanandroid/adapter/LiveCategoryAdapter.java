package www.wanandroid.com.wanandroid.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import www.wanandroid.com.wanandroid.R;
import www.wanandroid.com.wanandroid.service.bean.LiveList;
import www.wanandroid.com.wanandroid.service.bean.Navigation;

public class LiveCategoryAdapter extends BaseQuickAdapter<LiveList, BaseViewHolder> {
    public LiveCategoryAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, LiveList item) {
        helper.setText(R.id.tv_room_name, item.getRoom_name());
        helper.setText(R.id.tv_nick_name, item.getNickname());
        helper.setText(R.id.tv_online, item.getHn() + "");
        Glide.with(mContext).load(item.getRoom_src()).into((ImageView) helper.getView(R.id.iv_live_room));
    }
}
