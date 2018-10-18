package www.wanandroid.com.wanandroid.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import www.wanandroid.com.wanandroid.R;
import www.wanandroid.com.wanandroid.service.bean.KnowledgeClassify;

public class KnowledgeClassifyAdapter extends BaseQuickAdapter<KnowledgeClassify.DatasBean,BaseViewHolder>{
    public KnowledgeClassifyAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, KnowledgeClassify.DatasBean item) {
        helper.setText(R.id.tv_author, item.getAuthor());
        helper.setText(R.id.tv_time, item.getNiceDate());
        helper.setText(R.id.tv_title, item.getTitle());
        helper.setText(R.id.tv_tag, item.getChapterName());
        helper.addOnClickListener(R.id.iv_love);
        if (item.isCollect()){
            helper.setImageResource(R.id.iv_love,R.mipmap.love_red);
        }else {
            helper.setImageResource(R.id.iv_love,R.mipmap.love);
        }
    }
}
