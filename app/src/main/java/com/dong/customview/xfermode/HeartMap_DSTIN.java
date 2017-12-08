package com.dong.customview.xfermode;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.dong.customview.R;

/**
 * Created by dc on 2017/12/8.
 */

public class HeartMap_DSTIN extends View {
    private Paint mPaint;
    private Bitmap bmpDST,bmpSRC;
    private int dx=0;
    private int mItemWaveLength = 0;

    public HeartMap_DSTIN(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint=new Paint();
        bmpDST= BitmapFactory.decodeResource(getResources(), R.mipmap.heartmap,null);
        bmpSRC=Bitmap.createBitmap(bmpDST.getWidth(),bmpDST.getHeight(),Bitmap.Config.ARGB_8888);
        mItemWaveLength=bmpDST.getWidth();
        startAnima();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Canvas c=new Canvas(bmpSRC);
        c.drawColor(Color.RED, PorterDuff.Mode.CLEAR);
        c.drawRect(bmpDST.getWidth()-dx,0,bmpDST.getWidth(),bmpDST.getHeight(),mPaint);

        int layerId=canvas.saveLayer(0,0,bmpDST.getWidth(),bmpDST.getHeight(),null,Canvas.ALL_SAVE_FLAG);
        canvas.drawBitmap(bmpDST,0,0,mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawBitmap(bmpSRC,0,0,mPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(layerId);
    }

    private void startAnima() {
        ValueAnimator animator=ValueAnimator.ofInt(0,mItemWaveLength);
        animator.setDuration(6000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                dx= (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.start();
    }
}
