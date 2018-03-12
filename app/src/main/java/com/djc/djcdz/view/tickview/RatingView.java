package com.djc.djcdz.view.tickview;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.djc.djcdz.R;
import com.djc.djcdz.util.DensityUtil;
import com.djc.djcdz.util.LogUtils;

/**
 * Created by Administrator
 * on 2018/3/5 星期一.
 */

public class RatingView extends View {

    //内圆的画笔
    private Paint mPaintCircle;
    //外层圆环的画笔
    private Paint mPaintRing;
    //打钩的画笔
    private Paint mPaintTick;

    //整个圆外切的矩形
    private RectF mRectF = new RectF();

    //控件中心的X,Y坐标
    private int centerX;
    private int centerY;

    //计数器
    private int circleRadius = -1;
    private int ringProgress = 0;

    //是否被点亮
    private boolean isChecked = false;
    //是否处于动画中
    private boolean isAnimationRunning = false;

    //最后扩大缩小动画中，画笔的宽度的最大倍数
    private static final int SCALE_TIMES = 1;




    private int mRingAnimatorDuration;

    TickViewConfig mConfig;

    private Path mTickPath;
    private Path mTickPathMeasureDst;
    private PathMeasure mPathMeasure;

    public RatingView(Context context) {
        this(context, null);
    }

