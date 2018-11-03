package www.wanandroid.com.wanandroid.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import www.wanandroid.com.wanandroid.R;
import www.wanandroid.com.wanandroid.service.bean.KnowledgeSystem;

public class KnowledgeSystemAdapter extends BaseQuickAdapter<KnowledgeSystem,BaseViewHolder>{

    public KnowledgeSystemAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, KnowledgeSystem item) {
         helper.setText(R.id.tv_knowledge_title,item.getName());
         StringBuffer stringBuffer=new StringBuffer();
         for (KnowledgeSystem.ChildrenBean childrenBean:item.getChildren()){
             stringBuffer.append("   ");
             stringBuffer.append(childrenBean.getName());
         }
        helper.setText(R.id.tv_knowledge_content,stringBuffer.toString());
    }


}
