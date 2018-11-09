package www.wanandroid.com.wanandroid.common;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class LinearSpacingItemDecoration extends RecyclerView.ItemDecoration {
    private int orientation;//方向
    private int spacing;//间距
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    public LinearSpacingItemDecoration(int orientation, int spacing) {
        this.orientation = orientation;
        this.spacing = spacing;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        if (orientation==VERTICAL){
            outRect.bottom=spacing;
        }else {
            outRect.right=spacing;
        }

    }
}
