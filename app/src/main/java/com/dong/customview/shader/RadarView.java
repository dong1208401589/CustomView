package com.dong.customview.shader;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.dong.customview.R;

/**
 * Created by dc on 2017/11/3.
 * 雷达效果
 */

public class RadarView extends View {

    private static final int MSG_WHAT = 1;

    private static final int DELAY_TIME = 20;

    /**
     * 设置默认宽
     */
    private static final int DEFAULT_WIDTH = 200;
    /**
     * 设置默认高
     */
    private static final int DEFAULT_HEIGHT = 200;

    /**
     * paintShader
     */
    private Shader mRaderShader;

    /**
     * 雷达背景画笔
     */
    private Paint mRadarBGPaint;
    /**
     * 雷达画笔
     */
    private Paint mRadarPaint;

    /**
     * 开始颜色
     */
    private int mStartColor = 0x0000ff00;
    /**
     * 结束颜色
     */
    private int mEndColor = 0xaa00ff00;
    /**
     * 背景颜色
     */
    private int mRadarBgColor = Color.BLACK;
    /**
     * 圆圈线的颜色
     */
    private int mCircleColor = Color.WHITE;
    /**
     * 圆圈的层数
     */
    private int mCircleNum = 4;

    private int mRadarRadius;

    private Matrix mMatrix;

    /**
     * 旋转的角度
     */
    private int mRotate = 0;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            mRotate += 3;
            postInvalidate();

            mMatrix.reset();
            mMatrix.preRotate(mRotate, 0, 0);
            mHandler.sendEmptyMessageDelayed(MSG_WHAT, DELAY_TIME);
        }
    };


    public RadarView(Context context) {
        this(context,null);
    }

    public RadarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
        initData();
    }

    public RadarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        initData();
    }


    private void initAttrs(Context context, AttributeSet attrs) {

        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.RadarView);
            mStartColor = ta.getColor(R.styleable.RadarView_startColor, mStartColor);
            mEndColor = ta.getColor(R.styleable.RadarView_endColor, mEndColor);
            mRadarBgColor = ta.getColor(R.styleable.RadarView_backgroundColor, mRadarBgColor);
            mCircleColor = ta.getColor(R.styleable.RadarView_lineColor, mCircleColor);
            mCircleNum = ta.getInteger(R.styleable.RadarView_circleNum, mCircleNum);
            ta.recycle();
        }
    }

    private void initData() {
        mRadarBGPaint = new Paint();
        mRadarBGPaint.setAntiAlias(true);
        mRadarBGPaint.setColor(mRadarBgColor);
        //实心圆
        mRadarBGPaint.setStyle(Paint.Style.FILL);

        mRadarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //空心圆
        mRadarPaint.setStyle(Paint.Style.STROKE);
        mRadarPaint.setStrokeWidth(2);
        mRadarPaint.setColor(mCircleColor);
        mRaderShader = new SweepGradient(0, 0, mStartColor, mEndColor);

        mMatrix = new Matrix();
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRadarRadius = Math.min(w / 2, h / 2);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = measureSize(0, DEFAULT_WIDTH, widthMeasureSpec);
        int height = measureSize(1, DEFAULT_HEIGHT, heightMeasureSpec);
        int measureSize = Math.max(width, height);
        setMeasuredDimension(measureSize, measureSize);
    }

    /**
     * 测量宽高
     *
     * @param specType     0表示宽，其他表示高
     * @param defaultWidth 默认值
     * @param measureSpec  被测量值
     * @return 测量值
     */
    private int measureSize(int specType, int defaultWidth, int measureSpec) {

        int result;

        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            result = Math.max(defaultWidth, specSize);
        } else {
            result = defaultWidth;
            //等于0宽，其他为高
            if (specType == 0) {
                result += (getPaddingLeft() + getPaddingRight());
            } else {
                result += (getPaddingTop() + getPaddingBottom());
            }
        }


        return result;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mRadarBGPaint.setShader(null);

        //将画板移到中心点
        canvas.translate(mRadarRadius, mRadarRadius);
        //画背景底色
        canvas.drawCircle(0, 0, mRadarRadius, mRadarBGPaint);
        //画圈
        for (int i=0;i<mCircleNum;i++){
            canvas.drawCircle(0,0,(float) (i*1.0/mCircleNum* mRadarRadius),mRadarPaint);
        }

        //绘制y轴线
        canvas.drawLine(0,-mRadarRadius,0, mRadarRadius,mRadarPaint);
        //绘制x轴线
        canvas.drawLine(-mRadarRadius,0, mRadarRadius,0,mRadarPaint);

        //设置颜色渐变从透明到不透明
        mRadarBGPaint.setShader(mRaderShader);
        canvas.concat(mMatrix);
        canvas.drawCircle(0, 0, mRadarRadius, mRadarBGPaint);

    }

    public void startScan() {
        mHandler.removeMessages(MSG_WHAT);
        mHandler.sendEmptyMessage(MSG_WHAT);
    }

    public void stopScan() {
        mHandler.removeMessages(MSG_WHAT);
    }
}
