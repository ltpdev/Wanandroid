package www.wanandroid.com.wanandroid.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import www.wanandroid.com.wanandroid.R;
import www.wanandroid.com.wanandroid.service.bean.IndexArticle;
import www.wanandroid.com.wanandroid.utils.StringUtils;

public class HomeArticleAdapter extends BaseMultiItemQuickAdapter<IndexArticle.DatasBean, BaseViewHolder> {


    public HomeArticleAdapter(List<IndexArticle.DatasBean> data) {
        super(data);
        addItemType(IndexArticle.DatasBean.TEXT, R.layout.item_home_text);
        addItemType(IndexArticle.DatasBean.IMG, R.layout.item_home_pic);
    }

    @Override
    protected void convert(BaseViewHolder helper, IndexArticle.DatasBean item) {
        ImageView ivTag = helper.getView(R.id.iv_tag);
        TextView tvAuthor = helper.getView(R.id.tv_author);
        if (item.isFresh()) {
            ivTag.setVisibility(View.VISIBLE);
        } else {
            ivTag.setVisibility(View.GONE);
        }
        tvAuthor.setText(item.getAuthor());
        helper.setText(R.id.tv_time, item.getNiceDate());
        helper.setText(R.id.tv_title, StringUtils.stripHtml(item.getTitle()));
        if (!TextUtils.isEmpty(item.getChapterName())){
            helper.setVisible(R.id.tv_tag,true);
            helper.setText(R.id.tv_tag, item.getChapterName());
        }else {
            helper.setVisible(R.id.tv_tag,false);
        }
        helper.addOnClickListener(R.id.iv_love);
        if (item.isCollect()){
            helper.setImageResource(R.id.iv_love,R.mipmap.love_red);
        }else {
            helper.setImageResource(R.id.iv_love,R.mipmap.love);
        }
        switch (helper.getItemViewType()) {
            case IndexArticle.DatasBean.TEXT:
                break;
            case IndexArticle.DatasBean.IMG:
                ImageView imageView = helper.getView(R.id.iv_icon);
                Glide.with(helper.itemView.getContext()).load(item.getEnvelopePic()).into(imageView);
                break;
        }
    }




}
