package www.wanandroid.com.wanandroid.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import www.wanandroid.com.wanandroid.R;
import www.wanandroid.com.wanandroid.adapter.MyAdater;
import www.wanandroid.com.wanandroid.utils.ToastUtil;

public class TestActivity extends BaseActivity {

    @BindView(R.id.rv)
    RecyclerView rv;
    private MyAdater myAdater;
    private List<String> strings;

    @Override
    protected void init() {
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        strings = new ArrayList<>();
        myAdater = new MyAdater(strings, this);
        rv.setAdapter(myAdater);
        for (int i = 0; i < 50; i++) {
            strings.add("张三" + i);
        }
        myAdater.notifyDataSetChanged();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_test;
    }

    @OnClick(R.id.btn_go)
    public void onViewClicked() {
        ToastUtil.showText(this, "onViewClicked");
        rv.smoothScrollToPosition(0);
    }
}
