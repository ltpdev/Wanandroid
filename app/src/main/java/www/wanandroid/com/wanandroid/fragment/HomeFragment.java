package www.wanandroid.com.wanandroid.fragment;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
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
import www.wanandroid.com.wanandroid.R;
import www.wanandroid.com.wanandroid.adapter.HomeArticleAdapter;
import www.wanandroid.com.wanandroid.constant.Constant;
import www.wanandroid.com.wanandroid.event.RefreshEvent;
import www.wanandroid.com.wanandroid.event.UpEvent;
import www.wanandroid.com.wanandroid.observer.MyObserver;
import www.wanandroid.com.wanandroid.service.bean.Banner;
import www.wanandroid.com.wanandroid.service.bean.IndexArticle;
import www.wanandroid.com.wanandroid.utils.HttpUtil;
import www.wanandroid.com.wanandroid.utils.ToastUtil;

public class HomeFragment extends BaseFragment implements BaseQuickAdapter.OnItemClickListener, BaseQuickAdapter.OnItemChildClickListener, OnLoadMoreListener, OnRefreshListener {
    @BindView(R.id.rv_home)
    RecyclerView rvHome;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    private HomeArticleAdapter homeArticleAdapter;
    private int page = 0;
    private List<IndexArticle.DatasBean> datasBeans = new ArrayList<>();
    //是否在刷新
    private boolean isRefreshing = false;

    @Override
    protected void init() {
        initRecyclerView();
        requestIndexBanner();
    }


    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }


    private void initRecyclerView() {
        rvHome.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvHome.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        homeArticleAdapter = new HomeArticleAdapter(datasBeans);
        smartRefreshLayout.setOnLoadMoreListener(this);
        smartRefreshLayout.setOnRefreshListener(this);
        homeArticleAdapter.setOnItemClickListener(this);
        homeArticleAdapter.setOnItemChildClickListener(this);
        rvHome.setAdapter(homeArticleAdapter);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_home;
    }


    private void requestData() {
        HttpUtil.getIndexArticleList(page, new MyObserver<IndexArticle>() {
            @Override
            protected void onRequestSuccess(IndexArticle indexArticle) {
                if (indexArticle.getDatas().size() > 0) {
                    if (isRefreshing) {
                        homeArticleAdapter.getData().clear();
                    }
                    homeArticleAdapter.addData(indexArticle.getDatas());
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


    private void requestIndexBanner() {
        HttpUtil.getIndexBanner(new MyObserver<List<Banner>>() {
            @Override
            protected void onRequestSuccess(List<Banner> data) {
                View headerView = View.inflate(getActivity(), R.layout.list_header, null);
                SliderLayout sliderLayout = (SliderLayout) headerView.findViewById(R.id.sliderlayout);
                for (final Banner banner : data) {
                    TextSliderView textSliderView = new TextSliderView(getActivity());
                    textSliderView.description(banner.getTitle()).image(banner.getImagePath());
                    sliderLayout.addSlider(textSliderView);
                    textSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                        @Override
                        public void onSliderClick(BaseSliderView slider) {
                            ARouter.getInstance().build(Constant.ACTIVITY_URL_WEBVIEW).
                                    withString(Constant.KEY_WEBVIEW, banner.getUrl())
                                    .navigation();
                        }
                    });
                }
                page = 0;
                homeArticleAdapter.removeAllHeaderView();
                homeArticleAdapter.notifyDataSetChanged();
                homeArticleAdapter.addHeaderView(headerView);

            }

            @Override
            protected void onRequestError() {
                 if (isRefreshing){
                     smartRefreshLayout.finishRefresh(false);
                 }
            }

            @Override
            public void onComplete() {
                super.onComplete();
                requestData();
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
    public void onDestroyView() {
        super.onDestroyView();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpEvent(UpEvent msg) {
        if (isVisible() && msg.isUp()) {
            rvHome.smoothScrollToPosition(0);
            //homeArticleAdapter.notifyDataSetChanged();
        }
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
    public void onItemChildClick(BaseQuickAdapter adapter, View view, final int position) {
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
                ToastUtil.showText(getActivity(), "取消收藏成功");
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
                ToastUtil.showText(getActivity(), "收藏成功");
                datasBeans.get(position).setCollect(true);
                homeArticleAdapter.notifyDataSetChanged();
            }

            @Override
            protected void onRequestError() {

            }
        });
    }


    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        isRefreshing = true;
        requestIndexBanner();
    }



}
