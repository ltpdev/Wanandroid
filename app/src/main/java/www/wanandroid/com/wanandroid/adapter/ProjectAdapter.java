package www.wanandroid.com.wanandroid.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import www.wanandroid.com.wanandroid.R;
import www.wanandroid.com.wanandroid.service.bean.Project;

public class ProjectAdapter extends BaseQuickAdapter<Project.DatasBean,BaseViewHolder>{
    public ProjectAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, Project.DatasBean item) {
        helper.setText(R.id.tv_author, item.getAuthor());
        helper.setText(R.id.tv_time, item.getNiceDate());
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_desc, item.getDesc());
        helper.addOnClickListener(R.id.iv_love);
        Glide.with(mContext).load(item.getEnvelopePic()).into((ImageView) helper.getView(R.id.iv_pic));
        if (item.isCollect()){
            helper.setImageResource(R.id.iv_love,R.mipmap.love_red);
        }else {
            helper.setImageResource(R.id.iv_love,R.mipmap.love);
        }
    }
}
