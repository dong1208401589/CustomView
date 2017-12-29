package com.dong.customview.canvas;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;

/**
 * Created by Administrator on 2017/12/17.
 */

public class RevealDrawable extends Drawable {

    private Drawable mGrayDrawable;  //灰色图片
    private Drawable mColourDrawable;   //彩色图片

    private int mOrientation;
    public final static int HORIZONTAL=1;
    public final static int VERTICAL=2;
    private Rect mTmpRect=new Rect();

    public RevealDrawable(Drawable grayDrawable, Drawable colourDrawable, int mOrientation) {
        this.mGrayDrawable = grayDrawable;
        this.mColourDrawable = colourDrawable;
        this.mOrientation = mOrientation;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        int level=getLevel(); //从0到10000
        //三个区间

        //左边和右边设置成灰色
        if (level==10000||level==0){
            mGrayDrawable.draw(canvas);
        }else if (level==5000){
            mColourDrawable.draw(canvas);
        }else {
            //混合效果的Drawable

            //将画板切成两块，左边和右边

            final Rect r=mTmpRect;
            //得到自身的距形区域
            Rect bounds=getBounds();

            //level 0到5000到10000
            //比例
            float ratio=(level/5000f)-1f;
            int w=bounds.width();

            //1、先绘制灰色部分
            if (mOrientation==HORIZONTAL){
                w= (int) (w*Math.abs(ratio));
            }
            int h=bounds.height();
            if (mOrientation==VERTICAL){
                h= (int) (h*Math.abs(ratio));
            }

            int gravity=ratio<0? Gravity.LEFT:Gravity.RIGHT;
            //从一个已有的bounds矩形中抠出一个矩开r
            Gravity.apply(
                    gravity,//从左边还是从右边开发抠
                    w, //目标距形的宽
                    h,//目标距形的高
                    bounds,
                    r
            );
            canvas.save();
            canvas.clipRect(r);
            mGrayDrawable.draw(canvas);
            canvas.restore();


            //2、绘制彩色部分
            //level 0到5000到10000
            w = bounds.width();
            if (mOrientation==HORIZONTAL){
                w-=w*Math.abs(ratio);
            }

            h=bounds.height();
            if (mOrientation==VERTICAL){
                h-=h*Math.abs(ratio);
            }

            gravity=ratio<0?Gravity.RIGHT:Gravity.LEFT;
            Gravity.apply(
                    gravity,
                    w,
                    h,
                    bounds,
                    r
            );

            canvas.save();
            canvas.clipRect(r);
            mColourDrawable.draw(canvas);
            canvas.restore();

        }



    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        mColourDrawable.setBounds(bounds);
        mGrayDrawable.setBounds(bounds);

    }

    @Override
    public int getIntrinsicHeight() {
        return Math.max(mColourDrawable.getIntrinsicHeight(),mGrayDrawable.getIntrinsicHeight());
    }

    @Override
    public int getIntrinsicWidth() {
        return Math.max(mColourDrawable.getIntrinsicWidth(),mGrayDrawable.getIntrinsicWidth());
    }

    @Override
    protected boolean onLevelChange(int level) {
        invalidateSelf();
        return true;
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }
}
