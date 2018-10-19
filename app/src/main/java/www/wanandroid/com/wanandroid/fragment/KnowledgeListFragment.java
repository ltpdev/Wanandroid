package www.wanandroid.com.wanandroid.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import butterknife.ButterKnife;
import butterknife.Unbinder;
import www.wanandroid.com.wanandroid.R;
import www.wanandroid.com.wanandroid.adapter.KnowledgeClassifyAdapter;
import www.wanandroid.com.wanandroid.constant.Constant;
import www.wanandroid.com.wanandroid.event.RefreshEvent;
import www.wanandroid.com.wanandroid.observer.MyObserver;
import www.wanandroid.com.wanandroid.service.bean.KnowledgeClassify;
import www.wanandroid.com.wanandroid.utils.HttpUtil;
import www.wanandroid.com.wanandroid.utils.ToastUtil;

public class KnowledgeListFragment extends LazyLoadFragment implements BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener, OnLoadMoreListener, OnRefreshListener{
    @BindView(R.id.rv_classify)
    RecyclerView rvClassify;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    private int cid;
    private KnowledgeClassifyAdapter knowledgeClassifyAdapter;
    private int page = 0;
    private boolean isRefreshing=false;

    public static KnowledgeListFragment getInstance(int cid) {
        KnowledgeListFragment fragment = new KnowledgeListFragment();
        Bundle args = new Bundle();
        args.putInt(Constant.KNOWLEDGE_CID, cid);
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
        cid = getArguments().getInt(Constant.KNOWLEDGE_CID);
        Log.i("cid", cid + "");
        initRecyclerView();
        requestData();
    }


    private void initRecyclerView() {
        rvClassify.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvClassify.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        knowledgeClassifyAdapter = new KnowledgeClassifyAdapter(R.layout.item_knowledge_classify);
        knowledgeClassifyAdapter.setOnItemClickListener(this);
        knowledgeClassifyAdapter.setOnItemChildClickListener(this);
        smartRefreshLayout.setOnLoadMoreListener(this);
        smartRefreshLayout.setOnRefreshListener(this);
        rvClassify.setAdapter(knowledgeClassifyAdapter);
    }

    private void requestData() {
        HttpUtil.getKnowledgeClassifyList(page, cid, new MyObserver<KnowledgeClassify>() {
            @Override
            protected void onRequestSuccess(KnowledgeClassify data) {
                if (data.getDatas().size() > 0) {
                    if (isRefreshing) {
                        knowledgeClassifyAdapter.getData().clear();
                    }
                    knowledgeClassifyAdapter.addData(data.getDatas());
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
        String link = ((KnowledgeClassifyAdapter) adapter).getData().get(position).getLink();
        ARouter.getInstance().build(Constant.ACTIVITY_URL_WEBVIEW).
                withString(Constant.KEY_WEBVIEW, link)
                .navigation();
    }


    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.iv_love:
                int id = ((KnowledgeClassifyAdapter) adapter).getData().get(position).getId();
                boolean isCollect = ((KnowledgeClassifyAdapter) adapter).getData().get(position).isCollect();
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
                knowledgeClassifyAdapter.getData().get(position).setCollect(false);
                knowledgeClassifyAdapter.notifyDataSetChanged();
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
                knowledgeClassifyAdapter.getData().get(position).setCollect(true);
                knowledgeClassifyAdapter.notifyDataSetChanged();
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
