package www.wanandroid.com.wanandroid.fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import www.wanandroid.com.wanandroid.activity.BaseActivity;


/*自定义对话框基础类
* */
public abstract class BaseDialogFragment extends DialogFragment {
    private final int MARGIN = 40;//对话框左右边距
    protected Context context;
    protected BaseActivity activity;
    protected View rootView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
        activity= (BaseActivity) getActivity();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog=super.onCreateDialog(savedInstanceState);
        // 关闭标题栏，setContentView() 之前调用
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(isCanceledOnTouchOutside());
        Window window=dialog.getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return dialog;
    }

    protected abstract boolean isCanceledOnTouchOutside();

    @Override
    public void onStart() {
        super.onStart();
        initWindow(getDialog().getWindow());
    }
    /**
     * 初始化窗口
     * @param window
     */
    protected void initWindow(Window window) {
        DisplayMetrics dm=getResources().getDisplayMetrics();
        window.setLayout((int) (dm.widthPixels - 2 * MARGIN * dm.density), WindowManager.LayoutParams.WRAP_CONTENT);
    }


}
