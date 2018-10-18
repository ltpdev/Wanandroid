package www.wanandroid.com.wanandroid.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import www.wanandroid.com.wanandroid.R;
import www.wanandroid.com.wanandroid.activity.KnowledgeClassifyActivity;
import www.wanandroid.com.wanandroid.adapter.KnowledgeSystemAdapter;
import www.wanandroid.com.wanandroid.constant.Constant;
import www.wanandroid.com.wanandroid.event.UpEvent;
import www.wanandroid.com.wanandroid.observer.MyObserver;
import www.wanandroid.com.wanandroid.service.bean.KnowledgeSystem;
import www.wanandroid.com.wanandroid.utils.HttpUtil;
import www.wanandroid.com.wanandroid.utils.ToastUtil;

public class SystemFragment extends BaseFragment implements BaseQuickAdapter.OnItemClickListener{
    @BindView(R.id.rvSystem)
    RecyclerView rvSystem;
    private KnowledgeSystemAdapter knowledgeSystemAdapter;

    @Override
    protected void init() {
        rvSystem.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvSystem.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        knowledgeSystemAdapter=new KnowledgeSystemAdapter(R.layout.item_knowledge_system);
        rvSystem.setAdapter(knowledgeSystemAdapter);
        knowledgeSystemAdapter.setOnItemClickListener(this);
        requestData();
    }

    private void requestData() {
        HttpUtil.getKnowledgeSystem(new MyObserver<List<KnowledgeSystem>>() {
            @Override
            protected void onRequestSuccess(List<KnowledgeSystem> data) {
                knowledgeSystemAdapter.addData(data);
            }

            @Override
            protected void onRequestError() {

            }
        });
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
            rvSystem.smoothScrollToPosition(0);
        }
    }






    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
         KnowledgeSystem knowledgeSystem=((KnowledgeSystemAdapter)adapter).getData().get(position);
        Intent intent=new Intent(getActivity(), KnowledgeClassifyActivity.class);
        intent.putExtra(Constant.KNOWLEDGE,knowledgeSystem);
        startActivity(intent);
    }
}
