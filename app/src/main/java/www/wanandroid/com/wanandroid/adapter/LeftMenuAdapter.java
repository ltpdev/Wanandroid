package www.wanandroid.com.wanandroid.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import www.wanandroid.com.wanandroid.R;

public class LeftMenuAdapter extends BaseQuickAdapter<String,BaseViewHolder>{
    private int selectId;

    public LeftMenuAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
         helper.setText(R.id.tv_menu_name,item);
         if (selectId==helper.getAdapterPosition()){
             helper.setBackgroundColor(R.id.tv_menu_name,Color.WHITE);
             helper.setTextColor(R.id.tv_menu_name,mContext.getResources().getColor(R.color.colorPrimary));
         }else {
             helper.setTextColor(R.id.tv_menu_name,Color.GRAY);
             helper.setBackgroundColor(R.id.tv_menu_name, Color.TRANSPARENT);
         }
    }


    public void setSelectId(int selectId) {
        this.selectId = selectId;
        notifyDataSetChanged();
    }
}
