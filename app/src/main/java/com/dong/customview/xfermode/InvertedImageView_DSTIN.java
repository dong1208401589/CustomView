package com.dong.customview.xfermode;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.dong.customview.R;

/**
 * Created by dc on 2017/12/8.
 */

public class InvertedImageView_DSTIN extends View {

    private Paint mBitPaint;
    private Bitmap bmpDST,bmpSRC,bmpRevert;

    public InvertedImageView_DSTIN(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setLayerType(View.LAYER_TYPE_SOFTWARE,null);
        mBitPaint=new Paint();
        bmpDST= BitmapFactory.decodeResource(getResources(), R.mipmap.xyjy6,null);
        bmpSRC=BitmapFactory.decodeResource(getResources(),R.mipmap.invert_shade,null);

        Matrix matrix=new Matrix();
        matrix.setScale(1f,-1f);

        bmpRevert=Bitmap.createBitmap(bmpDST,0,0,bmpDST.getWidth(),bmpDST.getHeight(),matrix,true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bmpDST,0,0,mBitPaint);
        int layerId=canvas.saveLayer(0,0,getWidth(),getHeight(),null,Canvas.ALL_SAVE_FLAG);
        canvas.translate(0,bmpSRC.getHeight());
        canvas.drawBitmap(bmpRevert,0,0,mBitPaint);
        mBitPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        canvas.drawBitmap(bmpSRC,0,0,mBitPaint);
        mBitPaint.setXfermode(null);
        canvas.restoreToCount(layerId);
    }
}
