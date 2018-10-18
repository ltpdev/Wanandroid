package www.wanandroid.com.wanandroid.fragment;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import www.wanandroid.com.wanandroid.R;
import www.wanandroid.com.wanandroid.adapter.HomeArticleAdapter;
import www.wanandroid.com.wanandroid.adapter.KnowledgeClassifyAdapter;
import www.wanandroid.com.wanandroid.constant.Constant;
import www.wanandroid.com.wanandroid.observer.MyObserver;
import www.wanandroid.com.wanandroid.service.bean.KnowledgeClassify;
import www.wanandroid.com.wanandroid.utils.HttpUtil;

public class KnowledgeListFragment extends LazyLoadFragment implements BaseQuickAdapter.OnItemClickListener{
    @BindView(R.id.rv_classify)
    RecyclerView rvClassify;
    private int cid;
    private KnowledgeClassifyAdapter knowledgeClassifyAdapter;
    private int page = 0;

    public static KnowledgeListFragment getInstance(int cid) {
        KnowledgeListFragment fragment = new KnowledgeListFragment();
        Bundle args = new Bundle();
        args.putInt(Constant.KNOWLEDGE_CID, cid);
        fragment.setArguments(args);
        return fragment;
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
        rvClassify.setAdapter(knowledgeClassifyAdapter);
    }

    private void requestData() {
        HttpUtil.getKnowledgeClassifyList(page, cid, new MyObserver<KnowledgeClassify>() {
            @Override
            protected void onRequestSuccess(KnowledgeClassify data) {
                if (data.getDatas().size() > 0) {
                    knowledgeClassifyAdapter.addData(data.getDatas());
                    page++;
                } else {

                }
            }

            @Override
            protected void onRequestError() {

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
}
