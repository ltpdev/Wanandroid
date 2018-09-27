package www.wanandroid.com.wanandroid.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import www.wanandroid.com.wanandroid.R;


/**
 *
 * 加载框
 */
public class LoadingDialog extends BaseDialogFragment{
    private ImageView iv_loading;
    private Window window;
    private Dialog alertDialog;
    public static LoadingDialog newInstance() {
        return new LoadingDialog();
    }



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (alertDialog==null){
            Log.i("LoadingDialog","onCreateDialog");
            alertDialog=super.onCreateDialog(savedInstanceState);
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_loading, null,false);
            alertDialog.setContentView(view);
            iv_loading=view.findViewById(R.id.iv_loading);
        }
       return alertDialog;
    }

    @Override
    protected boolean isCanceledOnTouchOutside() {
        return false;
    }

    @Override
    protected void initWindow(Window window) {
        this.window=window;
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }


    public void startAnimation() {
        if (iv_loading!=null){
            iv_loading.startAnimation(AnimationUtils.loadAnimation(context, R.anim.anim_loading));
        }
    }
    public void stopAnimation() {
        if (iv_loading!=null){
            iv_loading.clearAnimation();
        }
    }
}
