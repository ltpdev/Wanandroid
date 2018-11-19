package www.wanandroid.com.wanandroid.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

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
public class LiveCategoryFragment extends LazyLoadFragment implements BaseQuickAdapter.OnItemClickListener,OnLoadMoreListener, OnRefreshListener{
    @BindView(R.id.rv_classify)
    RecyclerView rvClassify;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    private String tagId;
    private int offset=0;
    private LiveCategoryAdapter liveCategoryAdapter;
    private boolean isRefreshing=false;

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
        initListener();
        requestData();
    }

    private void initListener() {
        liveCategoryAdapter.setOnItemClickListener(this);
        smartRefreshLayout.setOnLoadMoreListener(this);
        smartRefreshLayout.setOnRefreshListener(this);
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
                if (data.size() > 0) {
                    if (isRefreshing) {
                        liveCategoryAdapter.getData().clear();
                    }
                    liveCategoryAdapter.addData(data);
                    if (isRefreshing) {
                        smartRefreshLayout.finishRefresh(true);
                    } else {
                        smartRefreshLayout.finishLoadMore(true);
                    }
                } else {
                    if (isRefreshing) {
                        smartRefreshLayout.finishRefresh(0,false);
                    } else {
                        smartRefreshLayout.finishLoadMore(0, false, true);
                    }
                }
            }

            @Override
            protected void onRequestError() {
                if (isRefreshing) {
                    smartRefreshLayout.finishRefresh(false);
                }else {
                    smartRefreshLayout.finishLoadMore(false);
                }
            }

        });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        isRefreshing = false;
        offset=liveCategoryAdapter.getData().size();
        requestData();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        isRefreshing = true;
        offset=0;
        requestData();
    }
}
