package www.wanandroid.com.wanandroid.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import www.wanandroid.com.wanandroid.R;
import www.wanandroid.com.wanandroid.activity.TestActivity;
import www.wanandroid.com.wanandroid.adapter.MyAdater;
import www.wanandroid.com.wanandroid.event.UpEvent;
import www.wanandroid.com.wanandroid.utils.ToastUtil;

public class SystemFragment extends BaseFragment {
    @BindView(R.id.rv)
    RecyclerView rv;
    private MyAdater myAdater;
    private List<String> strings;

    @Override
    protected void init() {
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        strings = new ArrayList<>();
        myAdater = new MyAdater(strings, getActivity());
        rv.setAdapter(myAdater);
        for (int i = 0; i < 50; i++) {
            strings.add("张三" + i);
        }
        myAdater.notifyDataSetChanged();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_system;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }




    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpEvent(UpEvent msg) {
        if (isVisible() && msg.isUp()) {
            rv.smoothScrollToPosition(0);
        }
    }



    @OnClick(R.id.btn_go)
    public void onViewClicked() {
       rv.smoothScrollToPosition(0);
    }
}