    public RatingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RatingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
        initPaint();
        initAnimatorCounter();
    }

    private void initAttrs(AttributeSet attrs) {
        if (mConfig == null) {
            mConfig = new TickViewConfig(getContext());
        }
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TickView);
        mConfig.setUnCheckBaseColor(typedArray.getColor(R.styleable.TickView_uncheck_base_color, getResources().getColor(R.color.colorPrimary)))
                .setCheckBaseColor(typedArray.getColor(R.styleable.TickView_check_base_color, getResources().getColor(R.color.tick_yellow)))
                .setCheckTickColor(typedArray.getColor(R.styleable.TickView_check_tick_color, getResources().getColor(R.color.tick_white)))
                .setRadius(typedArray.getDimensionPixelOffset(R.styleable.TickView_radius, dp2px(20)))
                .setClickable(typedArray.getBoolean(R.styleable.TickView_clickable, true))
                .setTickRadius(dp2px(12))
                .setTickRadiusOffset(dp2px(4));

        int rateMode = typedArray.getInt(R.styleable.TickView_rate, 1);
        TickRateEnum mTickRateEnum = TickRateEnum.getRateEnum(rateMode);
        mRingAnimatorDuration = mTickRateEnum.getmRingAnimatorDuration();
        typedArray.recycle();
        applyConfig(mConfig);
        if (mTickPath == null) mTickPath = new Path();
        if (mTickPathMeasureDst == null) mTickPathMeasureDst = new Path();
        if (mPathMeasure == null) mPathMeasure = new PathMeasure();
    }

    private void applyConfig(TickViewConfig config) {
        assert mConfig != null : "TickView Config must not be null";
        mConfig.setConfig(config);
        if (mConfig.isNeedToReApply()) {
            initPaint();
            initAnimatorCounter();
            mConfig.setNeedToReApply(false);
        }
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        if (mPaintRing == null) mPaintRing = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintRing.setStyle(Paint.Style.STROKE);
        mPaintRing.setColor(isChecked ? mConfig.getCheckBaseColor() : mConfig.getUnCheckBaseColor());
        mPaintRing.setStrokeCap(Paint.Cap.ROUND);
        mPaintRing.setStrokeWidth(dp2px(2.5f));

        if (mPaintCircle == null) mPaintCircle = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintCircle.setColor(mConfig.getCheckBaseColor());

        if (mPaintTick == null) mPaintTick = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintTick.setColor(isChecked ? mConfig.getCheckTickColor() : mConfig.getUnCheckBaseColor());
        mPaintTick.setStyle(Paint.Style.STROKE);
        mPaintTick.setStrokeCap(Paint.Cap.ROUND);
        mPaintTick.setStrokeWidth(dp2px(2.5f));
    }

    /**
     * 用ObjectAnimator初始化一些计数器
     */
    private void initAnimatorCounter() {
        //圆环进度
        ObjectAnimator mRingAnimator = ObjectAnimator.ofInt(this, "ringProgress", 0, 360);
        mRingAnimator.setDuration(800);
        mRingAnimator.setInterpolator(null);
        mRingAnimator.setRepeatCount(Integer.MAX_VALUE);
        mRingAnimator.start();

    }

    private int getMySize(int defaultSize, int measureSpec) {
        int mySize = defaultSize;

        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        switch (mode) {
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST:
                mySize = defaultSize;
                break;
            case MeasureSpec.EXACTLY:
                mySize = size;
                break;
            default:
                break;
        }
        return mySize;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        final int radius = mConfig.getRadius();
        final float tickRadius = mConfig.getTickRadius();
        final float tickRadiusOffset = mConfig.getTickRadiusOffset();

        //控件的宽度等于动画最后的扩大范围的半径
//        int width = getMySize((radius + dp2px(2.5f) * SCALE_TIMES) * 2, widthMeasureSpec);
//        int height = getMySize((radius + dp2px(2.5f) * SCALE_TIMES) * 2, heightMeasureSpec);

        int width = widthMeasureSpec;
        int height = heightMeasureSpec;


        height = width = Math.max(width, height);

        setMeasuredDimension(width, height);

        centerX = getMeasuredWidth() / 2;
        centerY = getMeasuredHeight() / 2;

        //设置圆圈的外切矩形
        mRectF.set(centerX - radius, centerY - radius, centerX + radius, centerY + radius);

        //设置打钩的几个点坐标
        final float startX = centerX - tickRadius + tickRadiusOffset;
        final float startY = (float) centerY;

        final float middleX = centerX - tickRadius / 2 + tickRadiusOffset;
        final float middleY = centerY + tickRadius / 2;

        final float endX = centerX + tickRadius * 2 / 4 + tickRadiusOffset;
        final float endY = centerY - tickRadius * 2 / 4;

        mTickPath.reset();
        mTickPath.moveTo(startX, startY);
        mTickPath.lineTo(middleX, middleY);
        mTickPath.lineTo(endX, endY);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mConfig.isNeedToReApply()) {
            applyConfig(mConfig);
        }
        super.onDraw(canvas);
        if (!isChecked) {
            canvas.drawArc(mRectF, 90, 360, false, mPaintRing);
            canvas.drawPath(mTickPath, mPaintTick);
            return;
        }
        int start=45;

        //画圆弧进度
        canvas.drawArc(mRectF, start+=45, ringProgress, false, mPaintRing);

    }

    /*--------------属性动画---getter/setter begin---------------*/

    private int getRingProgress() {
        return ringProgress;
    }

    private void setRingProgress(int ringProgress) {
        this.ringProgress = ringProgress;
        postInvalidate();
    }

    private int getCircleRadius() {
        return circleRadius;
    }

    private void setCircleRadius(int circleRadius) {
        this.circleRadius = circleRadius;
        postInvalidate();
    }

    private float tickProgress = 0.0f;

    private float getTickProgress() {
        return tickProgress;
    }

    private void setTickProgress(float tickProgress) {
        this.tickProgress = tickProgress;
        LogUtils.d("progress", "setTickProgress: " + tickProgress);
        invalidate();
    }

    private void setTickAlpha(int tickAlpha) {
        mPaintTick.setAlpha(tickAlpha);
        postInvalidate();
    }

    private float getRingStrokeWidth() {
        return mPaintRing.getStrokeWidth();
    }

    private void setRingStrokeWidth(float strokeWidth) {
        mPaintRing.setStrokeWidth(strokeWidth);
        postInvalidate();
    }

     /*--------------属性动画---getter/setter end---------------*/

    /**
     * 改变状态
     *
     * @param checked 选中还是未选中
     */
    public void setChecked(boolean checked) {
        if (this.isChecked != checked) {
            isChecked = checked;
            reset();
        }
    }

    /**
     * @return 当前状态是否选中
     */
    public boolean isChecked() {
        return isChecked;
    }

    /**
     * 改变当前的状态
     */
    public void toggle() {
        setChecked(!isChecked);
    }

    /**
     * 重置
     */
    private void reset() {
        initPaint();
        ringProgress = 0;
        circleRadius = -1;
        isAnimationRunning = false;
        final int radius = mConfig.getRadius();
        mRectF.set(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
        invalidate();
    }

    public TickViewConfig getConfig() {
        return mConfig;
    }

    public void setConfig(TickViewConfig tickViewConfig) {
        if (tickViewConfig == null) return;
        applyConfig(tickViewConfig);
    }

    private int dp2px(float dpValue) {
        return DensityUtil.dip2px(getContext(), dpValue);
    }


}