package www.wanandroid.com.wanandroid.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import www.wanandroid.com.wanandroid.R;
import www.wanandroid.com.wanandroid.constant.Constant;
import www.wanandroid.com.wanandroid.utils.ToastUtil;

public class ProjectFragment extends BaseFragment {




    @Override
    protected void init() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_project;
    }

    public void showToast() {
        ARouter.getInstance().build(Constant.ACTIVITY_URL_MAIN).navigation();

    }

    public void showToast2() {
        Toast.makeText(getActivity(), "哈哈我是第二个吐司", Toast.LENGTH_SHORT).show();
    }



    @OnClick({R.id.btn1, R.id.btn2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                showToast();
                break;
            case R.id.btn2:
                showToast2();
                break;
        }
    }
}
