package www.wanandroid.com.wanandroid.fragment;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import www.wanandroid.com.wanandroid.R;
import www.wanandroid.com.wanandroid.adapter.HomeArticleAdapter;
import www.wanandroid.com.wanandroid.observer.MyObserver;
import www.wanandroid.com.wanandroid.service.bean.Banner;
import www.wanandroid.com.wanandroid.service.bean.HttpResult;
import www.wanandroid.com.wanandroid.service.bean.IndexArticle;
import www.wanandroid.com.wanandroid.utils.HttpUtil;
import www.wanandroid.com.wanandroid.utils.ToastUtil;

public class HomeFragment extends BaseFragment implements BaseQuickAdapter.RequestLoadMoreListener {
    @BindView(R.id.rv_home)
    RecyclerView rvHome;
    private HomeArticleAdapter homeArticleAdapter;
    private int page = 0;
    private List<IndexArticle.DatasBean> datasBeans = new ArrayList<>();

    @Override
    protected void init() {
        initRecyclerView();
        requestIndexBanner();
    }


    private void initRecyclerView() {
        rvHome.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvHome.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        homeArticleAdapter = new HomeArticleAdapter(datasBeans);
        homeArticleAdapter.setOnLoadMoreListener(this, rvHome);
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
                    homeArticleAdapter.loadMoreComplete();
                } else {
                    homeArticleAdapter.loadMoreEnd();
                }
            }

            @Override
            protected void onRequestError() {
                homeArticleAdapter.loadMoreFail();
            }
        });
    }

    @Override
    public void onLoadMoreRequested() {
        requestData();
    }


    private void requestIndexBanner() {
        HttpUtil.getIndexBanner(new MyObserver<List<Banner>>() {
            @Override
            protected void onRequestSuccess(List<Banner> data) {
                View headerView = View.inflate(getActivity(), R.layout.list_header, null);
                SliderLayout sliderLayout = (SliderLayout) headerView.findViewById(R.id.sliderlayout);
                for (Banner banner:data) {
                    TextSliderView textSliderView = new TextSliderView(getActivity());
                    textSliderView.description(banner.getTitle()).image(banner.getImagePath());
                    sliderLayout.addSlider(textSliderView);
                    textSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                        @Override
                        public void onSliderClick(BaseSliderView slider) {

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
}
