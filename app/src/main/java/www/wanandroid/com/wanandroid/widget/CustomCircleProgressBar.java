package www.wanandroid.com.wanandroid.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import www.wanandroid.com.wanandroid.R;

/*自定义圆形进度条*/
public class CustomCircleProgressBar extends View {
    private int outsideColor;//进度条的颜色
    private float outsideRadius;//外圆半径大小
    private int insideColor;//背景颜色
    private int progressTextColor;//圆环内文字颜色
    private float progressTextSize;//圆环内文字大小
    private float progressWidth;//圆环宽度
    private float maxProgress;//最大进度
    private float progress;//当前进度
    private int direction;//进度从哪里开始
    private Paint paint;
    private String progressText;//圆环内文字
    private Rect rect;


    enum DirectionEnum {
        LEFT(0, 180.0f),
        TOP(1, 270.0f),
        RIGHT(2, 0.0f),
        BOTTOM(3, 90.0f);

        private final int direction;
        private final float degree;

        DirectionEnum(int direction, float degree) {
            this.direction = direction;
            this.degree = degree;
        }

        public int getDirection() {
            return direction;
        }

        public float getDegree() {
            return degree;
        }

        public boolean equalsDescription(int direction) {
            return this.direction == direction;
        }

        public static DirectionEnum getDirection(int direction) {
            for (DirectionEnum enumObject : values()) {
                if (enumObject.equalsDescription(direction)) {
                    return enumObject;
                }
            }
            return RIGHT;
        }

        public static float getDegree(int direction) {
            DirectionEnum enumObject = getDirection(direction);
            if (enumObject == null) {
                return 0;
            }
            return enumObject.getDegree();
        }
    }

    public CustomCircleProgressBar(Context context) {
        this(context, null);
    }

    public CustomCircleProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomCircleProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomCircleProgressBar, defStyleAttr, 0);
        outsideColor = a.getColor(R.styleable.CustomCircleProgressBar_outside_color, ContextCompat.getColor(getContext(), R.color.colorPrimary));
        outsideRadius = a.getDimension(R.styleable.CustomCircleProgressBar_outside_radius, 30f);
        insideColor = a.getColor(R.styleable.CustomCircleProgressBar_inside_color, ContextCompat.getColor(getContext(), R.color.colorAccent));
        progressTextColor = a.getColor(R.styleable.CustomCircleProgressBar_progress_text_color, ContextCompat.getColor(getContext(), R.color.colorPrimary));
        progressTextSize = a.getDimension(R.styleable.CustomCircleProgressBar_progress_text_size, 50);
        progressWidth = a.getDimension(R.styleable.CustomCircleProgressBar_progress_width, 5f);
        progress = a.getFloat(R.styleable.CustomCircleProgressBar_progress, 50.0f);
        maxProgress = a.getInt(R.styleable.CustomCircleProgressBar_max_progress, 100);
        direction = a.getInt(R.styleable.CustomCircleProgressBar_direction, 3);
        a.recycle();
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int circlePoint = getWidth() / 2;
        //第一步:画背景(即内层圆)
        paint.setColor(insideColor);//设置圆的颜色
        //设置空心
        paint.setStyle(Paint.Style.STROKE);
        //设置圆的宽度
        paint.setStrokeWidth(progressWidth);
        paint.setAntiAlias(true);
        canvas.drawCircle(circlePoint, circlePoint, outsideRadius, paint);
        //第二步:画进度(圆弧)
        //设置进度的颜色
        paint.setColor(outsideColor);
        RectF oval = new RectF(circlePoint - outsideRadius, circlePoint - outsideRadius, circlePoint + outsideRadius, circlePoint + outsideRadius);  //用于定义的圆弧的形状和大小的界限
        canvas.drawArc(oval, DirectionEnum.getDegree(direction), 360 * (progress / maxProgress), false, paint);  //根据进度画圆弧

        //第三步:画圆环内百分比文字
        rect = new Rect();
        paint.setColor(progressTextColor);
        paint.setTextSize(progressTextSize);
        paint.setStrokeWidth(0);
        progressText = getProgressText();
        paint.getTextBounds(progressText, 0, progressText.length(), rect);
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        int baseline = (getMeasuredHeight() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;  //获得文字的基准线
        canvas.drawText(progressText, getMeasuredWidth() / 2 - rect.width() / 2, baseline, paint);
    }

    //中间的进度百分比
    private String getProgressText() {
        return (int) ((progress / maxProgress) * 100) + "%";
    }

    public int getOutsideColor() {
        return outsideColor;
    }

    public void setOutsideColor(int outsideColor) {
        this.outsideColor = outsideColor;
    }

    public float getOutsideRadius() {
        return outsideRadius;
    }

    public void setOutsideRadius(float outsideRadius) {
        this.outsideRadius = outsideRadius;
    }

    public int getInsideColor() {
        return insideColor;
    }

    public void setInsideColor(int insideColor) {
        this.insideColor = insideColor;
    }

    public int getProgressTextColor() {
        return progressTextColor;
    }

    public void setProgressTextColor(int progressTextColor) {
        this.progressTextColor = progressTextColor;
    }

    public float getProgressTextSize() {
        return progressTextSize;
    }

    public void setProgressTextSize(float progressTextSize) {
        this.progressTextSize = progressTextSize;
    }

    public float getProgressWidth() {
        return progressWidth;
    }

    public void setProgressWidth(float progressWidth) {
        this.progressWidth = progressWidth;
    }

    public float getMaxProgress() {
        return maxProgress;
    }

    public void setMaxProgress(float maxProgress) {
        if (maxProgress < 0) {
            //此为传递非法参数异常
            throw new IllegalArgumentException("maxProgress should not be less than 0");
        }
        this.maxProgress = maxProgress;
    }

    public float getProgress() {
        return progress;
    }


    public void setProgress(float progress) {
        this.progress = progress;
        postInvalidate();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width;
        int height;
        int size = MeasureSpec.getSize(widthMeasureSpec);
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            width = size;
        } else {
            width = (int) ((2 * outsideRadius) + progressWidth);
        }
        size = MeasureSpec.getSize(heightMeasureSpec);
        mode = MeasureSpec.getMode(heightMeasureSpec);
        if (mode == MeasureSpec.EXACTLY) {
            height = size;
        } else {
            height = (int) ((2 * outsideRadius) + progressWidth);
        }
        //设置尺寸
        setMeasuredDimension(width, height);
    }


}
