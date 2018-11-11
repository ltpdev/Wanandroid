package www.wanandroid.com.wanandroid.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import www.wanandroid.com.wanandroid.R;
import www.wanandroid.com.wanandroid.adapter.SearchHistoryAdapter;
import www.wanandroid.com.wanandroid.common.LinearSpacingItemDecoration;
import www.wanandroid.com.wanandroid.constant.Constant;
import www.wanandroid.com.wanandroid.entry.dao.SearchHistory;
import www.wanandroid.com.wanandroid.manager.DataBaseManager;
import www.wanandroid.com.wanandroid.observer.MyObserver;
import www.wanandroid.com.wanandroid.service.bean.HotKey;
import www.wanandroid.com.wanandroid.service.bean.Navigation;
import www.wanandroid.com.wanandroid.utils.DrawableUtils;
import www.wanandroid.com.wanandroid.utils.HttpUtil;
import www.wanandroid.com.wanandroid.utils.NumberUtil;
import www.wanandroid.com.wanandroid.utils.SpUtil;
import www.wanandroid.com.wanandroid.widget.ClearableEditText;
import www.wanandroid.com.wanandroid.widget.FlowLayout;

@Route(path = Constant.ACTIVITY_URL_SEARCH_ARTICLE)
public class SearchArticleActivity extends BaseActivity implements TextView.OnEditorActionListener, BaseQuickAdapter.OnItemChildClickListener, BaseQuickAdapter.OnItemClickListener,View.OnClickListener {

    @BindView(R.id.edt_keyword)
    ClearableEditText edtKeyword;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.flow_layout)
    FlowLayout flowLayout;
    @BindView(R.id.rv_search_history)
    RecyclerView rvSearchHistory;
    private SearchHistoryAdapter searchHistoryAdapter;
    private View headView;

    @Override
    protected void init() {
        initRecyclerView();
        initListener();
        requestData();
    }

    private void initRecyclerView() {
        headView = View.inflate(this, R.layout.head_laout_search_history, null);
        rvSearchHistory.setLayoutManager(new LinearLayoutManager(this));
        rvSearchHistory.addItemDecoration(new LinearSpacingItemDecoration(LinearSpacingItemDecoration.VERTICAL, NumberUtil.dip2px(this,15)));
        searchHistoryAdapter = new SearchHistoryAdapter(R.layout.item_search_history);
        rvSearchHistory.setAdapter(searchHistoryAdapter);
        rvSearchHistory.setNestedScrollingEnabled(false);
        queryDataFromDataBase();
    }

    private void queryDataFromDataBase() {
        List<SearchHistory>searchHistoryList= DataBaseManager.getInstance(this).queryAllSearchHistory();
        if (searchHistoryList!=null&&searchHistoryList.size()>0){
            if (searchHistoryAdapter.getHeaderLayoutCount()==0){
                searchHistoryAdapter.addHeaderView(headView);
            }
            searchHistoryAdapter.setNewData(searchHistoryList);
        }
    }

    private void requestData() {
        HttpUtil.getHotSearchWord(new MyObserver<List<HotKey>>() {
            @Override
            protected void onRequestSuccess(List<HotKey> data) {
                int padding = NumberUtil.dip2px(SearchArticleActivity.this, 10);
                for (final HotKey hotKey : data) {
                    TextView textView = new TextView(SearchArticleActivity.this);
                    textView.setText(hotKey.getName());
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                    textView.setGravity(Gravity.CENTER);
                    textView.setPadding(padding, padding, padding, padding);
                    Random random = new Random();
                    int r = 45+ random.nextInt(210);
                    int g = 45 + random.nextInt(210);
                    int b = 45 + random.nextInt(210);
                    textView.setBackground(DrawableUtils.getGradientDrawable(Color.rgb(r, g, b),NumberUtil.dip2px(SearchArticleActivity.this,6)));
                    textView.setTextColor(Color.WHITE);
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                             SearchHistory searchHistory=new SearchHistory();
                             searchHistory.setKeyWord(hotKey.getName());
                             DataBaseManager.getInstance(SearchArticleActivity.this).save(searchHistory);
                             queryDataFromDataBase();
                            skipActivity(hotKey.getName());
                        }
                    });
                    flowLayout.addView(textView);
                }
            }

            @Override
            protected void onRequestError() {

            }
        });
    }

    private void initListener() {
        showSoftInputFromWindow(this, edtKeyword);
        edtKeyword.setOnEditorActionListener(this);
        searchHistoryAdapter.setOnItemChildClickListener(this);
        searchHistoryAdapter.setOnItemClickListener(this);
        headView.findViewById(R.id.tv_del_all).setOnClickListener(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_search_article;
    }


    @OnClick({R.id.iv_back, R.id.iv_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_search:
                String keyword=edtKeyword.getText().toString();
                if (!TextUtils.isEmpty(keyword)){
                    SearchHistory searchHistory=new SearchHistory();
                    searchHistory.setKeyWord(keyword);
                    DataBaseManager.getInstance(SearchArticleActivity.this).save(searchHistory);
                    hideKeyBoard();
                    queryDataFromDataBase();
                    skipActivity(keyword);;
                }
                break;
        }
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
            String keyword=edtKeyword.getText().toString();
            if (!TextUtils.isEmpty(keyword)){
                SearchHistory searchHistory=new SearchHistory();
                searchHistory.setKeyWord(keyword);
                DataBaseManager.getInstance(SearchArticleActivity.this).save(searchHistory);
                hideKeyBoard();
                queryDataFromDataBase();
                skipActivity(keyword);;
                return true;
            }else {
                return false;
            }
        }
        return false;
    }


    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        switch (view.getId()) {
            case R.id.iv_del:
                Long id=((SearchHistoryAdapter)adapter).getData().get(position).getId();
                DataBaseManager.getInstance(SearchArticleActivity.this).deleteSearchHistoryById(id);
                searchHistoryAdapter.remove(position);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_del_all:
                DataBaseManager.getInstance(SearchArticleActivity.this).deleteAllSearchHistory();
                searchHistoryAdapter.getData().clear();
                searchHistoryAdapter.notifyDataSetChanged();
                searchHistoryAdapter.removeAllHeaderView();
                break;
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        String keyWord=((SearchHistoryAdapter)adapter).getData().get(position).getKeyWord();
        skipActivity(keyWord);
    }


    private void skipActivity(String keyword){
        ARouter.getInstance().build(Constant.ACTIVITY_URL_SEARCH_ARTICLE_RESULT).
                withString(Constant.KEY_WORD_SEARCH, keyword)
                .navigation();
    }
}
