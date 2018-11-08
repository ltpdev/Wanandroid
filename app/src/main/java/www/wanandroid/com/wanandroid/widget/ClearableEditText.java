package www.wanandroid.com.wanandroid.widget;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.MotionEvent;

import www.wanandroid.com.wanandroid.R;


/*自带清除按钮的输入框*/
public class ClearableEditText extends AppCompatEditText {
    private static final int DRAWBLE_LEFT=0;
    private static final int DRAWBLE_TOP=1;
    private static final int DRAWBLE_RIGHT=2;
    private static final int DRAWBLE_BOTTOM=3;
    private Drawable mClearDrawable;

    public ClearableEditText(Context context) {
        super(context);
        init();
    }

    public ClearableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClearableEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mClearDrawable=getResources().getDrawable(R.mipmap.close);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        setClearIconVisible(hasFocus() && text.length() > 0);
    }


    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        setClearIconVisible(focused&&length()>0);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_UP:
                Drawable drawable=getCompoundDrawables()[DRAWBLE_RIGHT];
                if (drawable != null && event.getX() <= (getWidth() - getPaddingRight())
                        && event.getX() >= (getWidth() - getPaddingRight() - drawable.getBounds().width())) {
                    setText("");
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    //设置清除按钮是否可见
    private void setClearIconVisible(boolean visible) {
            setCompoundDrawablesWithIntrinsicBounds(getCompoundDrawables()[DRAWBLE_LEFT],getCompoundDrawables()[DRAWBLE_TOP],visible?mClearDrawable:null,getCompoundDrawables()[DRAWBLE_BOTTOM]);
    }


}
