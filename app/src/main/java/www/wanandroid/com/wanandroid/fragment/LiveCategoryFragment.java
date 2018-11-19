package www.wanandroid.com.wanandroid.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import www.wanandroid.com.wanandroid.R;
import www.wanandroid.com.wanandroid.adapter.LiveCategoryAdapter;
import www.wanandroid.com.wanandroid.constant.Constant;
import www.wanandroid.com.wanandroid.observer.MyObserver;
import www.wanandroid.com.wanandroid.service.bean.LiveList;
import www.wanandroid.com.wanandroid.utils.HttpUtil;

/*直播分类Fragment*/
public class LiveCategoryFragment extends LazyLoadFragment {
    @BindView(R.id.rv_classify)
    RecyclerView rvClassify;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    private String tagId;
    private int offset=0;
    private LiveCategoryAdapter liveCategoryAdapter;

    public static LiveCategoryFragment getInstance(String tagId) {
        LiveCategoryFragment fragment = new LiveCategoryFragment();
        Bundle args = new Bundle();
        args.putString(Constant.LIVE_TAG_ID, tagId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_knowled_gelist;
    }

    @Override
    protected void lazyLoad() {
        tagId=getArguments().getString(Constant.LIVE_TAG_ID);
        initRecyclerView();
        requestData();
    }



    private void initRecyclerView() {
        liveCategoryAdapter=new LiveCategoryAdapter(R.layout.item_live_category);
        rvClassify.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        rvClassify.setAdapter(liveCategoryAdapter);
    }

    private void requestData() {
        HttpUtil.getLiveList(tagId, offset, 20, new MyObserver<List<LiveList>>() {


            @Override
            protected void onRequestSuccess(List<LiveList> data) {
                liveCategoryAdapter.addData(data);
            }

            @Override
            protected void onRequestError() {

            }

        });
    }

}
