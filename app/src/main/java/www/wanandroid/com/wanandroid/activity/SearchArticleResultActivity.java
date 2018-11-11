package www.wanandroid.com.wanandroid.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import www.wanandroid.com.wanandroid.R;
import www.wanandroid.com.wanandroid.adapter.HomeArticleAdapter;
import www.wanandroid.com.wanandroid.constant.Constant;
import www.wanandroid.com.wanandroid.event.RefreshEvent;
import www.wanandroid.com.wanandroid.observer.MyObserver;
import www.wanandroid.com.wanandroid.service.bean.IndexArticle;
import www.wanandroid.com.wanandroid.utils.HttpUtil;
import www.wanandroid.com.wanandroid.utils.ToastUtil;

@Route(path = Constant.ACTIVITY_URL_SEARCH_ARTICLE_RESULT)
public class SearchArticleResultActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener, OnLoadMoreListener, OnRefreshListener {
    @BindView(R.id.rv_search_result)
    RecyclerView rvSearchResult;
    @Autowired(name = Constant.KEY_WORD_SEARCH)
    String searchKeyWord;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    private int page=0;
    private HomeArticleAdapter homeArticleAdapter;
    private List<IndexArticle.DatasBean> datasBeans = new ArrayList<>();
    private boolean isRefreshing=false;

    @Override
    protected void init() {
        intToolBar();
        initRecyclerView();
        requestData();
    }

    private void initRecyclerView() {
        rvSearchResult.setLayoutManager(new LinearLayoutManager(this));
        rvSearchResult.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        homeArticleAdapter=new HomeArticleAdapter(datasBeans);
        smartRefreshLayout.setOnLoadMoreListener(this);
        smartRefreshLayout.setOnRefreshListener(this);
        homeArticleAdapter.setOnItemClickListener(this);
        homeArticleAdapter.setOnItemChildClickListener(this);
        View emptyView=View.inflate(this,R.layout.layout_empty,null);
        homeArticleAdapter.setEmptyView(emptyView);
        rvSearchResult.setAdapter(homeArticleAdapter);
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_search_article_result;
    }

    private void intToolBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(searchKeyWord);
    }

    private void requestData() {
        HttpUtil.searchArticle(page, searchKeyWord, new MyObserver<IndexArticle>() {
            @Override
            protected void onRequestSuccess(IndexArticle data) {
                if (data.getDatas().size() > 0) {
                    if (isRefreshing) {
                        homeArticleAdapter.getData().clear();
                    }
                    homeArticleAdapter.addData(data.getDatas());
                    page++;
                    if (isRefreshing) {
                        smartRefreshLayout.finishRefresh(true);
                    } else {
                        smartRefreshLayout.finishLoadMore(true);
                    }
                } else {
                    if (isRefreshing) {
                        smartRefreshLayout.finishRefresh(0, false);
                    } else {
                        smartRefreshLayout.finishLoadMore(0, false, true);
                    }
                }
            }

            @Override
            protected void onRequestError() {
                if (isRefreshing) {
                    smartRefreshLayout.finishRefresh(false);
                } else {
                    smartRefreshLayout.finishLoadMore(false);
                }
            }
        });
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.iv_love:
                int id = ((HomeArticleAdapter) adapter).getData().get(position).getId();
                boolean isCollect = ((HomeArticleAdapter) adapter).getData().get(position).isCollect();
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
                ToastUtil.showText(SearchArticleResultActivity.this, "取消收藏成功");
                datasBeans.get(position).setCollect(false);
                homeArticleAdapter.notifyDataSetChanged();
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
                ToastUtil.showText(SearchArticleResultActivity.this, "收藏成功");
                datasBeans.get(position).setCollect(true);
                homeArticleAdapter.notifyDataSetChanged();
            }

            @Override
            protected void onRequestError() {

            }
        });
    }
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        String link = ((HomeArticleAdapter) adapter).getData().get(position).getLink();
        ARouter.getInstance().build(Constant.ACTIVITY_URL_WEBVIEW).
                withString(Constant.KEY_WEBVIEW, link)
                .navigation();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        isRefreshing = false;
        requestData();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRefreshEvent(RefreshEvent event) {
        if (event.isRefreshing()) {
            smartRefreshLayout.autoRefresh();
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        isRefreshing = true;
        page = 0;;
        requestData();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
