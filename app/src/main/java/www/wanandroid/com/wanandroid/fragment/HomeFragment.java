package www.wanandroid.com.wanandroid.fragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import www.wanandroid.com.wanandroid.R;
import www.wanandroid.com.wanandroid.adapter.HomeArticleAdapter;
import www.wanandroid.com.wanandroid.constant.Constant;
import www.wanandroid.com.wanandroid.event.UpEvent;
import www.wanandroid.com.wanandroid.observer.MyObserver;
import www.wanandroid.com.wanandroid.service.bean.Banner;
import www.wanandroid.com.wanandroid.service.bean.IndexArticle;
import www.wanandroid.com.wanandroid.utils.HttpUtil;

public class HomeFragment extends BaseFragment implements BaseQuickAdapter.OnItemClickListener, OnLoadMoreListener {
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
        //rvHome.setLayoutManager(new TopLayoutManager(getActivity()));
        rvHome.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        homeArticleAdapter = new HomeArticleAdapter(datasBeans);
        smartRefreshLayout.setOnLoadMoreListener(this);
        homeArticleAdapter.setOnItemClickListener(this);
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
            //rvHome.smoothScrollToPosition(0);
            rvHome.smoothScrollToPosition(0);
            //homeArticleAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        requestData();
    }
}
