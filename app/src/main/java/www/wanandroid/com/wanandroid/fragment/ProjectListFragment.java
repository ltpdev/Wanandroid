package www.wanandroid.com.wanandroid.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import www.wanandroid.com.wanandroid.R;
import www.wanandroid.com.wanandroid.adapter.KnowledgeClassifyAdapter;
import www.wanandroid.com.wanandroid.adapter.ProjectAdapter;
import www.wanandroid.com.wanandroid.constant.Constant;
import www.wanandroid.com.wanandroid.event.RefreshEvent;
import www.wanandroid.com.wanandroid.event.UpEvent;
import www.wanandroid.com.wanandroid.observer.MyObserver;
import www.wanandroid.com.wanandroid.service.bean.KnowledgeClassify;
import www.wanandroid.com.wanandroid.service.bean.Project;
import www.wanandroid.com.wanandroid.utils.HttpUtil;
import www.wanandroid.com.wanandroid.utils.ToastUtil;

public class ProjectListFragment extends LazyLoadFragment implements BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener, OnLoadMoreListener, OnRefreshListener{
    @BindView(R.id.rv_classify)
    RecyclerView rvClassify;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    private int cid;
    private ProjectAdapter projectAdapter;
    private int page = 0;
    private boolean isRefreshing=false;

    public static ProjectListFragment getInstance(int cid) {
        ProjectListFragment fragment = new ProjectListFragment();
        Bundle args = new Bundle();
        args.putInt(Constant.PROJECT_CID, cid);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_knowled_gelist;
    }

    @Override
    protected void lazyLoad() {
        cid = getArguments().getInt(Constant.PROJECT_CID);
        Log.i("cid", cid + "");
        initRecyclerView();
        requestData();
    }





    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpEvent(UpEvent msg) {
        if (isVisible() && msg.isUp()) {
            rvClassify.smoothScrollToPosition(0);
        }
    }







    private void initRecyclerView() {
        rvClassify.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvClassify.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        projectAdapter = new ProjectAdapter(R.layout.item_project);
        projectAdapter.setOnItemClickListener(this);
        projectAdapter.setOnItemChildClickListener(this);
        smartRefreshLayout.setOnLoadMoreListener(this);
        smartRefreshLayout.setOnRefreshListener(this);
        rvClassify.setAdapter(projectAdapter);
    }

    private void requestData() {
        HttpUtil.getProjectList(page, cid, new MyObserver<Project>() {
            @Override
            protected void onRequestSuccess(Project data) {
                if (data.getDatas().size() > 0) {
                    if (isRefreshing) {
                        projectAdapter.getData().clear();
                    }
                    projectAdapter.addData(data.getDatas());
                    page++;
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
        String link = ((ProjectAdapter) adapter).getData().get(position).getLink();
        ARouter.getInstance().build(Constant.ACTIVITY_URL_WEBVIEW).
                withString(Constant.KEY_WEBVIEW, link)
                .navigation();
    }


    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.iv_love:
                int id = ((ProjectAdapter) adapter).getData().get(position).getId();
                boolean isCollect = ((ProjectAdapter) adapter).getData().get(position).isCollect();
                if (isCollect) {
                    unCollectArticle(id, position);
                } else {
                    collectArticle(id, position);
                }
                break;
        }
    }


    //取消收藏文章
    private void unCollectArticle(int id, final int position) {
        HttpUtil.cancleCollect(id, new MyObserver() {
            @Override
            protected void onRequestSuccess(Object data) {
                ToastUtil.showText(getActivity(), "取消收藏成功");
                projectAdapter.getData().get(position).setCollect(false);
                projectAdapter.notifyDataSetChanged();
            }

            @Override
            protected void onRequestError() {

            }
        });

    }

    //收藏文章
    private void collectArticle(int id, final int position) {
        HttpUtil.collectArticle(id, new MyObserver() {
            @Override
            protected void onRequestSuccess(Object data) {
                ToastUtil.showText(getActivity(), "收藏成功");
                projectAdapter.getData().get(position).setCollect(true);
                projectAdapter.notifyDataSetChanged();
            }

            @Override
            protected void onRequestError() {

            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshEvent(RefreshEvent event) {
        if (event.isRefreshing()) {
            smartRefreshLayout.autoRefresh();
        }
    }


    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        isRefreshing = false;
        requestData();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        isRefreshing = true;
        page=0;
        requestData();
    }
}
