package www.wanandroid.com.wanandroid.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import www.wanandroid.com.wanandroid.R;
import www.wanandroid.com.wanandroid.entry.dao.SearchHistory;

public class SearchHistoryAdapter extends BaseQuickAdapter<SearchHistory,BaseViewHolder>{
    public SearchHistoryAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchHistory item) {
          helper.setText(R.id.tv_keyword,item.getKeyWord());
          helper.addOnClickListener(R.id.iv_del);
    }
}
