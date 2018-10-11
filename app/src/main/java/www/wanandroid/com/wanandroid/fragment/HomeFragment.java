package www.wanandroid.com.wanandroid.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import www.wanandroid.com.wanandroid.R;
import www.wanandroid.com.wanandroid.adapter.HomeArticleAdapter;
import www.wanandroid.com.wanandroid.constant.Constant;
import www.wanandroid.com.wanandroid.event.UpEvent;
import www.wanandroid.com.wanandroid.manager.TopLayoutManager;
import www.wanandroid.com.wanandroid.observer.MyObserver;
import www.wanandroid.com.wanandroid.service.bean.Banner;
import www.wanandroid.com.wanandroid.service.bean.HttpResult;
import www.wanandroid.com.wanandroid.service.bean.IndexArticle;
import www.wanandroid.com.wanandroid.utils.HttpUtil;
import www.wanandroid.com.wanandroid.utils.ToastUtil;

public class HomeFragment extends BaseFragment implements BaseQuickAdapter.OnItemClickListener,BaseQuickAdapter.OnItemChildClickListener, OnLoadMoreListener{
    @BindView(R.id.rv_home)
    RecyclerView rvHome;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    private HomeArticleAdapter homeArticleAdapter;
    private int page = 0;
    private List<IndexArticle.DatasBean> datasBeans = new ArrayList<>();

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
        rvHome.setLayoutManager(new TopLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false));
        rvHome.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        homeArticleAdapter = new HomeArticleAdapter(datasBeans);
        smartRefreshLayout.setOnLoadMoreListener(this);
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
                    homeArticleAdapter.addData(indexArticle.getDatas());
                    page++;
                    smartRefreshLayout.finishLoadMore(true);
                } else {
                    smartRefreshLayout.finishLoadMore(0, false, true);
                }
            }

            @Override
            protected void onRequestError() {
                smartRefreshLayout.finishLoadMore(false);
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

                homeArticleAdapter.addHeaderView(headerView);

            }

            @Override
            protected void onRequestError() {

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
        EventBus.getDefault().unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpEvent(UpEvent msg) {
        if (isVisible() && msg.isUp()) {
            //ToastUtil.showText(getActivity(),"isVisible");
            rvHome.smoothScrollToPosition(0);
            //homeArticleAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        requestData();
    }


    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
             switch (view.getId()){
                 case R.id.iv_love:
                     int id=((HomeArticleAdapter) adapter).getData().get(position).getId();
                     HttpUtil.collectArticle(id, new MyObserver() {
                         @Override
                         protected void onRequestSuccess(Object data) {
                              ToastUtil.showText(getActivity(),"收藏成功");
                         }

                         @Override
                         protected void onRequestError() {

                         }
                     });
                     break;
             }
    }
}
