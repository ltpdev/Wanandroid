package www.wanandroid.com.wanandroid.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import www.wanandroid.com.wanandroid.R;
import www.wanandroid.com.wanandroid.adapter.ContentAdapter;
import www.wanandroid.com.wanandroid.adapter.LeftMenuAdapter;
import www.wanandroid.com.wanandroid.event.UpEvent;
import www.wanandroid.com.wanandroid.observer.MyObserver;
import www.wanandroid.com.wanandroid.service.bean.Navigation;
import www.wanandroid.com.wanandroid.utils.HttpUtil;
import www.wanandroid.com.wanandroid.utils.ToastUtil;

public class NavigationFragment extends BaseFragment implements BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.rv_menu)
    RecyclerView rvMenu;
    @BindView(R.id.rv_content)
    RecyclerView rvContent;
    private LeftMenuAdapter leftMenuAdapter;
    private List<String> menus = new ArrayList<>();
    private ContentAdapter contentAdapter;
    private boolean isMenuClick=false;


    @Override
    protected void init() {
        initRecyclerView();
        requestData();
    }

    private void requestData() {
        HttpUtil.getNavigationList(new MyObserver<List<Navigation>>() {
            @Override
            protected void onRequestSuccess(List<Navigation> data) {
                contentAdapter.addData(data);
                for (Navigation navigation : data) {
                    menus.add(navigation.getName());
                }
                leftMenuAdapter.addData(menus);
            }

            @Override
            protected void onRequestError() {

            }

        });
    }

    private void initRecyclerView() {
        rvMenu.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvContent.setLayoutManager(new LinearLayoutManager(getActivity()));
        leftMenuAdapter = new LeftMenuAdapter(R.layout.item_left_menu);
        leftMenuAdapter.setOnItemClickListener(this);
        rvMenu.setAdapter(leftMenuAdapter);
        contentAdapter = new ContentAdapter(R.layout.item_content);
        rvContent.setAdapter(contentAdapter);
        rvContent.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                        int position = linearLayoutManager.findFirstVisibleItemPosition();
                        if (!isMenuClick){
                            leftMenuAdapter.setSelectId(position);
                            rvMenu.smoothScrollToPosition(position);
                        }
                        isMenuClick=false;
                        break;
                    case RecyclerView.SCROLL_STATE_DRAGGING:
                        break;
                    case RecyclerView.SCROLL_STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_navigation;
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (adapter instanceof LeftMenuAdapter) {
            leftMenuAdapter.setSelectId(position);
            rvContent.smoothScrollToPosition(position);
            isMenuClick=true;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpEvent(UpEvent msg) {
        if (isVisible() && msg.isUp()) {
            isMenuClick=false;
            rvContent.smoothScrollToPosition(0);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
