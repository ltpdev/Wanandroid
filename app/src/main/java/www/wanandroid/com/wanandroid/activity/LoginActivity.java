package www.wanandroid.com.wanandroid.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.android.arouter.facade.annotation.Route;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import www.wanandroid.com.wanandroid.R;
import www.wanandroid.com.wanandroid.constant.Constant;
import www.wanandroid.com.wanandroid.observer.MyObserver;
import www.wanandroid.com.wanandroid.service.bean.UserInfo;
import www.wanandroid.com.wanandroid.utils.HttpUtil;
import www.wanandroid.com.wanandroid.utils.ToastUtil;

@Route(path = Constant.ACTIVITY_URL_LOGIN)
public class LoginActivity extends BaseActivity {


    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.email_sign_in_button)
    Button emailSignInButton;

    @Override
    protected void init() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login;
    }



    @OnClick(R.id.email_sign_in_button)
    public void onViewClicked() {
        String name=username.getText().toString();
        String pwd=password.getText().toString();
        HttpUtil.userLogin(name, pwd, new MyObserver<UserInfo>() {

            @Override
            protected void onRequestSuccess(UserInfo data) {
                ToastUtil.showText(LoginActivity.this,"登录成功");
                finish();
            }

            @Override
            protected void onRequestError() {

            }
        });
    }
}
